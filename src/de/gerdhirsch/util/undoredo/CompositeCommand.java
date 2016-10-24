package de.gerdhirsch.util.undoredo;

public interface CompositeCommand extends Command {

	/**
	 * führt die Commands wieder aus
	 * nach dem sie rückgängig gemacht wurden
	 * @see undo()
	 * 
	 * @throws Exception thrown by the one of the Commands
	 */
	void doIt() throws Exception;

	void undo() throws Exception;

	/**
	 * fügt das Command c in den internen 
	 * (UndoRedoStack.doit(Command c) ein
	 * 
	 * @param c
	 * @throws Exception
	 */
	void doIt(Command c) throws Exception;

}