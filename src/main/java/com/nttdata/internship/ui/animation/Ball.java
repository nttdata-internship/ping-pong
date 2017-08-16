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
	public int speedY = 20;

	public Ball(Dimension frameSize) {
		x = 340;
		y = 275;
		this.frameSize = frameSize;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		y = 0;
		g.fillOval(x, 100, 20, 20);
	}

	public void ballCollision() {
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

	public void checkObjectCollision(/* shape ?? */int objX, int objy) throws InterruptedException {
		if (x < 50 - 10 / 2 /* radius */) {

			// sus
			if (!(y > objy && y < objy + 50)) {

				try {
					Thread.sleep(60);
					System.out.println("ball x=" + x + " y= " + y);
					System.out.println("shape x=" + objX + " y= " + objy);
					x = 340;
					y = 275;

					// stop game
					// send i.e 0-my_score - 1 to oponent & stop game
					// play space to start again.

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

		// } else if (x >= 640 - 50) {
		// if (y >= shape.getY() && y <= shape.getY()) {
		// speedX = -speedX;
		// x = 340;
		// y = 275;
		// Thread.sleep(100);
		// }
		// }
	}

	public void move() {
		x += speedX;
		y += speedY;
	}

	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

}
