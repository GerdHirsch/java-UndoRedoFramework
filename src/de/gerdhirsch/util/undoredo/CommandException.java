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
	 * @param message, message of the exception
	 */
	public CommandException(String message) {
		super(message);
		this.command = null;
	}
	/**
	 * @param message, message of the exception
	 * @param command, the Command that has thrown 
	 * (this inside of the command)
	 */
	public CommandException(String message, Command command) {
		super(message);
		this.command = command;
	}
	/**
	 * @param message, message of the exception
	 * @param command, the Command that has thrown an exception 
	 * (this inside of the command)
	 * @param cause the thrown exception
	 */
	public CommandException(String message, Command command, Throwable cause) {
		super(message, cause);
		this.command = command;
	}
}
