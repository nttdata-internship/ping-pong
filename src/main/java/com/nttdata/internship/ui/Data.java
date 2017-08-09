package com.nttdata.internship.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Data {

	static ServerSocket server;
	protected Socket socket = null;
	static int port = 2222;
	private ObjectOutputStream out;
	private Ball ball;
	protected ObjectShape shape;
	private ServerSquare square;

	public Object receiveData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return in.readObject();

	}

	public void sendingDataToClient(ArrayList<ObjectShape> list) throws IOException {
		// if (socket != null) {
		out = new ObjectOutputStream(socket.getOutputStream());

		// ArrayList<ObjectShape> list = new ArrayList<>();
		// list.add(ss);
		// if (ball != null) {
		// list.add(ball);
		// }

		out.writeObject(list);
		out.flush();
	}

	public void listenForConnection() {
		Thread clientReceiverThread = new Thread(new Runnable() {
			public void run() {
				try {
					server = new ServerSocket();
					server.bind(new InetSocketAddress("localhost", port));
					socket = server.accept();
					shape = new ObjectShape();

					while (true) {
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						ObjectShape coords = (ObjectShape) receiveData(in);
						shape.setX(coords.getX());
						shape.setY(coords.getY());

						square.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		);
		clientReceiverThread.start();
	}

}
