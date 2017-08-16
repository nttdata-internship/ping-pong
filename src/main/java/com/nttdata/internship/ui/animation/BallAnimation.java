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
				ball.ballCollision();

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

}
