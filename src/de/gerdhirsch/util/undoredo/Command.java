package de.gerdhirsch.util.undoredo;

/**
 * 
 * @author Marci
 */
public interface Command {
	/**
	 * executes the Command
	 * @throws Exception thrown by the Implementation
	 */
	void doIt() throws Exception;

	/**
	 * revert the Command
	 * @throws Exception thrown by the Implementation
	 */
	void undo() throws Exception;
}