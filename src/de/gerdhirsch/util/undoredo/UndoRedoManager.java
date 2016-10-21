package de.gerdhirsch.util.undoredo;
/**
 *	UML Diagramm und Dokumentation in
 *	DesignPatternSeminar.DesignPattern.UndoRedoFramework
 * @author Gerd
 *
 */
public interface UndoRedoManager extends UndoRedoStack{
	/**
	 * 
	 * @return true wenn der UndoRedoManager in dem Zustand ist, in dem er nach
	 *         dem letzten resetModified() war.
	 */
	public boolean isModified();

	/**
	 * versetzt den UndoRedoManager in den nicht modifizierten
	 * Zustand. Nach undo() liefert isModified() true, 
	 * nach redo() wieder false. Nach einer Sequenze von resetModified(),
	 * undo() und doIt(c)  liefert isModified() immer false!
	 * 
	 */
	public void resetModified();
	/**
	 * @return Eine Kopie der Stacks (Undo/Redo) 
	 * @throws CloneNotSupportedException
	 */
//	public Object clone() throws CloneNotSupportedException;
}