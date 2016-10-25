/**
 * 
 */
package de.gerdhirsch.util.undoredo;

/**
 * @author Gerd
 *
 */
public class CannotExecuteCommand extends RuntimeException {

	/**
	 * @param message
	 * @param cause
	 */
	public CannotExecuteCommand(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CannotExecuteCommand(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
