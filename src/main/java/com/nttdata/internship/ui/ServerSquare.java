package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerSquare extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int x = 0;
	protected int y = 0;
	private int length = 50;
	private int width = 50;
	private boolean gameStarted = false;

	protected ObjectShape shape;
	private Ball ball;

	static Dimension frameSize = new Dimension(640, 560);

	public ServerSquare() {

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setPreferredSize(frameSize);
		JFrame f = new JFrame();
		f.setTitle("SERVER");
		f.add(this);
		f.setVisible(true);
		ball = new Ball(frameSize);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {
				frameSize = e.getComponent().getSize();
				ball.setFrameSize(frameSize);

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		f.pack();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.pink);
		if (!gameStarted) {
			g.setColor(Color.RED);
			g.drawString("Press SPACE to play", 250, 200);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		g2.fill(new Rectangle2D.Double(x, y, 50, 50));
		// TODO rezolva NPE de dedesubt
		if (shape != null) {
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(frameSize.getWidth() - 2 * length, 0 + shape.getY(), length, width));

		}

		ball.draw(g);

	}

	public boolean collision() {
		float distX = Math.abs(ball.getX() - x - width / 2);
		float distY = Math.abs(ball.getY() - y - length / 2);

		if (distX > (width / 2 + ball.getRadius())) {
			return false;
		}
		if (distY > (length / 2 + ball.getRadius())) {
			return false;
		}

		if (distX <= (width / 2)) {
			return true;
		}
		if (distY <= (length / 2)) {
			return true;
		}

		float dx = distX - width / 2;
		float dy = distY - length / 2;
		return (dx * dx + dy * dy <= (ball.getRadius() * ball.getRadius()));

	}

	

}