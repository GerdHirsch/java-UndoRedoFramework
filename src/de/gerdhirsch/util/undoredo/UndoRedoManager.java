package de.gerdhirsch.util.undoredo;
/**
 *	UML Diagram and Documentation in
 *	DesignPatternSeminar.DesignPattern.UndoRedoFramework
 * @author Gerd
 *
 */
public interface UndoRedoManager extends UndoRedoStack{
	/**
	 * 
	 * @return true wenn der UndoRedoManager in dem Zustand ist, in dem er nach
	 *         dem letzten resetModified() war.
	 */
	public boolean isModified();

	/**
	 * resets the UndoRedoManager in an unmodified state.
	 * after a call to undo() isModified() == true  
	 * after redo() isModified() == false. 
	 * 
	 * after a sequence of resetModified(), undo(), doIt(c)
	 * is isModified() always == false
	 * @see #undo()
	 */
	public void resetModified();
//	/**
//	 * @return Eine Kopie der Stacks (Undo/Redo) 
//	 * @throws CloneNotSupportedException
//	 */
////	public Object clone() throws CloneNotSupportedException;
}