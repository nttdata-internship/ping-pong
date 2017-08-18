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
	private Ball ball;
	protected ObjectShape paddle;

	protected ObjectShape clientPaddle;
	protected OutputStream os;

	protected GAME_STATUS gameStatus = GAME_STATUS.NEW;

	public static enum GAME_STATUS {
		
		RUNNING("Game running"), PAUSED("Game paused"), NEW("New Game, Press space to start"), LOOSE("You loose"), WIN(
				"You win");

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
		setBackground(Color.pink);

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

}
