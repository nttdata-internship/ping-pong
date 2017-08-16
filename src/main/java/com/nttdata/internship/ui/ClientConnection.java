package com.nttdata.internship.ui;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection extends Thread {

	static Socket socket;
	static int port = 2222;
	private Ball ball;
	private ClientSquare client;
	ObjectShape shape ;
	private ObjectInputStream in;

	public ClientConnection(Ball ball, ClientSquare client) {
		this.client = client;
		this.ball = ball;
	}

	public void run() {
		try {
			ClientSquare.socket = new Socket("localhost", port);
			shape = new ObjectShape();

			while (true) {
				in = new ObjectInputStream(ClientSquare.socket.getInputStream());
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
				shape.setX(coords.getX());
				shape.setY(coords.getY());

			}

		}
	}

}
