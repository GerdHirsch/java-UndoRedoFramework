package de.gerdhirsch.util.undoredo.test;

import de.gerdhirsch.util.undoredo.Command;

public class Plus implements Command, Cloneable {

	private Calculator calculator;
	private int summand;
	Plus(Calculator calculator, int summand){
		this.calculator = calculator;
		this.summand = summand;
	}
	@Override
	public void doIt() throws Exception {
		calculator.plus(summand);
	}

	@Override
	public void undo() throws Exception {
		calculator.minus(summand);
	}
	public Plus clone() throws CloneNotSupportedException{
		return (Plus) super.clone();
	}
}
