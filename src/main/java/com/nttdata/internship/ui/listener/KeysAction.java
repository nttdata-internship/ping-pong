package com.nttdata.internship.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.nttdata.internship.ui.animation.BallAnimation;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.ServerPanel;

public class KeysAction implements KeyListener {

	private GamePanel gamePanel;
	private static final int SPEED_INCREMENT = 10;
	private boolean gameStarted = false;

	public KeysAction(GamePanel server) {
		this.gamePanel = server;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int prevX = gamePanel.getPaddle().getX();
		int prevY = gamePanel.getPaddle().getY();

		if (gamePanel instanceof ServerPanel) {
			if (code == KeyEvent.VK_SPACE) {
				if (!gamePanel.isGameStarted()) {
					gamePanel.setGameStarted(true);
					new BallAnimation(gamePanel).start();
				} else {
					gamePanel.setGameStarted(false);
				}
			}

			else

			{

			}

		}

		if (code == KeyEvent.VK_UP) {
			gamePanel.getPaddle().setY(gamePanel.getPaddle().getY() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_DOWN) {
			gamePanel.getPaddle().setY(gamePanel.getPaddle().getY() + SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_LEFT) {
			gamePanel.getPaddle().setX(gamePanel.getPaddle().getX() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_RIGHT) {
			gamePanel.getPaddle().setX(gamePanel.getPaddle().getX() + SPEED_INCREMENT);
		}

		if (code == KeyEvent.VK_SPACE) {
			gameStarted = !gameStarted;
		}

		if (gamePanel.getPaddle().getX() < 0 || gamePanel.getPaddle().getX() > ServerPanel.frameSize.getWidth() - 60) {
			gamePanel.getPaddle().setX(prevX);

		} else if (gamePanel.getPaddle().getY() < 0
				|| gamePanel.getPaddle().getY() > ServerPanel.frameSize.getHeight() - 90) {
			gamePanel.getPaddle().setY(prevY);
		}

		gamePanel.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
