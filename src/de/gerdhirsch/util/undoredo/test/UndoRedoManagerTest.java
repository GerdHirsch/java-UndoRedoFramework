package de.gerdhirsch.util.undoredo.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gerdhirsch.util.undoredo.UndoRedoManagerImpl;

import static org.hamcrest.Matchers.is;

public class UndoRedoManagerTest extends UndoRedoTest {

	@Test
	public final void testDoIt() throws Exception {
		urMngr.doIt(plus);
		assertThat(plusValue, is(calculator.getResult()));
	}
	
	@Test
	public final void testUndo() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		assertThat(0, is(calculator.getResult()));	
	}

	@Test
	public final void testRedo() throws Exception {
		urMngr.doIt(plus);
		urMngr.undo();
		urMngr.redo();
		assertThat(plusValue, is(calculator.getResult()));	
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

}
