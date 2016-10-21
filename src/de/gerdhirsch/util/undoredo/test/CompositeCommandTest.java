package de.gerdhirsch.util.undoredo.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.gerdhirsch.util.undoredo.CompositeCommand;
import de.gerdhirsch.util.undoredo.UndoRedoStackImpl;

public class CompositeCommandTest extends UndoRedoTest {

	CompositeCommand ccmd;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ccmd = new CompositeCommand(new UndoRedoStackImpl());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
//	public ExpectedException thrown = ExpectedException.none().expect(IllegalArgumentException.class);
	
	@Test()
	public final void testCommandComposite() {
		thrown.expect(IllegalArgumentException.class);
		new CompositeCommand(null);
	}

	@Test
	public final void testUndoDoIt() throws Exception {
		int expected = 0;
		ccmd.doIt(plus);
		ccmd.doIt(minus);
		urMngr.doIt(ccmd);
		expected = plusValue - minusValue;
		assertThat(expected, is(calculator.getResult()));
		urMngr.undo();
		expected = 0;
		assertThat(expected, is(calculator.getResult()));
		urMngr.redo();
		expected = plusValue - minusValue;
		assertThat(expected, is(calculator.getResult()));
	}

	@Test
	public final void testDoItCommand() throws Exception {
		ccmd.doIt(plus);
		ccmd.doIt(minus);
		urMngr.doIt(ccmd);
		int expected = plusValue-minusValue; 
		assertThat(expected, is(calculator.getResult()));
		urMngr.doIt(plus);
		expected += plusValue;
		assertThat(expected, is(calculator.getResult()));	}

}
