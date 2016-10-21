package de.gerdhirsch.util.undoredo;

/**
 * 
 * @author Marci
 */
public interface Command {
	/**
	 * f�hrt ein Command aus
	 */
	void doIt() throws Exception;

	/**
	 * Macht das Command r�ckg�ngig
	 */
	void undo() throws Exception;

	//Object clone() throws CloneNotSupportedException;
}