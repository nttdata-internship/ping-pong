package com.nttdata.internship.maps.threads;

class SharedCounter {

	protected int counter = new Integer(0);

	public int increment() {
		return counter++;
	}

	public int getValue() {
		return counter;
	}

}
