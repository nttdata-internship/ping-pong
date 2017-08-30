package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.SocketConnection;

import dataBase.DatabaseUtil;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8592774932261018198L;
	protected Ball ball;
	protected ObjectShape paddle;
	protected ObjectShape clientPaddle;
	protected OutputStream os;
	private BufferedImage img;
	protected GAME_STATUS gameStatus = GAME_STATUS.NEW;
	private SocketConnection socket;
	private int rowId;
	private int serverScore;
	private int clientScore;
	private BufferedImage ballAnim;

	public static enum GAME_STATUS {
		RUNNING("The game is running..."), PAUSED("The game is paused..."), NEW("Press SPACE to START"), LOOSE(
				"Awww...you've lost"), WIN("Great job, you won!"), RESUME("Press SPACE to RESUME");

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
		try {
			img = ImageIO.read(this.getClass().getClassLoader().getResource("bg.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}

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
		g.drawImage(img, 0, 0, this);

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

	protected List<Integer> getScore() {
		List<Integer> scoreList = new ArrayList<>();
		try {
			//
			Connection con = DatabaseUtil.getConnection();
			String scoreSelect = "select host_score, client_score from score where id=?";
			PreparedStatement st = con.prepareStatement(scoreSelect);
			st.setInt(1, this.getRowId());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {

				scoreList.add(rs.getInt(1));
				scoreList.add(rs.getInt(2));

			}
			rs.close();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
		return scoreList;

	}

	protected void paintScore(List<Integer> score, Graphics g) {
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
		g.drawString(score.get(0) + "-" + score.get(1), 300, 25);
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

	public void setRowId(int i) {
		this.rowId = i;
	}

	public int getRowId() {
		return rowId;
	}

}
