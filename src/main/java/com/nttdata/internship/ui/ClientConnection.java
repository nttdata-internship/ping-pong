package com.nttdata.internship.ui;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection extends Thread {

	static Socket socket = null;
	static int port = 2222;
	private Ball ball;
	private ClientSquare client;
	private ObjectInputStream in;

	public ClientConnection(Ball ball, ClientSquare client) {
		this.client = client;
		this.ball = ball;
	}

	public void run() {
		try {
			socket = new Socket("localhost", port);
			client.shape = new ObjectShape();

			while (true) {
				in = new ObjectInputStream(socket.getInputStream());

				processResponse((ArrayList<ObjectShape>) SocketUtil.readData(in));

				client.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void processResponse(ArrayList<ObjectShape> objects) {
		for (ObjectShape coords : objects) {

			if (coords instanceof Ball) {

				ball.setX(coords.getX());
				ball.setY(coords.getY());
				System.out.println("Sending data to server, ball x=" + ball.getX() + " y=" + ball.getY());
			} else {
				client.shape.setX(coords.getX());
				client.shape.setY(coords.getY());

			}

		}
	}

}
