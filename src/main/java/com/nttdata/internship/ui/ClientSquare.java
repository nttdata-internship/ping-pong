package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientSquare extends JPanel implements KeyListener {

	static Socket socket = null;
	static int port = 2222;

	private int x = 0, v_x = 0;
	private int y = 0, v_y = 0;

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

	public ClientSquare() {
		t.start();
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.fill(new Ellipse2D.Double(x, y, 50, 50));

	}

	public static void sendDataToServer(String sir) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		out.writeUTF(sir);
		out.flush();

	}

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		ClientSquare cs = new ClientSquare();
		f.addKeyListener(cs);
		f.add(cs);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);

		socket = new Socket("localhost", port);
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
			sendDataToServer(x + "," + y + "\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}