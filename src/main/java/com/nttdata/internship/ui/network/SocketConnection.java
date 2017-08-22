package com.nttdata.internship.ui.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

public class SocketConnection extends Thread {

	private Socket clientSocket = null;
	private GamePanel panel;

	static Properties gameProperties = new Properties();

	static {
		try {
			gameProperties.load(Thread.currentThread().getClass().getResourceAsStream("game.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SocketConnection(GamePanel panel) {
		this.panel = panel;
	}

	public void run() {
		try {
			clientSocket = new Socket(gameProperties.getProperty("game.host"), (int) gameProperties.get("game.port"));
			panel.setOutputStream(clientSocket.getOutputStream());
			while (true) {

				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				GameData receivedData = (GameData) SocketUtil.readData(in);
				panel.setGameStatus(receivedData.getGameStatus());
				panel.setScoreS(receivedData.getScore());
				System.out.println(panel.getScoreC() + " " + panel.getScoreS());
				if (GAME_STATUS.RUNNING == receivedData.getGameStatus()) {
					processResponse(receivedData);
					GameData sentData = new GameData();
					List<ObjectShape> paddle = new ArrayList<>();
					paddle.add(panel.getPaddle());
					sentData.setObjects(paddle);
					// sentData.setScore(panel.getScoreC());
					sentData.setGameStatus(panel.getGameStatus());
					SocketUtil.sendDataToServer(clientSocket.getOutputStream(), sentData);

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
				ServerSocket server = null;
				try {
					server = new ServerSocket();
					server.bind(new InetSocketAddress(gameProperties.getProperty("game.host"),
							(int) gameProperties.get("game.port")));
					clientSocket = server.accept();
					panel.setOutputStream(clientSocket.getOutputStream());
					while (true) {
						ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
						GameData gameData = (GameData) SocketUtil.readData(in);
						if (gameData != null && !gameData.getObjects().isEmpty()) {

							panel.setClientPaddle(gameData.getObjects().get(0));
						}
						if (panel.getGameStatus() != gameData.getGameStatus()) {
							if (gameData.isGameRunning()) {
								// panel.setScoreS(gameData.getScoreC());
								// panel.startGame();
							}

							panel.setGameStatus(gameData.getGameStatus());

						}
						panel.setScoreC(gameData.getScore());
						System.out.println(panel.getScoreS() + " -" + panel.getScoreC());
						panel.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (server != null)
							server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
