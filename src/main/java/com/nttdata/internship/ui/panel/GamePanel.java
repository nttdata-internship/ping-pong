package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;

import dataBase.Driver;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8592774932261018198L;
	protected Ball ball;
	protected ObjectShape paddle;
	protected ObjectShape clientPaddle;
	protected OutputStream os;
	private int scoreC = 0;
	protected int scoreS = 0;
	protected GAME_STATUS gameStatus = GAME_STATUS.NEW;
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement st = null;

	public static enum GAME_STATUS {
		RUNNING("Game running..."), PAUSED("Game paused."), NEW("Press SPACE to start"), LOOSE("You've lost!"), WIN(
				"You won!"), RESUME("Continue game.");

		protected String message;

		private GAME_STATUS(String message) {
			//
			this.message = message;

		}

		public String getMessage() {
			return message;
		}
	}

	public GamePanel() {
		this.paddle = new ObjectShape();
		this.ball = new Ball(ServerPanel.frameSize);
		this.clientPaddle = new ObjectShape();
		con = Driver.DB();

	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public ObjectShape getPaddle() {
		return paddle;
	}

	public void setPaddle(ObjectShape paddle) {
		this.paddle = paddle;
	}

	public void setClientPaddle(ObjectShape clientPaddle) {
		this.clientPaddle = clientPaddle;
	}

	public ObjectShape getClientPaddle() {
		return clientPaddle;

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);

		if (ball != null) {
			g.setColor(Color.WHITE);
			ball.draw(g);
		}

		if (gameStatus != GAME_STATUS.RUNNING) {
			paintMessage(g, gameStatus.message);
		}

	}

	public OutputStream getOutputStream() {
		return os;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.os = outputStream;

	}

	protected void paintMessage(Graphics g, String message) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(message, 250, 200);
	}

	protected void paintScore(Graphics g, String message) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 24));

		try {

			String sql = "select score from Score where id=1";
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.getRow() != 0) {
				int scoreS = rs.getInt(1);
			}

			String clientScore = "select score from Score where id=2";
			st = con.prepareStatement(clientScore);
			rs = st.executeQuery();
			if (rs.getRow() != 0) {
				int scoreC = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				st.close();
			} catch (Exception e) {
			}
		}
		g.drawString(scoreS + "-" + scoreC, 300, 25);
	}

	public boolean isGameStarted() {
		return gameStatus == GAME_STATUS.RUNNING;
	}

	public GAME_STATUS getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GAME_STATUS gameStarted) {
		this.gameStatus = gameStarted;
	}

	public void stopGame() {
		this.gameStatus = GAME_STATUS.PAUSED;
	}

	public void startGame() {
		this.gameStatus = GAME_STATUS.RUNNING;

	}

	public int getScoreS() {
		return scoreS;
	}

	public void setScoreS(int scoreC) {
		this.scoreS = scoreC;
	}

	public int getScoreC() {
		return scoreC;
	}

	public void setScoreC(int scoreC) {
		this.scoreC = scoreC;
	}

}
