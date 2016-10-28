package de.gerdhirsch.util.undoredo;

import de.gerdhirsch.util.undoredo.Command;

/**
 * @author Marci, Gerd
 */
public class CompositeCommandImpl implements Command, CompositeCommand {
	private boolean redoExceptionCatched = false;
	private boolean undoExceptionCatched = false;
	private boolean doItExceptionCatched = false;

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
		try{
			while (urStack.isRedoable()) {
					urStack.redo();
			}
		}catch(Throwable e){
			doItExceptionCatched = true;
			
			if(undoExceptionCatched){
				undoExceptionCatched = false;
				throw new CannotRollbackException("undo not possible, cause Command.doIt() throws Exception see getCause()", e);
			}else{
				undo();
				doItExceptionCatched = false;
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gerdhirsch.util.undoredo.CompositeCommand#undo()
	 */
	@Override
	public void undo() throws Exception {
		try{
			while (urStack.isUndoable()) {
					urStack.undo();
			}
		}catch(Throwable e){
			undoExceptionCatched = true;
			if(doItExceptionCatched){
				doItExceptionCatched = false;
				throw new CannotRollbackException("doIt not possible, cause Command.undo() throws Exception see getCause()", e);
			}else{
				doIt();
				undoExceptionCatched = false;
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.gerdhirsch.util.undoredo.CompositeCommand#doIt(de.gerdhirsch.util.
	 * undoredo.Command)
	 * @see
	 * javax.swing.undo.CannotUndo/RedoException
	 */
	@Override
	public void doIt(Command c) throws Exception {
		try {
			urStack.doIt(c);
		} catch (Throwable e) {
			doItExceptionCatched = true;
			undo();
			urStack.clear();
			throw e;
		}
	}

	/**
	 * Commands has to be undone in the reverse order
	 * this is done with the help of an UndoRedoStack
	 * @see de.gerdhirsch.util.undoredo.UndoRedoStack
	 */
	UndoRedoStack urStack = null;

	// public Object clone() throws CloneNotSupportedException{
	// CompositeCommandImpl retVal = (CompositeCommandImpl) super.clone();
	// retVal.urha = (UndoRedoManager) urStack.clone();
	//
	// return retVal;
	// }
}