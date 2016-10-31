package de.gerdhirsch.util.undoredo;

public interface CompositeCommand extends Command {

	/**
	 * executes all Commands undone by undo().
	 * If an Exception is thrown by one Command, 
	 * the exception is caught, undo() is done
	 * and the exception is rethrown (exception neutral). 
	 * 
	 * @see #undo()
	 * @see CannotRollbackException
	 * @gh.pre no Command throws an Exception
	 * @throws Exception thrown by one of the Commands
	 * @throws CannotRollbackException 
	 * when it is used as rollback from an undo 
	 * and an exception is thrown again
	 */
	void doIt() throws Exception;

	/**
	 * undoes the complete Composite Command.
	 * If an Exception is thrown by one Command, 
	 * the exception is caught, doIt() is done
	 * and the exception is rethrown (exception neutral). 
	 * 
	 * @gh.pre no Command throws an Exception
	 * @see #doIt()
	 * @see #doIt(Command c)
	 * @see CannotRollbackException
	 * @throws Exception thrown by one of the Commands
	 * @throws CannotRollbackException 
	 * when it is used as rollback from an doIt 
	 * and an exception is thrown again
	 */
	void undo() throws Exception;

	/**
	 * executes the Command c immediatly
	 * and pushes it to the undoStack.
	 * If an Exception is thrown by the Command, 
	 * undo is done and all Commands are removed (clear()).
	 * If undo throws an Exception, too,
	 * this Exception is wrapped in a CannotRollbackException.
	 * @see de.gerdhirsch.util.undoredo.UndoRedoStack#clear()
	 * @gh.pre no Command throws an Exception
	 * @see CannotRollbackException
	 * @param c the Command to be executed
	 * @throws Exception thrown by the Implementation of c
	 */
	void doIt(Command c) throws Exception;

}