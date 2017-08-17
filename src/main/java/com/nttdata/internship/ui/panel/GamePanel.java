package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	protected boolean gameStarted = false;

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
		Graphics2D g2 = (Graphics2D) g;
		
	

		if (ball != null) {
			g.setColor(Color.WHITE);
			ball.draw(g);
		}

		

		if (!gameStarted) {
			paintWelcomeMessage(g);
		}

	}

	public OutputStream getOutputStream() {
		return os;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.os = outputStream;

	}

	protected void paintWelcomeMessage(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Press SPACE to start", 250, 200);
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public void stopGame() {
		this.gameStarted = false;
	}

	public void startGame() {
		this.gameStarted = true;

	}

}
