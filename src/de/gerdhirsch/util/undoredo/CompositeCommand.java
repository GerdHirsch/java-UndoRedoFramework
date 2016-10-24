package de.gerdhirsch.util.undoredo;

import de.gerdhirsch.util.undoredo.Command;

/**
 * @author Marci, Gerd
 */
public class CompositeCommand implements Command{
    public CompositeCommand(UndoRedoStack urStack) {
    	//Precondition
    	if(urStack == null) 
    		throw new IllegalArgumentException("urStack must not be null");
    	
        this.urStack = urStack;
    }

    /**
     * f�hrt die Commands wieder aus
	 * nach dem sie r�ckg�ngig gemacht wurden
	 * @see undo()
     * 
     * @throws Exception thrown by the one of the Commands
     */
    public void doIt() throws Exception {
        while (urStack.isRedoable()) {
            urStack.redo();
        }
    }
    public void undo() throws Exception {
        while (urStack.isUndoable()) {
            urStack.undo();
        }
    }

    /**
     * f�gt das Command c in den internen 
     * (UndoRedoStack.doit(Command c) ein
     * 
     * @param c
     * @throws Exception
     */
    public void doIt(Command c) throws Exception {
        urStack.doIt(c);
    }

    /**
     * Commands m�ssen in der umgekehrten Reihenfolge r�ckg�ngig gemacht werden,
     * wie sie ausgef�hrt wurden! Dazu wird ein UndoRedoManager benutzt.
     * 
     * @directed true
     * @supplierRole Command Manager
     */
    UndoRedoStack urStack = null;
	
//	public Object clone() throws CloneNotSupportedException{
//		CompositeCommand retVal = (CompositeCommand) super.clone();
//		retVal.urha = (UndoRedoManager) urStack.clone();
//		
//		return retVal;
//	}
}