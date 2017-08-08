package com.nttdata.internship.ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysAction implements KeyListener {

	private ObjectShape shape;
	private Ball ball;
	private ServerSquare server;
	private static final int SPEED_INCREMENT = 10;
	private static Thread ballAnimation;
	private boolean gameStarted = false;
	private Data connection;

	public KeysAction(Data connection, ServerSquare server) {
		this.shape = new ObjectShape();
		this.connection = connection;
		this.server = server;
		this.ball = new Ball(ServerSquare.frameSize);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int prevX = shape.getX();
		int prevY = shape.getY();

		if (connection.shape != null) {
			if (code == KeyEvent.VK_SPACE) {
				ballAnimation = new Thread(new Runnable() {
					public void run() {
						try {

							while (gameStarted) {
								ball.move();
								ball.ballCollision();
								ball.checkObjectCollision(shape.getX(), shape.getY());
								ObjectShape currentRect = new ObjectShape();
								currentRect.setX(shape.getX());
								currentRect.setY(shape.getY());
								connection.sendingDataToClient(currentRect);

								if (server.collision()) {
									System.out.println("s-au ciocnit");
								} else
									System.out.println("nu s-au ciocnit");
								server.repaint();
								Thread.sleep(60);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				ballAnimation.start();
			}
		}

		if (code == KeyEvent.VK_UP) {
			shape.setY(shape.getY() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_DOWN) {
			shape.setY(shape.getY() + SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_LEFT) {
			shape.setX(shape.getX() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_RIGHT) {
			shape.setX(shape.getX() + SPEED_INCREMENT);
		}

		if (code == KeyEvent.VK_SPACE) {
			gameStarted = !gameStarted;
		}

		if (shape.getX() < 0 || shape.getX() > ServerSquare.frameSize.getWidth() - 75) {
			shape.setX(prevX);

		} else if (shape.getY() < 0 || shape.getY() > ServerSquare.frameSize.getHeight() - 75) {
			shape.setY(prevY);
		}

		server.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
