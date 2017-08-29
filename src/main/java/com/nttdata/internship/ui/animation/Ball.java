package com.nttdata.internship.ui.animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

/**
 * 
 * @author ioana.constantin
 *
 */
public class Ball extends ObjectShape implements ImageObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public transient Dimension frameSize;
	protected transient BufferedImage ballAnim;
	public double speedX = getRandomSpeed() * getRandomDirection();
	public double speedY = getRandomSpeed() * getRandomDirection();

	public Ball(Dimension frameSize) {
		x = 340;
		y = 275;
		this.frameSize = frameSize;
		try {
			ballAnim = ImageIO.read(
					this.getClass().getClassLoader().getResource("ball.png")
					/*new File("/maps/src/main/resources/ball.png")*/
					);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The ball is not loading.");
		}
	}

	public void draw(Graphics g) {
		g.drawImage(ballAnim, x, y, this);
	}

	public double getRandomSpeed() {
		return (Math.random() * 4 + 3);
	}

	public int getRandomDirection() {
		int rand = (int) (Math.random() * 2 + 6);
		if (rand == 1) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * checks if the player wins or looses the ball
	 * @param paddle is server player
	 * @param clientPaddle is client player
	 * @return
	 */
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
	
	/**
	 * 
	 * Change the direction of the ball when it hits the wall
	 */
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
