package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.OutputStream;

import javax.swing.JPanel;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;

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
	private int scoreS = 0;
	protected GAME_STATUS gameStatus = GAME_STATUS.NEW;

	public static enum GAME_STATUS {
		RUNNING("Game running..."), PAUSED("Game paused."), NEW("Press SPACE to start"), LOOSE("You've lost!"), WIN(
				"You won!");

		private String message;

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
	//	int ok=0;
		if (ball != null) {
			g.setColor(Color.WHITE);
			ball.draw(g);
		}

		if (gameStatus != GAME_STATUS.RUNNING) {
			paintMessage(g, gameStatus.message);
		}

		if (gameStatus == GAME_STATUS.WIN){ //&& ok == 0) {
			//setScoreC(getScoreC() + 1);
			//ok = 1;
			paintScore(g, gameStatus.message);
		}

		if (gameStatus == GAME_STATUS.LOOSE ){//&& ok == 0) {
			paintScore(g, gameStatus.message);
			//ok = 1;
		}

		//if (ok == 1)
			//ok = 0;
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
		g.drawString( getScoreS()+"-" + getScoreC(), 300, 25);
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

	public int getScoreC() {
		return scoreC;
	}

	public void setScoreC(int scoreC) {
		this.scoreC = scoreC;
	}

	public int getScoreS() {
		return scoreS;
	}

	public void setScoreS(int scoreS) {
		this.scoreS = scoreS;
	}

}
