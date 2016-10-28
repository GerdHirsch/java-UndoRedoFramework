package de.gerdhirsch.util.undoredo;

/*
 * @autor gerd
 */
/**
 * Two Stacks to manage Commands. 
 * @gh.inv exception neutral: should be always in a valid state
 * and throws all exception thrown by the commands.
 */
public interface UndoRedoStack {

	/**
	 * undo last Command
	 * @gh.pre  Command don´t throw.
	 * If Command throws an Exception, UndoRedoStack stays unchanged
	 * @gh.post isRedoable() == true
	 * @see #isRedoable()
	 * @see #isUndoable()
	 * @throws Exception thrown by the Command
	 * @see #redo()
	 */
	void undo() throws Exception;

	/**
	 * redo last undone Command
	 * @gh.pre Command don´t throw.
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @gh.post isUndoable() == true
	 * @see #isRedoable()
	 * @see #isUndoable()
	 * @throws Exception thrown by the Command
	 * @see #undo()
	 */
	void redo() throws Exception;
	/**
	 * executes Command c
	 * @gh.pre Command don´t throw.
	 * If Command throws an Exception, UndoRedoStack stays unchanged
	 * @gh.post isUndoable() == true
	 * @gh.post isRedoable() == false
	 * @param c the Command to be executed
	 * @throws Exception thrown by the Command
	 * @see #isUndoable()
	 * @see #isRedoable()
	 */
	void doIt(Command c) throws Exception;

	/**
	 * @return true after doIt(Command c) or redo()
	 * @see #doIt(Command c)
	 * @see #redo()
	 * 
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
	/**
	 * clears undo and redo Stack 
	 */
	void clear();
}