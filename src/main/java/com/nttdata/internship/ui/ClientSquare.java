package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nttdata.internship.ui.ServerSquare.ClientShape;

public class ClientSquare extends JPanel implements KeyListener {

	static Socket socket = null;
	static int port = 2222;

	private int x = 0, v_x = 0;
	private int y = 0, v_y = 0;
	
	static class ServerShape {
		private int x;
		private int y;
	}

	private ServerShape server;
/*
	Thread t = new Thread(() -> {
		while (true) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}

	);
*/
	public ClientSquare() {
		//t.start();
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.fill(new Ellipse2D.Double(x, y, 50, 50));
		if (server != null) {
			g2.fill(new Ellipse2D.Double(server.x, server.y, 50, 50));
		}

	}
/*
	public static void sendDataToServer(String sir) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		out.writeUTF(sir);
		out.flush();

	}*/
	
	public void sendingDataToServer(ClientSquare ss) throws IOException {
		if (socket != null) {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			// out.writeUTF(sir); write clientShape Object
			out.writeObject(ss);
			out.flush();
		}

	}
	
	public Object receiveData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		return  in.readObject();
		
	}
	/*
	public String[] receiveData(DataInputStream in) throws IOException {

		return in.readUTF().trim().split(",");

	}
	*/

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		ClientSquare cs = new ClientSquare();
		f.addKeyListener(cs);
		f.add(cs);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);
		
		Thread thread = new Thread(() -> {

			try {
				socket = new Socket("localhost", port);
				cs.server = new ServerShape();
				
				while(true){
				//DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				//String coords[] = cs.receiveData(in);
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				Object ss = cs.receiveData(in);
				cs.server.x = ss.x;
				cs.server.y = ss.y;
				System.out.println("x " + cs.server.x + " " + cs.server.y);
				cs.repaint();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		});
		thread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			v_y -= 1;
			v_x = 0;
		}

		if (code == KeyEvent.VK_S) {
			v_y = 1;
			v_x = 0;
		}
		if (code == KeyEvent.VK_A) {
			v_x -= 1;
			v_y = 0;
		}
		if (code == KeyEvent.VK_D) {
			v_x += 1;
			v_y = 0;
		}
		if (x < 0 || x > 660)
			v_x = -v_x;
		if (y < 0 || y > 560)
			v_y = -v_y;

		x += v_x;
		y += v_y;
		try {
			ClientSquare sq = new ClientSquare();
			sq.x = x;
			sq.y = y;

			sendingDataToServer(sq);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}