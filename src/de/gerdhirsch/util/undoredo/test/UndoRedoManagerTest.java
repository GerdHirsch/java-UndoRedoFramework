package de.gerdhirsch.util.undoredo.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gerdhirsch.util.undoredo.UndoRedoManagerImpl;

import static org.hamcrest.Matchers.is;

public class UndoRedoManagerTest extends UndoRedoTest {

	@Test
	public final void testIsModifiedSimple() throws Exception {
		// initial modified Count == 0
		assertThat(false, is(urMngr.isModified()));
		
		urMngr.doIt(plus); 
		urMngr.doIt(plus); 
		assertThat(true, is(urMngr.isModified()));
		urMngr.undo(); 
		assertThat(true, is(urMngr.isModified()));
		
		urMngr.resetModified();
		assertThat(false, is(urMngr.isModified()));
		
		urMngr.redo(); 
		assertThat(true, is(urMngr.isModified()));
		urMngr.undo(); 
		assertThat(false, is(urMngr.isModified()));
		urMngr.undo();
		assertThat(true, is(urMngr.isModified()));
		urMngr.redo(); 
		assertThat(false, is(urMngr.isModified()));
		urMngr.redo(); 
		assertThat(true, is(urMngr.isModified()));
		
	}
	@Test
	public final void testIsModifiedAndIsRedoableWithNewCommand() throws Exception {
		urMngr.doIt(plus);
		urMngr.doIt(plus); // modified Count == 2 
		assertThat(true, is(urMngr.isModified()));

		// modified Count == 0 undoStack.size == 1
		urMngr.resetModified(); 
		assertThat(false, is(urMngr.isModified()));
		
		urMngr.undo(); // -1
		assertThat(true, is(urMngr.isModified()));
		urMngr.redo(); // 0
		assertThat(false, is(urMngr.isModified()));
		urMngr.undo(); // -1
		assertThat(true, is(urMngr.isModified()));
		
		// NewCommand danach kommen wir nicht mehr 
		// in den unmodified State
		urMngr.doIt(plus); // modified Count == 3 undoStack.size == 2
		assertThat(true, is(urMngr.isModified()));
		//nach neuem Command darf kein redo mehr möglich sein
		assertThat(false, is(urMngr.isRedoable()));
		
		while(urMngr.isUndoable()){
			urMngr.undo();
			assertThat(true, is(urMngr.isModified()));
		}
		
	}

	@Test
	public final void testResetModified() throws Exception {
		urMngr.doIt(plus);
		urMngr.resetModified();
		assertThat(false, is(urMngr.isModified()));
	}
	
	@Test
	public final void testisModifiedtwithExceptiondoIt() throws Exception {
		// initial modified Count == 0
		assertThat(false, is(urMngr.isModified()));
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));

		Plus.throwException = true;
		
		try{
			urMngr.doIt(plus);
		}catch (Exception e){
			System.out.println("catch: " + e.getMessage());
		}
		assertThat(false, is(urMngr.isModified()));
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}
	@Test
	public final void testisModifiedtwithExceptionUndo() throws Exception {
		// initial modified Count == 0
		assertThat(false, is(urMngr.isModified()));
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
		
		urMngr.doIt(plus);
		assertThat(true, is(urMngr.isModified()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));

		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch (Exception e){
			System.out.println("catch: " + e.getMessage());
		}
		assertThat(true, is(urMngr.isModified()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
	}
	@Test
	public final void testisModifiedtwithExceptionRedo() throws Exception {
		// initial modified Count == 0
		assertThat(false, is(urMngr.isModified()));
		assertThat(false, is(urMngr.isUndoable()));
		assertThat(false, is(urMngr.isRedoable()));
		
		urMngr.doIt(plus);
		urMngr.doIt(plus);
		urMngr.undo();
		assertThat(true, is(urMngr.isModified()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
		
		urMngr.resetModified();
		assertThat(false, is(urMngr.isModified()));
		
		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch (Exception e){
			System.out.println("catch: " + e.getMessage());
		}
		assertThat(false, is(urMngr.isModified()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
		
		try{
			urMngr.redo();
		}catch (Exception e){
			System.out.println("catch: " + e.getMessage());
		}
		assertThat(false, is(urMngr.isModified()));
		assertThat(true, is(urMngr.isUndoable()));
		assertThat(true, is(urMngr.isRedoable()));
	}
}
