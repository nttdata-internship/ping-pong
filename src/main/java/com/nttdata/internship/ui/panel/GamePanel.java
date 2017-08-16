package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;

import javax.swing.JPanel;

import com.nttdata.internship.ui.animation.Ball;
import com.nttdata.internship.ui.animation.ObjectShape;

public class GamePanel extends JPanel {

	private Ball ball;
	private ObjectShape paddle;

	protected ObjectShape clientPaddle;
	private OutputStream os;
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
		if (paddle != null) {
			g2.setColor(Color.red);

			g2.fill(new Ellipse2D.Double(paddle.getX(), paddle.getY(), 50, 50));
		}

		if (ball != null) {
			g.setColor(Color.WHITE);
			ball.draw(g);
		}

		if (clientPaddle != null) {
			g2.setColor(Color.blue);
			g2.fill(new Rectangle2D.Double(ServerPanel.frameSize.getWidth() - 500, 0 + clientPaddle.getY(), 50, 50));
		}
/*
		if (!gameStarted) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("Press SPACE to start", 250, 200);
		}
*/
	}

	public OutputStream getOutputStream() {
		return os;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.os = outputStream;

	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public boolean setGameStarted(boolean gameStarted) {
		return this.gameStarted = gameStarted;
	}

}
// casadasdasdasdassdasdasdasdas
