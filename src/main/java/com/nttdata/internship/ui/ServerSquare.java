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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerSquare extends JPanel implements KeyListener, Serializable {

	private static final int SPEED_INCREMENT = 10;
	static ServerSocket server;
	static Socket socket = null;
	static int port = 2222;
	private static final long serialVersionUID = 1L;

	private int x = 0;
	private int y = 0;
	private int length = 50;
	private int width = 50;

	private ObjectShape shape;
	private static Ball ball;

	private static Thread ballAnimation;

	private ObjectOutputStream out;

	static Dimension frameSize = new Dimension(640, 560);

	public ServerSquare() {

		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		setPreferredSize(frameSize);

	}

	public Object receiveData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return in.readObject();

	}

	public void sendingDataToClient(ObjectShape ss) throws IOException {
		if (socket != null) {
			out = new ObjectOutputStream(socket.getOutputStream());

			ArrayList<ObjectShape> list = new ArrayList<>();
			list.add(ss);
			if (ball != null) {
				System.out.println("Sending data to server, ball x=" + ball.getX() + " y=" + ball.getY());
				list.add(ball);
			}

			out.writeObject(list);
			out.flush();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		g2.fill(new Rectangle2D.Double(x, y, 50, 50));
		if (shape != null) {
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(frameSize.getWidth() - 2 * length, 0 + shape.getY(), length, width));

		}

		ball.draw(g);

	}

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame();
		f.setTitle("SERVER");
		final ServerSquare ssquare = new ServerSquare();
		f.add(ssquare);
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

		// TODO move this in another class
		Thread clientReceiverThread = new Thread(new Runnable() {
			public void run() {
				try {
					server = new ServerSocket();
					server.bind(new InetSocketAddress("localhost", port));
					socket = server.accept();
					ssquare.shape = new ObjectShape();

					while (true) {
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						ObjectShape coords = (ObjectShape) ssquare.receiveData(in);
						ssquare.shape.setX(coords.getX());
						ssquare.shape.setY(coords.getY());

						System.out.println(" Sending x " + ssquare.shape.getX() + " y" + ssquare.shape.getY());

						ssquare.repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		);
		clientReceiverThread.start();

		// TODO move this in another class - END

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		int prevX = x;
		int prevY = y;

		if (shape != null) {
			if (code == KeyEvent.VK_SPACE && ballAnimation == null) {
				ballAnimation = new Thread(new Runnable() {
					public void run() {
						try {

							while (true) {
								ball.move();
								ball.ballCollision();
								ObjectShape currentRect = new ObjectShape();
								currentRect.setX(x);
								currentRect.setY(y);
								sendingDataToClient(currentRect);
								repaint();

								Thread.sleep(60);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

				ballAnimation.start();
			}
		}

		if (code == KeyEvent.VK_UP) {
			y -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_DOWN) {
			y += SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_LEFT) {
			x -= SPEED_INCREMENT;
		}
		if (code == KeyEvent.VK_RIGHT) {
			x += SPEED_INCREMENT;
		}

		if (x < 0 || x > frameSize.getWidth() - 75) {
			x = prevX;

		} else if (y < 0 || y > frameSize.getHeight() - 75) {
			y = prevY;
		}

		repaint();

	}

	public boolean collision() {
		// ???? functioneaza ???????????????????????????????
		float cathetusX = Math.abs(shape.getX() - ball.getX() - width / 2);
		float cathetusY = Math.abs(shape.getY() - ball.getY() - length / 2);

		if (cathetusX > (width / 2 + shape.getRadius())) {
			return false;
		}
		if (cathetusY > (length / 2 + shape.getRadius())) {
			return false;
		}

		if (cathetusX <= (width / 2)) {
			return true;
		}
		if (cathetusY <= (length / 2)) {
			return true;
		}

		float dx = cathetusX - width / 2;
		float dy = cathetusY - length / 2;
		return (dx * dx + dy * dy <= (shape.getRadius() * shape.getRadius()));

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}