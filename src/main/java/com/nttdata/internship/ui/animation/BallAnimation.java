package com.nttdata.internship.ui.animation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.nttdata.internship.ui.network.SocketConnection;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;
import com.nttdata.internship.ui.panel.GamePanel;
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

import dataBase.DatabaseUtil;

/**
 * Syncs data between client and server(game status, score)
 * 
 * @author ioana.constantin
 *
 */
public class BallAnimation extends Thread {

	private Ball ball;
	private GamePanel panel;

	private Connection con = null;

	public BallAnimation(GamePanel panel) {
		this.ball = panel.getBall();
		this.panel = panel;
	}

	public void run() {
		try {
			PreparedStatement st = null;
			ResultSet rs = null;

			while (panel.isGameStarted()) {
				ball.move();
				GAME_STATUS status = ball.checkObjectCollision(panel.getPaddle(), panel.getClientPaddle());
				panel.setGameStatus(status);
				if (status == GAME_STATUS.RUNNING) {
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
					gameData.setGameStatus(panel.getGameStatus());
					// gameData.setScore(panel.getScoreS());

					SocketUtil.sendDataToServer(panel.getOutputStream(), gameData);
					panel.repaint();
				} else {
					ball.setX(280);
					ball.setY(280);
					panel.repaint();

					String winner_column = "host_score";
					if (status == GAME_STATUS.LOOSE) {
						winner_column = "client_score";
					}

					try {
						con = DatabaseUtil.getConnection();
						String sql = "update score SET " + winner_column + " = " + winner_column + "+ 1 where id = ?";
						st = con.prepareStatement(sql);
						st.setInt(1, panel.getRowId());
						st.executeUpdate();
						st.close();
						con.close();

					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {

					}

					break;
				}
				Thread.sleep(20);

			}

			ObjectShape ballShape = new Ball(null);
			ballShape.setX(ball.getX());
			ballShape.setY(ball.getY());

			ArrayList<ObjectShape> objectsToSend = new ArrayList<>();
			objectsToSend.add(ballShape);

			GameData gameData = new GameData();
			gameData.setObjects(objectsToSend);
			GAME_STATUS status = GAME_STATUS.WIN;
			if (panel.getGameStatus() == status) // {
				status = GAME_STATUS.LOOSE;
			// gameData.setScore(panel.getScoreS()+1);
			// }else
			// gameData.setScore(panel.getScoreS());

			gameData.setGameStatus(status);
			// gameData.setGameStatus(panel.getGameStatus());

			SocketUtil.sendDataToServer(panel.getOutputStream(), gameData);
			panel.repaint();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
