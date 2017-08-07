package com.nttdata.internship.ui;

import java.io.Serializable;

public class ObjectShape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4271784931525424591L;
	private int x;
	private int y;
	private int radius;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	int getRadius() {
		return radius;
	}

	void setRadius(int raza) {
		this.radius = raza;
	}

}
