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
	private static final int SPEED_INCREMENT = 10;
	static Socket socket = null;
	static int port = 2222;

	private int x = 0;
	private int y = 0;

	private ObjectShape shape;

	private static Dimension frameSize = new Dimension(700, 600);

	static ClientSquare cs;

	public ClientSquare() {
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
			g2.fill(new Ellipse2D.Double(shape.getX(), shape.getY(), 50, 50));
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
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		
		int prevX = x;
		int prevY = y;
		int code = e.getKeyCode();
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
		
		float cathetusX = Math.abs(x - shape.getX());
		float cathetusY = Math.abs(y - shape.getY());
		
		float hypotenuse = (float) Math.sqrt(Math.pow(cathetusY, 2) + Math.pow(cathetusX, 2));
		System.out.println("hypo" + hypotenuse);
		// check window boundaries
		if ((x < 0 || x > frameSize.getWidth() - 75) || hypotenuse < 100) {
			x = prevX;
		} else if ((y < 0 || y > frameSize.getHeight() - 75 )|| hypotenuse < 100) {
			y = prevY;
		}
		
		try {
			// ClientSquare sq = new ClientSquare();
			// sq.x = x;
			// sq.y = y;
			ObjectShape shape = new ObjectShape();
			shape.setX(x);
			shape.setY(y);
			sendingDataToServer(shape);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// }

		cs.repaint();
		}
	

	@Override
	public void keyReleased(KeyEvent e) {

	}
}