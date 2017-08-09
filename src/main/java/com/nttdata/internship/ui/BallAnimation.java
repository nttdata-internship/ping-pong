package com.nttdata.internship.ui;

import java.util.ArrayList;

import javax.swing.JPanel;

public class BallAnimation extends Thread {

	private Ball ball;
	private Data data;
	private ObjectShape paddle;
	private JPanel panel;

	public BallAnimation(Ball ball, Data data, ObjectShape shape, JPanel panel) {
		this.ball = ball;
		this.data = data;
		this.paddle = shape;
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
				data.sendingDataToClient(objectsToSend);
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
