package de.gerdhirsch.util.undoredo;

public interface UndoRedoStack {

	/**
	 * sendet dem letzten ausgef�hrten Command undo
	 * 
	 * @throws Exception
	 */
	void undo() throws Exception;

	/**
	 * sendet dem letzten r�ckg�ngig gemachten Command redo
	 * 
	 * @throws Exception
	 */
	void redo() throws Exception;

	/**
	 * f�hrt das �bergebene Command c aus und stellt es f�r undo zur Verf�gung
	 * der redoStack wird gel�scht, isModified() liefert true.
	 * @param c
	 * @throws Exception
	 */
	void doIt(Command c) throws Exception;

	/**
	 * @return true, wenn undo ausf�hrbar ist
	 */
	boolean isUndoable();

	/**
	 * @return true, wenn redo ausf�hrbar ist. redo() ist nicht ausf�hrbar
	 * nach einer Sequenz von undo() und doIt(c), der redoStack wird gel�scht.
	 */
	boolean isRedoable();

}