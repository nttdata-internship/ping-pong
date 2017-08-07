package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientSquare extends JPanel implements KeyListener, Serializable {

	private static final long serialVersionUID = 1L;
	private static final int SPEED_INCREMENT = 5;
	static Socket socket = null;
	static int port = 2222;

	private int x = 0;
	private int y = 0;
	private int length = 50;
	private int width = 50;

	private ObjectShape shape;

	boolean isDown = false;
	int startX;
	int startY;

	private static Dimension frameSize = new Dimension(700, 600);

	static ClientSquare cs;

	public ClientSquare() {
		shape = new ObjectShape();
		setFocusable(true);
		addKeyListener(this);
		setPreferredSize(frameSize);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.fill(new Ellipse2D.Double(x, y, 50, 50));
		if (shape != null) {
			g2.setColor(Color.blue);
			g2.fill(new Rectangle2D.Double(shape.getX(), shape.getY(), length, width));
		}

	}

	public void sendingDataToServer(ObjectShape ss) throws IOException {
		if (socket != null) {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(ss);
			out.flush();
		}

	}

	public Object receiveData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return in.readObject();

	}

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		f.setTitle("CLIENT");
		cs = new ClientSquare();
		f.add(cs);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				frameSize = e.getComponent().getSize();

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

		Thread thread = new Thread(

		new Runnable() {
			public void run() {
				try {
					socket = new Socket("localhost", port);
					cs.shape = new ObjectShape();

					while (true) {
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						ObjectShape coords = (ObjectShape) cs.receiveData(in);
						cs.shape.setX(coords.getX());
						cs.shape.setY(coords.getY());
						System.out.println("x " + cs.shape.getX() + " " + cs.shape.getY());
						cs.repaint();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
		// cs.sendingDataToServer(cs.shape);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		float dx = startX - shape.getX();
		float dy = startY - shape.getY();
		isDown = (dx * dx + dy * dy < shape.getRadius() * shape.getRadius());
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		int prevX = x;
		int prevY = y;
		//boolean ox = true;
		//boolean oy = true;

		if (code == KeyEvent.VK_UP) {
			y -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_DOWN) {
			y += SPEED_INCREMENT;
			//startY = y - prevY;
		}
		if (code == KeyEvent.VK_LEFT) {
			x -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_RIGHT) {
			x += SPEED_INCREMENT;
		}

		//int dx = startX - shape.getX();
		//int dy = startY - shape.getY();
		//isDown = (dx * dx + dy * dy < shape.getRadius() * shape.getRadius());

		if (x < 0 || x > frameSize.getWidth() - 75) {
			x = prevX;

		} else if (y < 0 || y > frameSize.getHeight() - 75) {
			y = prevY;

		}

		//shape.setX(shape.getX() + dx);
		//shape.setY(shape.getY() + dy);

		if (collision()) {
			System.out.println("s-au ciocnit");

		} else {
			System.out.println("nu s-au ciocnit");
		}

		try {
			ObjectShape sq = new ObjectShape();
			sq.setX(x);
			sq.setY(y);
			sendingDataToServer(sq);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// }

		cs.repaint();
	}

	public boolean collision() {
		float distX = Math.abs(shape.getX() - x - width / 2);
		float distY = Math.abs(shape.getY() - y - length / 2);

		if (distX > (width / 2 + shape.getRadius())) {
			return false;
		}
		if (distY > (length / 2 + shape.getRadius())) {
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
		return (dx * dx + dy * dy <= (shape.getRadius() * shape.getRadius()));

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}