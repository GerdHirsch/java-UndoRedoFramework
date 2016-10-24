package de.gerdhirsch.util.undoredo;

public interface UndoRedoStack {

	/**
	 * undo last Command
	 * @pre: Command don�t throw
	 * @post: isRedoable() == true
	 * @see #isRedoable()
	 * @see #isUndoable()
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @throws Exception thrown by the Command
	 * @see #redo()
	 */
	void undo() throws Exception;

	/**
	 * redo last undone Command
	 * @pre: Command don�t throw
	 * @post: isUndoable() == true
	 * @see #isRedoable()
	 * @see #isUndoable()
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @throws Exception thrown by the Command
	 * @see #undo()
	 */
	void redo() throws Exception;
	/**
	 * executes Command c
	 * @pre: Command don�t throw
	 * @post: isModified() == true
	 * @post: isUndoable() == true
	 * @post: isRedoable() == false
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @param c the Command to be executed
	 * @throws Exception thrown by the Command
	 */
	void doIt(Command c) throws Exception;

	/**
	 * @return true after doIt(Command c) or redo()
	 * @see #doIt(Command c)
	 * @see #redo()
	 */
	boolean isUndoable();

	/**
	 * @return false after doIt(Command c)
	 * @return true after undo()
	 * @see #doIt(Command c)
	 * @see #undo()
	 * @see #redo()
	 */
	boolean isRedoable();

}