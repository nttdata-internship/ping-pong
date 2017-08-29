package com.nttdata.internship.ui.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.h2.tools.Server;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

import dataBase.DatabaseUtil;

/**
 * 
 *
 * the class connects the client to server
 *
 */
public class SocketConnection extends Thread {

	private Socket clientSocket = null;
	private GamePanel panel;

	static Properties gameProperties = new Properties();

	static {
		try {
			gameProperties.load(new java.io.FileReader("src/main/java/com/nttdata/internship/game.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SocketConnection(GamePanel panel) {
		this.panel = panel;
	}

	/**
	 * sends data from client to server(score, game status position of client)
	 */
	public void run() {
		try {
			clientSocket = new Socket(gameProperties.getProperty("game.host"),
					Integer.parseInt((String) gameProperties.get("game.port")));
			panel.setOutputStream(clientSocket.getOutputStream());

			while (true) {
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				GameData receivedData = (GameData) SocketUtil.readData(in);
				panel.setGameStatus(receivedData.getGameStatus());

				if (GAME_STATUS.RUNNING == receivedData.getGameStatus()) {
					processResponse(receivedData);
					GameData sentData = new GameData();
					List<ObjectShape> paddle = new ArrayList<>();
					paddle.add(panel.getPaddle());
					sentData.setObjects(paddle);
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

				PreparedStatement st = null;
				ServerSocket server = null;
				try {

					server = new ServerSocket();
					server.bind(new InetSocketAddress(gameProperties.getProperty("game.host"),
							Integer.parseInt((String) gameProperties.get("game.port"))));
					clientSocket = server.accept();

					final Connection con = DatabaseUtil.getConnection();

					String init = "delete from score";
					st = con.prepareStatement(init);
					st.execute();

					String insertData = "insert into score values(null,'server',0,'"
							+ gameProperties.getProperty("game.host") + "',0)";
					st = con.prepareStatement(insertData);
					st.executeUpdate();

					ResultSet rs = con.prepareStatement("select max(id) from score").executeQuery();
					panel.setRowId(rs.next() ? rs.getInt(1) : -1);
					con.close();

					panel.setOutputStream(clientSocket.getOutputStream());

					while (true) {
						ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
						GameData gameData = (GameData) SocketUtil.readData(in);

						if (gameData != null && !gameData.getObjects().isEmpty()) {
							panel.setClientPaddle(gameData.getObjects().get(0));
						}

						if (panel.getGameStatus() != gameData.getGameStatus()) {
							if (!gameData.isGameRunning()) {
								// panel.startGame();
							}
							panel.setGameStatus(gameData.getGameStatus());
						}

						panel.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (server != null)
							server.close();
					} catch (IOException e) {
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
