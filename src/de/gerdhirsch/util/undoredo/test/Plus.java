package de.gerdhirsch.util.undoredo.test;

import de.gerdhirsch.util.undoredo.Command;

public class Plus implements Command, Cloneable {

	private Calculator calculator;
	private int summand;
	/**
	 * for test purposes let the command throw an exception
	 */
	static boolean throwException = false;
	
	Plus(Calculator calculator, int summand){
		this.calculator = calculator;
		this.summand = summand;
	}
	@Override
	public void doIt() throws Exception {
		if(throwException) throw new Exception("Plus.doIt()");
		calculator.plus(summand);
	}

	@Override
	public void undo() throws Exception {
		if(throwException) throw new Exception("Plus.doIt()");
		calculator.minus(summand);
	}
	public Plus clone() throws CloneNotSupportedException{
		return (Plus) super.clone();
	}
}
