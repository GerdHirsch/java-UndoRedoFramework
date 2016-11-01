package de.gerdhirsch.util.undoredo.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.gerdhirsch.util.undoredo.CannotRollbackException;
import de.gerdhirsch.util.undoredo.CommandException;
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
		assertThat(calculator.getResult(), equalTo(expected));
		
		urMngr.doIt(plus);
		
		expected += plusValue;
		assertThat(calculator.getResult(), equalTo(expected));	
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
		assertThat(result, equalTo(expected));
		
		try{
			ccmd.doIt(minus);
			ccmd.doIt(minus);
			ccmd.doIt(plus);
		}catch(CommandException e){}
		
		result = calculator.getResult(); 
		assertThat(result, equalTo(expected));
		
		urMngr.doIt(ccmd);
		
		result = calculator.getResult(); 
		assertThat(result, equalTo(expected));
		urMngr.undo();
		
		result = calculator.getResult(); 
		assertThat(result, equalTo(expected));
	}

	@Test
	public final void testUndoRedoDoIt() throws Exception {
		int expected = 0;
		
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), equalTo(expected));
		
		urMngr.undo();
		
		expected = 0;
		assertThat(calculator.getResult(), equalTo(expected));
		urMngr.redo();
		
		expected = plusValue - minusValue;
		assertThat(calculator.getResult(), equalTo(expected));
	}
	
	@Test
	public final void testUndoWithException() throws Exception {
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in undo
		ccmd.doIt(minus);
		urMngr.doIt(ccmd);
		
		int expected = plusValue - (minusValue+minusValue);
		int result = calculator.getResult();
		assertThat(result, equalTo(expected));
		
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		result = calculator.getResult();
		assertThat(result, equalTo(expected));
		
		Plus.throwException = false;
		
		assertThat(urMngr.isUndoable(), equalTo(true));
		urMngr.undo();

		expected = 0;
		result = calculator.getResult();
		assertThat(result, equalTo(expected));
		
		//check a second Exception
		Plus.throwException = true;
		
		try{
			urMngr.redo();
		}catch(Exception e){}
		
		result = calculator.getResult();
		assertThat(result, equalTo(expected));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		int expected = 0;
		ccmd.doIt(minus);
		ccmd.doIt(plus);
		ccmd.doIt(minus);
		urMngr.doIt(ccmd);
		
		expected = plusValue - (minusValue+minusValue);
		assertThat(calculator.getResult(), equalTo(expected));
		
		urMngr.undo();
		
		expected = 0;
		assertThat(calculator.getResult(), equalTo(expected));

		Plus.throwException = true;

		try{
			urMngr.redo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), equalTo(expected));
		
		Plus.throwException = false;
		
		urMngr.redo();

		expected = plusValue - (minusValue+minusValue);
		assertThat(calculator.getResult(), equalTo(expected));
		
		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), equalTo(expected));
	}
	
	@Test()
	public final void testdoItExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		Plus.throwException = true;
		ccmd.doIt(plus);
	}
	
	@Test()
	public final void testUndoExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		Plus.throwException = true;
		urMngr.undo();
	}
	@Test()
	public final void testRedoExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		ccmd.doIt(plus);
		urMngr.doIt(ccmd);
		urMngr.undo();
		Plus.throwException = true;
		urMngr.redo();
	}

	/**
	 * tests Exception in rollback of commands
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testDoItCommandWithExceptionInRollback() throws Exception {
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in rollback
		ccmd.doIt(minus);
		
		Plus.throwException = true;
		try{
			ccmd.doIt(plus);
		}catch(CannotRollbackException e){
			Class<?> resultClazz = e.getCause().getClass();
			Class<?> expectedClazz = CommandException.class;
			
			assertThat(resultClazz, equalTo(expectedClazz));
			
			int result = calculator.getResult();
			int expected = 2;
			assertThat(result, equalTo(expected));
			
			Plus.throwException = false;
			ccmd.undo();
			
			result = calculator.getResult();
			expected = 0;
			assertThat(result, equalTo(expected));
		}
	}
	/**
	 * tests Exception in rollback of commands
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testDoItThrowsCannotRollback() throws Exception {
		thrown.expect(CannotRollbackException.class);
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in rollback
		ccmd.doIt(minus);
		
		Plus.throwException = true;
		ccmd.doIt(plus);
	}

	/**
	 * tests Exception in rollback of commands
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testUndoThrowsCannotRollback() throws Exception {
		thrown.expect(CannotRollbackException.class);
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in undo
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in rollback
		urMngr.doIt(ccmd);
		
		Plus.throwException = true;
		Plus.throwAtTimes = 1;

		urMngr.undo();
	}
	/**
	 * tests Exception in rollback of commands
	 * @throws Exception thrown by the Commands
	 */
	@Test
	public final void testRedoThrowsCannotRollback() throws Exception {
		thrown.expect(CannotRollbackException.class);
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in doIt
		ccmd.doIt(minus);
		ccmd.doIt(plus); // throws in rollback
		urMngr.doIt(ccmd);
		urMngr.undo();
		
		Plus.throwException = true;
		Plus.throwAtTimes = 1;
		
		urMngr.redo();
	}
}
