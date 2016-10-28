package de.gerdhirsch.util.undoredo.test;

import de.gerdhirsch.util.undoredo.Command;
import de.gerdhirsch.util.undoredo.CommandException;

public class Plus implements Command, Cloneable {

	private Calculator calculator;
	private int summand;
	/**
	 * for test purposes let the command throw an exception
	 */
	static boolean throwException = false;
	static int throwAtTimes = 0;
	
	Plus(Calculator calculator, int summand){
		this.calculator = calculator;
		this.summand = summand;
	}
	
	@Override
	public void doIt() throws Exception {
		if(throwException) 
			if(throwAtTimes <= 0)
				throw new CommandException("Plus.doIt()", this);
			else
				--throwAtTimes;
		
		calculator.plus(summand);
	}

	@Override
	public void undo() throws Exception {
		if(throwException) 
			if(throwAtTimes <= 0)
				throw new CommandException("Plus.doIt()", this);
			else
				--throwAtTimes;
		
		calculator.minus(summand);
	}
	
	@Override
	public Plus clone() throws CloneNotSupportedException{
		return (Plus) super.clone();
	}
}
