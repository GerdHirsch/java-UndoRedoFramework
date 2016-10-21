package de.gerdhirsch.util.undoredo.test;

import de.gerdhirsch.util.undoredo.Command;

public class Minus implements Command {

	private Calculator calculator;
	private int subtrahend;
	public Minus(Calculator calculator, int subtrahend){
		this.calculator = calculator;
		this.subtrahend = subtrahend;
	}
	@Override
	public void doIt() throws Exception {
		calculator.minus(subtrahend);
	}

	@Override
	public void undo() throws Exception {
		calculator.plus(subtrahend);
	}
	public Minus clone() throws CloneNotSupportedException{
		return (Minus) super.clone();
	}
}
