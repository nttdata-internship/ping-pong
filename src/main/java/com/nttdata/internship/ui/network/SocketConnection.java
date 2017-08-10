package com.nttdata.internship.ui.network;

import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.panel.GamePanel;

public class SocketConnection extends Thread {

	static Socket socket = null;
	static int port = 2222;
	private GamePanel client;
	private ObjectInputStream in;

	public SocketConnection(GamePanel panel) {
		this.client = panel;
	}

	public void run() {
		try {
			socket = new Socket("localhost", port);

			while (true) {
				in = new ObjectInputStream(socket.getInputStream());

				processResponse((ArrayList<ObjectShape>) SocketUtil.readData(in));

				client.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void listenForConnection() {
		Thread clientReceiverThread = new Thread(new Runnable() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket();
					server.bind(new InetSocketAddress("localhost", port));
					socket = server.accept();
					client.setOutputStream(socket.getOutputStream());
					while (true) {
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						ObjectShape coords = (ObjectShape) SocketUtil.readData(in);
						client.setClientPaddle(coords);
						
						client.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		);
		clientReceiverThread.start();
	}

	private void processResponse(ArrayList<ObjectShape> objects) {
		for (ObjectShape coords : objects) {

			if (coords instanceof Ball) {

				client.getBall().setX(coords.getX());
				client.getBall().setY(coords.getY());
			} else {
				ObjectShape clientPaddle = new ObjectShape();
				clientPaddle.setX(coords.getX());
				clientPaddle.setY(coords.getY());
				client.setClientPaddle(clientPaddle);

			}

		}
	}

}
