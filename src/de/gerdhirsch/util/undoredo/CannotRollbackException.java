/**
 * 
 */
package de.gerdhirsch.util.undoredo;

/**
 * Thrown by CompositeCommand, after a sequence of two exceptions
 * when doIt(Command c) throws and undo() throws 
 * or 
 * when undo() throws and redo() too 
 * @author Gerd
 *
 */
public class CannotRollbackException extends RuntimeException {

	/**
	 * @param message reason why the exception is thrown
	 * @param cause the last exception thrown by Command
	 */
	public CannotRollbackException(String message, Throwable cause) {
		super(message, cause);
	}
}
