package com.nttdata.internship.ui.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Ball extends ObjectShape {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public transient Dimension frameSize;
	public int speedX = 20;
	public int speedY = 2;

	public Ball(Dimension frameSize) {
		x = 340;
		y = 275;
		this.frameSize = frameSize;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, 20, 20);
	}

	public boolean checkObjectCollision(ObjectShape paddle) {
		if (x <= 40) {
			if (y >= paddle.getY() && y <= paddle.getY() + 80) {
				speedX = -speedX;
				return true;
			}
			else {
				return false;
			}

		}
		return true;
	}

	public void move() {
		x += speedX;
		y += speedY;

		if (x < -1) {
			speedX = -speedX;

		}
		if (x > frameSize.getWidth() - 40) {
			speedX = -speedX;
		}
		if (y < -1 || y + 60 >= frameSize.getHeight()) {
			// speedY = -speedY;
		}

	}

	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

}
