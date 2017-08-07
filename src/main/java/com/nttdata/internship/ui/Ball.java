package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Ball {
	double xVel, yVel, xb, yb;
	Dimension frameSize;
	
	public Ball() {
		xb = 350;
		yb = 250;
		xVel = 5;
		yVel = 5;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)xb-10, (int)yb-10, 20, 20);
	}
	
	public void ballCollision() {
		if (xb <= 50) {
			if (yb >= frameSize.getWidth() && yb <= frameSize.getHeight() + 80) {
				xVel = -xVel;
			}
			else if (xb >= 650) {
				if( yb >= frameSize.getHeight() && yb <= frameSize.getWidth() + 80) {
					xVel = - xVel;
				}
			}
		}
	}
	
	public void move() {
		xb += xVel;
		yb += yVel;
		
		if (yb < 10)
			yVel = -yVel;
		
		if(yb > 490) 
			yVel = -yVel;
	}
	
	public int getX() {
		return (int)xb;
		
	}
	
	public int getY() {
		return (int)yb;
	}

}
