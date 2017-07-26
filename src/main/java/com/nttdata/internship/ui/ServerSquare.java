package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerSquare extends JPanel {

	static ServerSocket server;
	static Socket socket;
	static int port = 2222;

	private static Graphics g;
	private int x = 0;
	private int y = 0;

	public ServerSquare() {
		
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

	}

	public String[] receiveData(DataInputStream in) throws IOException {

		return in.readUTF().trim().split(",");

	}
	

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.fill(new Rectangle2D.Double(x, y, 50, 50));
		

	}

	public static void main(String[] args) throws IOException {

		JFrame f = new JFrame();
		ServerSquare ssquare = new ServerSquare();
		f.add(ssquare);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);
		server = new ServerSocket();
		server.bind(new InetSocketAddress("localhost", port));

		Thread thread = new Thread(() -> {
			
			try {
				socket = server.accept();
				DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				// ->
				String coords[] = ssquare.receiveData(in);
				
					ssquare.x = Integer.valueOf(coords[0]);
					ssquare.y = Integer.valueOf(coords[1]);
				///desenez cu x si y cercul
				
					
					
				System.out.println("x " + ssquare.x + " " + ssquare.y);
				ssquare.repaint();
				ssquare.repaint(ssquare.x, ssquare.y, 50, 50);

			} catch (Exception e) {

				e.printStackTrace();
			}

			System.out.println("merge");
		}

		);
		thread.start();

	}

}
