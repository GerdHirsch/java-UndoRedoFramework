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
	 * tests Exception in rollback of commands
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testDoItCommandWithExceptionInRollback() throws Exception {
		int expected = 0; 
		int result = calculator.getResult(); 
		assertThat(result, is(expected));
		
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in rollback
		ccmd.doIt(minus);
		
		expected = plusValue-(minusValue+minusValue);
		result = calculator.getResult(); 
		assertThat(result, is(expected));
		
		Plus.throwException = true;
		try{
			ccmd.doIt(plus);
		}catch(Exception e){
			
		}
		
		Plus.throwException = false;
		
		ccmd.undo();
		
		expected = 0;
		result = calculator.getResult(); 
		assertThat(result, is(expected));
		
		
	}
	/**
	 * tests the rollback all commands done bevor the Exception is thrown
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testDoItCommandWithException() throws Exception {
		Plus.throwException = true;
		
		int expected = 0; 
		int result = calculator.getResult(); 
		assertThat(result, is(expected));
		
		try{
			ccmd.doIt(minus);
			ccmd.doIt(minus);
			ccmd.doIt(plus);
		}catch(Exception e){
			
		}
		
		result = calculator.getResult(); 
		assertThat(result, is(expected));
		
		urMngr.doIt(ccmd);
		
		result = calculator.getResult(); 
		assertThat(result, is(expected));
		urMngr.undo();
		
		result = calculator.getResult(); 
		assertThat(result, is(expected));
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
		int result = calculator.getResult();
		assertThat(result, is(expected));

		ccmd.doIt(minus);
		ccmd.doIt(plus);
		ccmd.doIt(minus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - (minusValue+minusValue);
		result = calculator.getResult();
		assertThat(result, is(expected));
		
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		result = calculator.getResult();
		assertThat(result, is(expected));
		
		Plus.throwException = false;
		
		urMngr.undo();

		expected = 0;
		result = calculator.getResult();
		assertThat(result, is(expected));
		
		//check a second Exception
		Plus.throwException = true;
		
		try{
			urMngr.redo();
		}catch(Exception e){}
		
		result = calculator.getResult();
		assertThat(result, is(expected));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		int expected = 0;
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		ccmd.doIt(minus);
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
		
		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch(Exception e){}
		
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
