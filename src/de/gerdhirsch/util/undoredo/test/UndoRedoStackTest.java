package de.gerdhirsch.util.undoredo.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UndoRedoStackTest extends UndoRedoTest {

	public UndoRedoStackTest() {
		super();
	}

	@Test
	public final void testDoIt() throws Exception {
		int result = calculator.getResult();
		int expected = 0;
		assertThat(result, is(expected));
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));
		
		urMngr.doIt(plus);
		expected = plusValue;
		result = calculator.getResult();
		assertThat(result, is(expected));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(false));
	}
	@Test
	public final void testDoItWithException() throws Exception {
		Plus.throwException = true;
		
		int result = calculator.getResult();
		int expected = 0;
		assertThat(calculator.getResult(), is(expected));
		
		try{
			urMngr.doIt(plus);
		}catch(Exception e){}
		
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));
	}

	@Test
	public final void testUndo() throws Exception{
		urMngr.doIt(plus);
		urMngr.undo();
		
		assertThat(calculator.getResult(), is(0));	
		
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(true));
	}
	@Test
	public final void testUndoWithException() throws Exception {
		urMngr.doIt(plus);
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(plusValue));	
		
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(false));
	}

	@Test
	public final void testRedo() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(true));
		
		assertThat(calculator.getResult(), is(0));	

		urMngr.redo();

		assertThat(calculator.getResult(), is(plusValue));	
		
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(false));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		Plus.throwException = true;
		try{
			urMngr.redo();
		}catch(Exception e){}
		
		assertThat(calculator.getResult(), is(0));	
		
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(true));
	}

	@Test
	public final void testIsUndoable() throws Exception {
		urMngr.doIt(plus);
		assertThat(urMngr.isUndoable(), is(true));
	}

	@Test
	public final void testIsRedoable() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		assertThat(urMngr.isRedoable(), is(true));
	}

}