package com.nttdata.internship.maps;

/**
 * Operation interface used for strategy pattern example.
 * 
 * @author ioana.constantin
 *
 */

interface Operatie {

	/**
	 * Strategy method for overriding.
	 * 
	 * @param operand
	 * @return The result of an operation. I.e
	 *         Subtraction/ Addition/ Division/ Multiplication.
	 */
	public int op(int operand);

	public boolean stop(int a, int b);

	void writeToFile();
}
