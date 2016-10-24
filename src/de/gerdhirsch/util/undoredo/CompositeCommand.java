package de.gerdhirsch.util.undoredo;

public interface CompositeCommand extends Command {

	/**
	 * executes all Commands after an undo()
	 * 
	 * if it is called from the catch Block of undo()
	 * nothing is done but the Exception is rethrown
	 * 
	 * @see #undo()
	 * @pre no Command throws an Exception
	 * @throws Exception thrown by one of the Commands
	 */
	void doIt() throws Exception;

	/**
	 * undoes the complete Composite Command
	 * if an Exception is thrown, doIt() is done.
	 * 
	 * if it is called from the catch Block of doIt()
	 * nothing is done but the Exception is rethrown
	 * 
	 * @pre no Command throws an Exception
	 * @see #doIt()
	 * @throws Exception thrown by one of the Commands
	 */
	void undo() throws Exception;

	/**
	 * executes the Command c immediatly
	 * if an Exception is thrown, undo is done
	 * 
	 * @pre no Command throws an Exception
	 * @param c the Command to be executed
	 * @throws Exception thrown by the Implementation of c
	 */
	void doIt(Command c) throws Exception;

}