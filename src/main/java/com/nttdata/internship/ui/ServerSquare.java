package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerSquare extends JPanel implements KeyListener {

	static ServerSocket server;
	static Socket socket;
	static int port = 2222;
	private static final long  serialVersionUID = 1L;

	private int x = 0;
	private int y = 0;

	static class ClientShape {
		private int x;
		private int y;
	}

	private ClientShape client;

	public ServerSquare() {
		setFocusable(true);
		addKeyListener(this);

		setFocusTraversalKeysEnabled(false);

	}
/*
	public String[] receiveData(DataInputStream in) throws IOException {

		return in.readUTF().trim().split(",");

	}
*/
	public ServerSquare receiveData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return (ServerSquare) in.readObject();
		
	}
	
	public void sendingDataToClient(ServerSquare ss) throws IOException {
		if (socket != null) {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			// out.writeUTF(sir); write clientShape Object
			out.writeObject(ss);
			out.flush();
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.fill(new Rectangle2D.Double(x, y, 50, 50));
		if (client != null) {
			g2.fill(new RoundRectangle2D.Double(client.x, client.y, 50, 50, 0, 0));
		}

	}

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame();

		ServerSquare ssquare = new ServerSquare();
		f.add(ssquare);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);

		Thread thread = new Thread(() -> {

			try {

				server = new ServerSocket();
				server.bind(new InetSocketAddress("localhost", port));
				socket = server.accept();
				ssquare.client = new ClientShape();
				while (true) {
					//DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					//String coords[] = ssquare.receiveData(in);
					////ssquare.client.x = Integer.valueOf(coords[0]);
					//ssquare.client.y = Integer.valueOf(coords[1]);
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					ServerSquare ss =  ssquare.receiveData(in);
					ssquare.client.x = ss.x;
					ssquare.client.y = ss.y;
					System.out.println("x " + ssquare.client.x + " " + ssquare.client.y);
					ssquare.repaint();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		);
		thread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		paintImmediately(0, 0, 50, 50);

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			y -= 1;
		}
		if (code == KeyEvent.VK_DOWN) {
			y += 1;
		}
		if (code == KeyEvent.VK_LEFT) {
			x -= 1;
		}
		if (code == KeyEvent.VK_RIGHT) {
			x += 1;
		}
		if (x < 0 || x > 660)
			x = -x;
		if (y < 0 || y > 560)
			y = -y;
		try {
			ServerSquare sq = new ServerSquare();
			sq.x = x;
			sq.y = y;

			sendingDataToClient(sq);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
