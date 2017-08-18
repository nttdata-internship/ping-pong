package com.nttdata.internship.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.ServerPanel;

public class KeysAction implements KeyListener {

	private GamePanel gamePanel;
	private static final int SPEED_INCREMENT = 15;

	public KeysAction(GamePanel server) {
		this.gamePanel = server;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		int prevX = gamePanel.getPaddle().getX();
		int prevY = gamePanel.getPaddle().getY();

		if (code == KeyEvent.VK_SPACE) {
			if (!gamePanel.isGameStarted()) {
				gamePanel.startGame();

			} else {
				gamePanel.stopGame();
			}
		}

		if (code == KeyEvent.VK_UP) {
			gamePanel.getPaddle().setY(gamePanel.getPaddle().getY() - SPEED_INCREMENT);
		}
		if (code == KeyEvent.VK_DOWN) {
			gamePanel.getPaddle().setY(gamePanel.getPaddle().getY() + SPEED_INCREMENT);
		}

		if (gamePanel.getPaddle().getX() < 0 || gamePanel.getPaddle().getX() > ServerPanel.frameSize.getWidth() - 60) {
			gamePanel.getPaddle().setX(prevX);

		} else if (gamePanel.getPaddle().getY() < 0
				|| gamePanel.getPaddle().getY() > ServerPanel.frameSize.getHeight() - 90) {
			gamePanel.getPaddle().setY(prevY);
		}

		if (gamePanel.getPaddle().getY() < 0)
			gamePanel.getPaddle().setY(0);
		if (gamePanel.getPaddle().getY() > 480)
			gamePanel.getPaddle().setY(480);

		gamePanel.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
