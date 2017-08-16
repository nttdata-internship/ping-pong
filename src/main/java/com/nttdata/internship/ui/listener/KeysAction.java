package com.nttdata.internship.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import com.nttdata.internship.ui.animation.BallAnimation;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.ServerPanel;

public class KeysAction implements KeyListener {

	private GamePanel gamePanel;
	private static final int SPEED_INCREMENT = 10;
	private BallAnimation animationThread;

	public KeysAction(GamePanel server) {
		this.gamePanel = server;
		animationThread = new BallAnimation(gamePanel);

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
				gamePanel.setGameStarted(true);
				if (gamePanel instanceof ServerPanel) {
					animationThread.start();
				}
			} else {
				gamePanel.setGameStarted(false);
				if (gamePanel instanceof ServerPanel) {
					try {
						animationThread.join();
						animationThread = new BallAnimation(gamePanel);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

		}

		if (code == KeyEvent.VK_UP)

		{
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
