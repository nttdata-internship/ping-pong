package com.nttdata.internship.ui.network;

import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

public class SocketConnection extends Thread {

	private static Socket socket = null;
	private static int port = 2222;
	private GamePanel panel;
	private ObjectInputStream in;

	public SocketConnection(GamePanel panel) {
		this.panel = panel;
	}

	public void run() {
		try {
			socket = new Socket("localhost", port);
			panel.setOutputStream(socket.getOutputStream());
			while (true) {
				
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				GameData receivedData = (GameData) SocketUtil.readData(in);
				panel.setGameStatus(receivedData.getGameStatus());
				if (GAME_STATUS.RUNNING == receivedData.getGameStatus()) {
					processResponse(receivedData);
					GameData sentData = new GameData();
					List<ObjectShape> paddle = new ArrayList<>();
					paddle.add(panel.getPaddle());
					sentData.setObjects(paddle);
					// sentData.setGameScore(panel.getGameScore());
					sentData.setGameStatus(panel.getGameStatus());
					SocketUtil.sendDataToServer(socket.getOutputStream(), sentData);

				}

				panel.repaint();

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
					panel.setOutputStream(socket.getOutputStream());
					while (true) {
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						GameData gameData = (GameData) SocketUtil.readData(in);
						if (gameData != null && !gameData.getObjects().isEmpty()) {
							panel.setClientPaddle(gameData.getObjects().get(0));
						}
						if (panel.getGameStatus() != gameData.getGameStatus()) {
							if (gameData.isGameRunning()) {
								panel.startGame();
							}

							panel.setGameStatus(gameData.getGameStatus());

						}

						panel.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		);
		clientReceiverThread.start();
	}

	private void processResponse(GameData gameData) {
		if (gameData != null) {
			for (ObjectShape coords : gameData.getObjects()) {

				if (coords instanceof Ball) {

					panel.getBall().setX(coords.getX());
					panel.getBall().setY(coords.getY());
				} else {
					ObjectShape clientPaddle = new ObjectShape();
					clientPaddle.setX(coords.getX());
					clientPaddle.setY(coords.getY());
					panel.setClientPaddle(clientPaddle);

				}

			}

			panel.setGameStatus(gameData.getGameStatus());
		}

	}

}
