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
		urMngr.doIt(plus);
		assertThat(plusValue, is(calculator.getResult()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}
	@Test
	public final void testDoItWithExceptioni() throws Exception {
		Plus.throwException = true;
		try{
			urMngr.doIt(plus);
		}catch(Exception e){}
		
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}

	@Test
	public final void testUndo() throws Exception{
		urMngr.doIt(plus);
		urMngr.undo();
		
		assertThat(0, is(calculator.getResult()));	
		
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
	}
	@Test
	public final void testUndoWithException() throws Exception {
		urMngr.doIt(plus);
		Plus.throwException = true;
		try{
			urMngr.undo();
		}catch(Exception e){}
		
		assertThat(plusValue, is(calculator.getResult()));	
		
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}

	@Test
	public final void testRedo() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
		
		assertThat(0, is(calculator.getResult()));	

		urMngr.redo();

		assertThat(plusValue, is(calculator.getResult()));	
		
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}
	
	@Test
	public final void testRedoWithException() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		Plus.throwException = true;
		try{
			urMngr.redo();
		}catch(Exception e){}
		
		assertThat(0, is(calculator.getResult()));	
		
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
	}

	@Test
	public final void testIsUndoable() throws Exception {
		urMngr.doIt(plus);
		assertThat(true, is(urMngr.isUndoable()));
	}

	@Test
	public final void testIsRedoable() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		assertThat(true, is(urMngr.isRedoable()));
	}

}