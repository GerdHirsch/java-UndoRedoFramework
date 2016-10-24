package de.gerdhirsch.util.undoredo;

import java.util.Stack;

public class UndoRedoStackImpl implements UndoRedoStack {

	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	public boolean isRedoable() {
		return !redoStack.isEmpty();
	}

	public boolean isUndoable() {
		return !undoStack.isEmpty();
	}

	public void undo() throws Exception {
		undoStack.peek().undo();
		
		redoStack.push(undoStack.pop());
	}

	public void redo() throws Exception {
		redoStack.peek().doIt();

		undoStack.push(redoStack.pop());
	}
	/**
	 * @pre: Command don´t throw
	 * @post: isModified() == true
	 * @post: isUndoable() == true
	 * @post: isRedoable() == false
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @exception Exception throws the Exception of the Command c
	 */
	public void doIt(Command c) throws Exception {
		c.doIt();
		undoStack.push(c);
		redoStack.clear();
	}
	protected int undoStackSize(){ return undoStack.size();}

}