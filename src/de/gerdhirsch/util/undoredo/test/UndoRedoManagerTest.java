package de.gerdhirsch.util.undoredo.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UndoRedoManagerTest extends UndoRedoTest {

	@Test
	public final void testIsModifiedSimple() throws Exception {
		// initial modified Count == 0
		assertThat(urMngr.isModified(), is(false));
		
		urMngr.doIt(plus); 
		urMngr.doIt(plus); 
		assertThat(urMngr.isModified(), is(true));
		urMngr.undo(); 
		assertThat(urMngr.isModified(), is(true));
		
		urMngr.resetModified();
		assertThat(urMngr.isModified(), is(false));
		
		urMngr.redo(); 
		assertThat(urMngr.isModified(), is(true));
		urMngr.undo(); 
		assertThat(urMngr.isModified(), is(false));
		urMngr.undo();
		assertThat(urMngr.isModified(), is(true));
		urMngr.redo(); 
		assertThat(urMngr.isModified(), is(false));
		urMngr.redo(); 
		assertThat(urMngr.isModified(), is(true));
		
	}
	@Test
	public final void testIsModifiedAndIsRedoableWithNewCommand() throws Exception {
		urMngr.doIt(plus);
		urMngr.doIt(plus); // modified Count == 2 
		assertThat(urMngr.isModified(), is(true));

		// modified Count == 0 undoStack.size == 1
		urMngr.resetModified(); 
		assertThat(urMngr.isModified(), is(false));
		
		urMngr.undo(); // -1
		assertThat(urMngr.isModified(), is(true));
		urMngr.redo(); // 0
		assertThat(urMngr.isModified(), is(false));
		urMngr.undo(); // -1
		assertThat(urMngr.isModified(), is(true));
		
		// NewCommand danach kommen wir nicht mehr 
		// in den unmodified State
		urMngr.doIt(plus); // modified Count == 3 undoStack.size == 2
		assertThat(urMngr.isModified(), is(true));
		//nach neuem Command darf kein redo mehr möglich sein
		assertThat(urMngr.isRedoable(), is(false));
		
		while(urMngr.isUndoable()){
			urMngr.undo();
			assertThat(urMngr.isModified(), is(true));
		}
		
	}

	@Test
	public final void testResetModified() throws Exception {
		urMngr.doIt(plus);
		urMngr.resetModified();
		assertThat(urMngr.isModified(), is(false));
	}
	
	@Test
	public final void testisModifiedtwithExceptiondoIt() throws Exception {
		// initial modified Count == 0
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));

		Plus.throwException = true;
		
		try{
			urMngr.doIt(plus);
		}catch (Exception e){
//			System.out.println("catch: " + e.getMessage());
		}
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));
	}
	@Test
	public final void testisModifiedtwithExceptionUndo() throws Exception {
		// initial modified Count == 0
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));
		
		urMngr.doIt(plus);
		assertThat(urMngr.isModified(), is(true));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(false));

		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch (Exception e){
//			System.out.println("catch: " + e.getMessage());
		}
		assertThat(urMngr.isModified(), is(true));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(false));
	}
	@Test
	public final void testisModifiedtwithExceptionRedo() throws Exception {
		// initial modified Count == 0
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(false));
		assertThat(urMngr.isRedoable(), is(false));
		
		urMngr.doIt(plus);
		urMngr.doIt(plus);
		urMngr.undo();
		assertThat(urMngr.isModified(), is(true));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(true));
		
		urMngr.resetModified();
		assertThat(urMngr.isModified(), is(false));
		
		Plus.throwException = true;
		
		try{
			urMngr.undo();
		}catch (Exception e){
//			System.out.println("catch: " + e.getMessage());
		}
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(true));
		
		try{
			urMngr.redo();
		}catch (Exception e){
//			System.out.println("catch: " + e.getMessage());
		}
		assertThat(urMngr.isModified(), is(false));
		assertThat(urMngr.isUndoable(), is(true));
		assertThat(urMngr.isRedoable(), is(true));
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test()
	public final void testdoItExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		Plus.throwException = true;
		urMngr.doIt(plus);
	}
	
	@Test()
	public final void testUndoExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		urMngr.doIt(plus);
		Plus.throwException = true;
		urMngr.undo();
	}
	
	@Test()
	public final void testRedoExceptionNeutral() throws Exception {
		thrown.expect(Exception.class);
		urMngr.doIt(plus);
		urMngr.undo();
		Plus.throwException = true;
		urMngr.redo();
	}
}
