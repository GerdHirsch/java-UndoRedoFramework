package de.gerdhirsch.util.undoredo;

import java.util.Stack;

public class UndoRedoStackImpl implements UndoRedoStack {

	/**
	 * @directed true
	 * @supplierRole UndoCommands
	 */
	private Stack<Command> undoStack = new Stack<Command>();
	/**
	 * @directed true
	 * @supplierRole RedoCommands
	 */
	private Stack<Command> redoStack = new Stack<Command>();

	public UndoRedoStackImpl() {
		super();
	}

	public boolean isRedoable() {
		return !redoStack.isEmpty();
	}

	public boolean isUndoable() {
		return !undoStack.isEmpty();
	}

	public synchronized void undo() throws Exception {
		Command c = undoStack.pop();
		redoStack.push(c);
		c.undo();
	}

	public synchronized void redo() throws Exception {
		Command c = redoStack.pop();
		undoStack.push(c);
		c.doIt();
	}

	public synchronized void doIt(Command c) throws Exception {
		undoStack.push(c);
		redoStack.clear();
		c.doIt();
	}
	protected int undoStackSize(){ return undoStack.size();}

}