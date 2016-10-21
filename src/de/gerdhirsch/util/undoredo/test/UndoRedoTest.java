package de.gerdhirsch.util.undoredo.test;

import org.junit.Before;

import de.gerdhirsch.util.undoredo.UndoRedoManager;
import de.gerdhirsch.util.undoredo.UndoRedoManagerImpl;

public class UndoRedoTest {

	protected final Calculator calculator = new Calculator();
	protected final int plusValue = 2;
	protected final Plus plus = new Plus(calculator, plusValue );
	protected final int minusValue = 1;
	protected final Minus minus = new Minus(calculator, minusValue );
	protected UndoRedoManager urMngr;

	public UndoRedoTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		calculator.clear();
		urMngr = new UndoRedoManagerImpl();
	}

}