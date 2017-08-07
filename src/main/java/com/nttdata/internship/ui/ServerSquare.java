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
	private static Ball b1;


	static Dimension frameSize = new Dimension(640, 560);

	public ServerSquare() {
		shape = new ObjectShape();
		b1 = new Ball();
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
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(ss);
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
			g2.setColor(Color.red);
			g2.fill(new Ellipse2D.Double(shape.getX(), shape.getY(), length, width));
			
		}
		if(b1.getX() < -10 || b1.getX() > 610) {
			g.setColor(Color.red);
//			g.drawString("Game over", 350, 250);
			System.out.println("Collision detected: " + b1.getX() + b1.getY());
		} else {
			b1.draw(g);
		}

	}

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame();
		f.setTitle("SERVER");
		final ServerSquare ssquare = new ServerSquare();
		f.add(ssquare);
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

		Thread thread = new Thread(new Runnable() {
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
		thread.start();
		//square.sendingDataToClient(ssquare.shape);
		
		Thread thread2 = new Thread(
				new Runnable() {
					public void run() {
						try {
							ssquare.shape = new ObjectShape();
							
							
							while(true) {
								b1.move();
								b1.ballCollision();
								ssquare.repaint();
								Thread.sleep(60);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				});
		thread2.start();
	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		int code = e.getKeyCode();
		int prevX = x;
		int prevY = y;
		boolean ox = false, oy = false;
		if (code == KeyEvent.VK_UP) {
			y -= SPEED_INCREMENT;
			oy = true;
		}
		if (code == KeyEvent.VK_DOWN) {
			y += SPEED_INCREMENT;
			oy = true;
		}
		if (code == KeyEvent.VK_LEFT) {
			x -= SPEED_INCREMENT;
			ox = true;
		}
		if (code == KeyEvent.VK_RIGHT) {
			x += SPEED_INCREMENT;
			ox = true;
		}

		if (x < 0 || x > frameSize.getWidth() - 75) {
			x = prevX;

		} else if (y < 0 || y > frameSize.getHeight() - 75) {
			y = prevY;

		}
		

		
		try {
			ObjectShape sq = new ObjectShape();
			sq.setX(x);
			sq.setY(y);
			sendingDataToClient(sq);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		repaint();

	}
	public boolean collision() {
		float cathetusX = Math.abs(shape.getX() - x - width / 2);
		float cathetusY = Math.abs(shape.getY() - y - length / 2);

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