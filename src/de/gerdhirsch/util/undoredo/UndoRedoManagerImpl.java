package de.gerdhirsch.util.undoredo;

import de.gerdhirsch.util.undoredo.UndoRedoManager;

public class UndoRedoManagerImpl extends UndoRedoStackImpl implements UndoRedoManager {

	int modifiedCount = 0;

	public boolean isModified() {
		return modifiedCount != 0;
	}

	public void resetModified() {
		modifiedCount = 0;
	}
	public synchronized void undo() throws Exception {
		modifiedCount--;
		super.undo();
	}

	public synchronized void redo() throws Exception {
		modifiedCount++;
		super.redo();
	}

	public synchronized void doIt(Command c) throws Exception {
		if (modifiedCount < 0)
			modifiedCount = undoStackSize() + 1;
		modifiedCount++;
		
		super.doIt(c);
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