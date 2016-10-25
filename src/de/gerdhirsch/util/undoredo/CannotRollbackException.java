/**
 * 
 */
package de.gerdhirsch.util.undoredo;

/**
 * @author Gerd
 *
 */
public class CannotRollbackException extends RuntimeException {

	/**
	 * @param message reason why the exception is thrown
	 * @param cause the wrapped exception
	 */
	public CannotRollbackException(String message, Throwable cause) {
		super(message, cause);
	}
}
