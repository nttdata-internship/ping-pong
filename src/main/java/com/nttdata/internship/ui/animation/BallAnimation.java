package com.nttdata.internship.ui.animation;

import java.util.ArrayList;

import com.nttdata.internship.ui.network.SocketUtil;
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

			while (true) {
				ball.move();
				ball.ballCollision();
				// ball.checkObjectCollision(shape.getX(),
				// shape.getY());

				ObjectShape ballShape = new Ball(null);
				ballShape.setX(ball.getX());
				ballShape.setY(ball.getY());
				ObjectShape paddle = new ObjectShape();
				paddle.setX(paddle.getX());
				paddle.setY(paddle.getY());
				ArrayList<ObjectShape> objectsToSend = new ArrayList<>();
				objectsToSend.add(paddle);
				objectsToSend.add(ballShape);

				SocketUtil.sendDataToServer(panel.getOutputStream(), objectsToSend);
				// data.sendDataToClient(objectsToSend);

				//
				// if (server.collision()) {
				// System.out.println("s-au ciocnit");
				// } else
				// System.out.println("nu s-au ciocnit");
				panel.repaint();
				Thread.sleep(60);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
