package de.gerdhirsch.util.undoredo;
/**
 *	UML Diagram and Documentation in
 *	DesignPatternSeminar.DesignPattern.UndoRedoFramework
 * @gh.inv exception neutral: should be always in a valid state
 * and throws all exception thrown by the commands.
 * @author Gerd
 *
 */
public interface UndoRedoManager extends UndoRedoStack{
	/**

	 * @return false, if the UndoRedoManager is in the same
	 * state, as it was after resetModified, else true.
	 * @see #resetModified()
	 */
	public boolean isModified();

	/**
	 * Resets the UndoRedoManager into the unmodified state.
	 * 
	 * After a sequence of resetModified(), doIt(c), undo() 
	 * isModified() == true,   
	 * after redo() isModified() again == false. 
	 * 
	 * After a sequence of resetModified(), undo(), doIt(c)
	 * isModified() is always == false
	 * @see #doIt(Command)
	 * @see #undo()
	 * @see #redo()
	 * @see #isModified()
	 */
	public void resetModified();
//	/**
//	 * @return Eine Kopie der Stacks (Undo/Redo) 
//	 * @throws CloneNotSupportedException
//	 */
////	public Object clone() throws CloneNotSupportedException;
}