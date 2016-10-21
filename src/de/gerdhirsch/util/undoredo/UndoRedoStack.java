package de.gerdhirsch.util.undoredo;

public interface UndoRedoStack {

	/**
	 * sendet dem letzten ausgeführten Command undo
	 * 
	 * @throws Exception
	 */
	void undo() throws Exception;

	/**
	 * sendet dem letzten rückgängig gemachten Command redo
	 * 
	 * @throws Exception
	 */
	void redo() throws Exception;

	/**
	 * führt das übergebene Command c aus und stellt es für undo zur Verfügung
	 * der redoStack wird gelöscht, isModified() liefert true.
	 * @param c
	 * @throws Exception
	 */
	void doIt(Command c) throws Exception;

	/**
	 * @return true, wenn undo ausführbar ist
	 */
	boolean isUndoable();

	/**
	 * @return true, wenn redo ausführbar ist. redo() ist nicht ausführbar
	 * nach einer Sequenz von undo() und doIt(c), der redoStack wird gelöscht.
	 */
	boolean isRedoable();

}