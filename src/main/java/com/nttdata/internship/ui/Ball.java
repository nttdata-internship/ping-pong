package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Ball extends ObjectShape {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Dimension frameSize;
	public int speedX = 10;
	public int speedY = 10;

	public Ball() {
	}

	public Ball(Dimension frameSize) {
		x = 340;
		y = 275;
		this.frameSize = frameSize;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, 20, 20);
	}

	public void ballCollision() {
		if (x < -1 ){
			speedX = 0;
			
		}
		if(x > frameSize.getWidth() - 40) {
			speedX = -speedX;
		}
		if (y < -1 || y + 60 >= frameSize.getHeight()) {
			speedY = -speedY;
		}
		
	}

	public void move() {
		setX(getX() + speedX);
		setY(getY() + speedY);
	}

	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

}
