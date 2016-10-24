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
	public void setUp() {
		super.setUp();
		ccmd = new CompositeCommandImpl(new UndoRedoStackImpl());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * tests throwing an IllegalArgumentException für null UndoRedoStack
	 */
	@Test()
	public final void testCommandComposite() {
		thrown.expect(IllegalArgumentException.class);
		new CompositeCommandImpl(null);
	}

	/**
	 * tests the execution of minus and plus command
	 * @throws Exception thrown by the Commands
	 */
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

	/**
	 * tests the rollback all commands done bevor the Exception is thrown
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testDoItCommandWithException() throws Exception {
		Plus.throwException = true;
		
		int expected = 0; 
		assertThat(calculator.getResult(), is(expected));
		
		try{
			ccmd.doIt(minus);
			ccmd.doIt(minus);
			ccmd.doIt(plus);
		}catch(Exception e){
//			System.out.println("testDoItCommandWithException: " + e.getMessage());
		}
		assertThat(calculator.getResult(), is(expected));
		urMngr.doIt(ccmd);
		assertThat(calculator.getResult(), is(expected));
		urMngr.undo();
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
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - (minusValue+minusValue);
		assertThat(calculator.getResult(), is(expected));
		
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(expected));
		
		Plus.throwException = false;
		
		urMngr.undo();

		expected = 0;
		assertThat(calculator.getResult(), is(expected));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		int expected = 0;
		ccmd.doIt(minus);
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - (minusValue+minusValue);
		assertThat(calculator.getResult(), is(expected));
		
		urMngr.undo();
		
		expected = 0;
		assertThat(calculator.getResult(), is(expected));

		Plus.throwException = true;

		try{
			urMngr.redo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(expected));
		
		Plus.throwException = false;
		
		urMngr.redo();

		expected = plusValue - (minusValue+minusValue);
		assertThat(calculator.getResult(), is(expected));
	}
	
	@Test()
	public final void testdoItException() throws Exception {
		thrown.expect(Exception.class);
		Plus.throwException = true;
		ccmd.doIt(plus);
	}
	
	@Test()
	public final void testUndoException() throws Exception {
		thrown.expect(Exception.class);
		ccmd.doIt(plus);
		Plus.throwException = true;
		ccmd.undo();
	}
	
	@Test()
	public final void testRedoException() throws Exception {
		thrown.expect(Exception.class);
		ccmd.doIt(plus);
		ccmd.undo();
		Plus.throwException = true;
		ccmd.doIt();
	}
}
