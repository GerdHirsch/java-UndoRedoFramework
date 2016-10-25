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
	 * @gh.pre: Command don´t throw
	 * @gh.post: isModified() == true
	 * @gh.post: isUndoable() == true
	 * @gh.post: isRedoable() == false
	 * if Command throws an Exception, UndoRedoStack stays unchanged
	 * @exception Exception throws the Exception of the Command c
	 */
	public void doIt(Command c) throws Exception {
		c.doIt();
		undoStack.push(c);
		redoStack.clear();
	}
	protected int undoStackSize(){ return undoStack.size();}

	@Override
	public void clear() {
		undoStack.clear();
		redoStack.clear();
	}
	

//	@SuppressWarnings("unchecked")
//	public Object clone() throws CloneNotSupportedException {
//		UndoRedoManagerImpl retVal = (UndoRedoManagerImpl) super.clone();
//
//		retVal.redoStack = deepCopy(redoStack);
//		retVal.undoStack = deepCopy(undoStack);
//		assert (redoStack.isEmpty() == retVal.redoStack.isEmpty());
//		assert (undoStack.isEmpty() == retVal.undoStack.isEmpty());
//
//		return retVal;
//	}

//	private Stack<Command> deepCopy(Stack<Command> stack)
//			throws CloneNotSupportedException {
//		Stack<Command> retVal = new Stack<Command>();
//		Command[] commands = new Command[stack.size()];
//		stack.copyInto(commands);
//		for (int i = 0; i < commands.length; i++)
//			retVal.push((Command) commands[i].clone());
//		return retVal;
//	}

}