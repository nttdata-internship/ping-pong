package com.nttdata.internship.ui.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

public class Ball extends ObjectShape implements ImageObserver, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public transient Dimension frameSize;
	public double speedX = getRandomSpeed() * getRandomDirection();
	public double speedY = getRandomSpeed() * getRandomDirection();
	protected transient BufferedImage ballImage;

	public Ball(Dimension frameSize) {
		x = 340;
		y = 275;
		this.frameSize = frameSize;
		try {
			ballImage = ImageIO.read(new File("C:\\Users\\stefan.neacsu\\Desktop\\ball.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		// g.setColor(Color.WHITE);
		// g.fillOval(x, y, 20, 20);
		g.drawImage(ballImage, 320, 250, this);
	}

	public double getRandomSpeed() {
		return (Math.random() * 4 + 2);
	}

	public int getRandomDirection() {
		int rand = (int) (Math.random() * 2);
		if (rand == 1) {
			return 1;
		} else {
			return -1;
		}
	}

	public GAME_STATUS checkObjectCollision(ObjectShape paddle, ObjectShape clientPaddle) {
		GAME_STATUS status = GAME_STATUS.RUNNING;
		if (x <= 20) {
			if (y >= paddle.getY() - 20 && y <= paddle.getY() + 100) {
				speedX = -speedX;
			} else {
				status = GAME_STATUS.LOOSE;

			}
		}
		if (x >= 600) {
			if (y >= clientPaddle.getY() - 20 && y <= clientPaddle.getY() + 100) {
				speedX = -speedX;
			} else {
				status = GAME_STATUS.WIN;

			}
		}

		return status;

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
			speedY = -speedY;
		}

	}

	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
