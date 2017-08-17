package com.nttdata.internship.ui.animation;

import java.util.ArrayList;

import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;

public class BallAnimation extends Thread {

	private Ball ball;
	private GamePanel panel;

	public BallAnimation(GamePanel panel) {
		this.ball = panel.getBall();
		this.panel = panel;
	}

	public void run() {
		try {

   			while (panel.isGameStarted()) {
				ball.move();
				if (ball.checkObjectCollision(panel.getPaddle())) {
					
					ObjectShape ballShape = new Ball(null);
					ballShape.setX(ball.getX());
					ballShape.setY(ball.getY());
					ObjectShape paddle = new ObjectShape();
					paddle.setX(panel.getPaddle().getX());
					paddle.setY(panel.getPaddle().getY());
					ArrayList<ObjectShape> objectsToSend = new ArrayList<>();
					objectsToSend.add(paddle);
					objectsToSend.add(ballShape);

					// GameData.gameStatus, objects[],score 
					GameData gameData = new GameData();
					gameData.setObjects(objectsToSend);
					gameData.setGameRunning(panel.isGameStarted());

					SocketUtil.sendDataToServer(panel.getOutputStream(), gameData);
					panel.repaint();
				} else {
					ball.setX(280);
					ball.setY(280);
//					Thread.yield();
					break;
				}
				Thread.sleep(60);

			}

			ObjectShape ballShape = new Ball(null);
			ballShape.setX(ball.getX());
			ballShape.setY(ball.getY());

			ArrayList<ObjectShape> objectsToSend = new ArrayList<>();
			objectsToSend.add(ballShape);

			GameData gameData = new GameData();
			gameData.setObjects(objectsToSend);
			gameData.setGameRunning(panel.isGameStarted());

			SocketUtil.sendDataToServer(panel.getOutputStream(), gameData);

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkObjectCollision(int objX, int objy) throws InterruptedException {
		if (ball.getX() < 50 - 10 / 2 /* radius */) {

			// sus
			if (!(ball.getY() > objy && ball.getY() < objy + 50)) {

				try {
					Thread.sleep(60);
					System.out.println("shape x=" + objX + " y= " + objy);
					ball.setX(340);
					ball.setY(275);

					// stop game
					// send i.e 0-my_score - 1 to oponent & stop game
					// play space to start again.

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}

	public void checkObjectCollision(ObjectShape paddle, ObjectShape clientPaddle) {
		if (ball.x <= 50) {
			if (ball.y >= paddle.getY() && ball.y <= paddle.getY() + 80) {
				ball.speedX = -ball.speedX;
			} else if (ball.x >= 650) {
				if (ball.y >= clientPaddle.getY() && ball.y <= clientPaddle.getY() + 80) {
					ball.speedX = -ball.speedX;
				}

			}
		}
	}

}
