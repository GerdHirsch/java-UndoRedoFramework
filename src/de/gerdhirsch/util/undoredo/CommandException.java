/**
 * 
 */
package de.gerdhirsch.util.undoredo;

/**
 * @author Gerd
 *
 */
public class CommandException extends RuntimeException {
	private Command command;

	public Command getCommand(){
		return command;
	}
	/**
	 * @param message
	 */
	public CommandException(String message) {
		super(message);
		this.command = null;
	}
	/**
	 * @param message
	 */
	public CommandException(String message, Command command) {
		super(message);
		this.command = command;
	}
	/**
	 * @param message
	 * @param cause
	 */
	public CommandException(String message, Command command, Throwable cause) {
		super(message, cause);
		this.command = command;
	}
}
