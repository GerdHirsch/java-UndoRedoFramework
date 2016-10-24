package de.gerdhirsch.util.undoredo.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.gerdhirsch.util.undoredo.CompositeCommand;
import de.gerdhirsch.util.undoredo.CompositeCommandImpl;
import de.gerdhirsch.util.undoredo.UndoRedoStackImpl;

public class CompositeCommandTest extends UndoRedoTest {

	CompositeCommand ccmd;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ccmd = new CompositeCommandImpl(new UndoRedoStackImpl());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test()
	public final void testCommandComposite() {
		thrown.expect(IllegalArgumentException.class);
		new CompositeCommandImpl(null);
	}

	@Test
	public final void testDoItCommand() throws Exception {
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		int expected = plusValue-minusValue; 
		assertThat(calculator.getResult(), is(expected));
		
		urMngr.doIt(plus);
		
		expected += plusValue;
		assertThat(calculator.getResult(), is(expected));	
	}
	
	@Test
	public final void testDoItCommandWithException() throws Exception {
		Plus.throwException = true;
		
		int expected = 0; 
		assertThat(calculator.getResult(), is(expected));
		
		try{
			ccmd.doIt(minus);
			ccmd.doIt(plus);
			urMngr.doIt(ccmd);
		}catch(Exception e){
			
		}
		
		assertThat(calculator.getResult(), is(expected));
	}

	@Test
	public final void testUndoRedoDoIt() throws Exception {
		int expected = 0;
		
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), is(expected));
		
		urMngr.undo();
		
		expected = 0;
		assertThat(calculator.getResult(), is(expected));
		urMngr.redo();
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), is(expected));
	}
	
	@Test
	public final void testUndoWithException() throws Exception {
		int expected = 0;
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), is(expected));
		
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(expected));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		int expected = 0;
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), is(expected));
		urMngr.undo();
		
		expected = 0;
		assertThat(calculator.getResult(), is(expected));
		
		Plus.throwException = true;
		try{
			urMngr.redo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(expected));
	}
}
