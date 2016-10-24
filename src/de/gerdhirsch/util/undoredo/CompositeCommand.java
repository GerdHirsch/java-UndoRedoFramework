package de.gerdhirsch.util.undoredo;

public interface CompositeCommand extends Command {

	/**
	 * f�hrt die Commands wieder aus
	 * nach dem sie r�ckg�ngig gemacht wurden
	 * @see undo()
	 * 
	 * @throws Exception thrown by the one of the Commands
	 */
	void doIt() throws Exception;

	void undo() throws Exception;

	/**
	 * f�gt das Command c in den internen 
	 * (UndoRedoStack.doit(Command c) ein
	 * 
	 * @param c
	 * @throws Exception
	 */
	void doIt(Command c) throws Exception;

}