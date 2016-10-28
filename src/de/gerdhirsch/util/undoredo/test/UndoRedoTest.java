package de.gerdhirsch.util.undoredo.test;

import org.junit.Before;

import de.gerdhirsch.util.undoredo.UndoRedoManager;
import de.gerdhirsch.util.undoredo.UndoRedoManagerImpl;
/**
 * common base for undo redo component tests
 * @author Gerd
 *
 */
public class UndoRedoTest {

	protected final Calculator calculator = new Calculator();
	protected final int plusValue = 3;
	protected final Plus plus = new Plus(calculator, plusValue );
	protected final int minusValue = 1;
	protected final Minus minus = new Minus(calculator, minusValue );
	protected UndoRedoManager urMngr;

	public UndoRedoTest() {
		super();
	}
		
	/**
	 * Plus.throwException = false;
	 * Plus.throwAtTimes = 0;
	 * calculator.clear();
	 * urMngr = new ...
	 */
	@Before
	public void setUp() {
		Plus.throwException = false;
		Plus.throwAtTimes = 0;
		calculator.clear();
		urMngr = new UndoRedoManagerImpl();
	}

}