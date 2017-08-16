package com.nttdata.internship.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysAction implements KeyListener {

	private ObjectShape paddle;
	private Ball ball;
	private ServerSquare server;
	private static final int SPEED_INCREMENT = 10;
	private boolean gameStarted = false;
	private Data connection;

	public KeysAction(Data connection, ServerSquare server, ClientSquare client) {
		this.paddle = new ObjectShape();
		this.connection = connection;
		this.server = server;
		//this.ball = server.getBall();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int prevX = server.x;
		int prevY = server.y;

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
			server.y -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_DOWN) {
			server.y += SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_LEFT) {
			server.x -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_RIGHT) {
			server.x += SPEED_INCREMENT;
		}

		if (code == KeyEvent.VK_SPACE) {
			gameStarted = !gameStarted;
		}

		if (server.x < 0 || server.x > ServerSquare.frameSize.getWidth() - 75) {
			server.x = prevX;

		} else if (server.y < 0 || server.y > ServerSquare.frameSize.getHeight() - 75) {
			server.y = prevY;
		}

		server.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
