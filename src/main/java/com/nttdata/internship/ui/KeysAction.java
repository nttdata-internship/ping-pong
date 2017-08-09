package com.nttdata.internship.ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeysAction implements KeyListener {

	private ObjectShape paddle;
	private Ball ball;
	private ServerSquare server;
	private static final int SPEED_INCREMENT = 10;
	private boolean gameStarted = false;
	private Data connection;

	public KeysAction(Data connection, ServerSquare server) {
		this.paddle = new ObjectShape();
		this.connection = connection;
		this.server = server;
		this.ball = server.getBall();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int prevX = paddle.getX();
		int prevY = paddle.getY();

		if (connection.shape != null) {
			if (code == KeyEvent.VK_SPACE) {
				if (!gameStarted) {
					gameStarted = true;
					new BallAnimation(ball, connection, paddle, server).start();
				} else {
					gameStarted = false;
				}
			}
		}

		if (code == KeyEvent.VK_UP) {
			paddle.setY(paddle.getY() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_DOWN) {
			paddle.setY(paddle.getY() + SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_LEFT) {
			paddle.setX(paddle.getX() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_RIGHT) {
			paddle.setX(paddle.getX() + SPEED_INCREMENT);
		}

		if (code == KeyEvent.VK_SPACE) {
			gameStarted = !gameStarted;
		}

		if (paddle.getX() < 0 || paddle.getX() > ServerSquare.frameSize.getWidth() - 75) {
			paddle.setX(prevX);

		} else if (paddle.getY() < 0 || paddle.getY() > ServerSquare.frameSize.getHeight() - 75) {
			paddle.setY(prevY);
		}

		server.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
