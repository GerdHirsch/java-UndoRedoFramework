package de.gerdhirsch.util.undoredo;

/**
 * 
 * @author Marci
 */
public interface Command {
	/**
	 * führt ein Command aus
	 */
	void doIt() throws Exception;

	/**
	 * Macht das Command rückgängig
	 */
	void undo() throws Exception;

	//Object clone() throws CloneNotSupportedException;
}