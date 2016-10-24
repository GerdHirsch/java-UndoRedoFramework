package de.gerdhirsch.util.undoredo;

import de.gerdhirsch.util.undoredo.Command;

/**
 * @author Marci, Gerd
 */
public class CompositeCommandImpl implements Command, CompositeCommand {
	public CompositeCommandImpl(UndoRedoStack urStack) {
		// Precondition
		if (urStack == null)
			throw new IllegalArgumentException("urStack must not be null");

		this.urStack = urStack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gerdhirsch.util.undoredo.CompositeCommand#doIt()
	 */
	@Override
	public void doIt() throws Exception {
		while (urStack.isRedoable()) {
			urStack.redo();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gerdhirsch.util.undoredo.CompositeCommand#undo()
	 */
	@Override
	public void undo() throws Exception {
		while (urStack.isUndoable()) {
			urStack.undo();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.gerdhirsch.util.undoredo.CompositeCommand#doIt(de.gerdhirsch.util.
	 * undoredo.Command)
	 */
	@Override
	public void doIt(Command c) throws Exception {
		try {
			urStack.doIt(c);
		} catch (Throwable e) {
			undo();
		}
	}

	/**
	 * Commands müssen in der umgekehrten Reihenfolge rückgängig gemacht werden,
	 * wie sie ausgeführt wurden! Dazu wird ein UndoRedoManager benutzt.
	 * 
	 * @directed true
	 * @supplierRole Command Manager
	 */
	UndoRedoStack urStack = null;

	// public Object clone() throws CloneNotSupportedException{
	// CompositeCommandImpl retVal = (CompositeCommandImpl) super.clone();
	// retVal.urha = (UndoRedoManager) urStack.clone();
	//
	// return retVal;
	// }
}