package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientSquare extends JPanel implements KeyListener,Serializable {

	private static final long serialVersionUID = 1L;
	static Socket socket = null;
	static int port = 2222;

	private int x = 0, v_x = 0;
	private int y = 0, v_y = 0;
	
	private ObjectShape shape;

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
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
		if (shape != null) {
			g2.setColor(Color.blue);
			g2.fill(new Ellipse2D.Double(shape.x, shape.y, 50, 50));
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
		return in.readObject();
		
	}
	/*
	public String[] receiveData(DataInputStream in) throws IOException {

		return in.readUTF().trim().split(",");

	}
	*/
	
	 @Override
	 public Dimension getPreferredSize() {
		 return new Dimension(700, 600);
	 }

	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame();
		ClientSquare cs = new ClientSquare();
		f.addKeyListener(cs);
		f.add(cs);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		
		Thread thread = new Thread(() -> {

			try {
				socket = new Socket("localhost", port);
				cs.shape = new ObjectShape();
				
				while(true){
					//DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					//String coords[] = cs.receiveData(in);
					
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					ServerSquare coords =  (ServerSquare) cs.receiveData(in);
					cs.shape.x = coords.getX();
					cs.shape.y = coords.getY();
					System.out.println("x " + cs.shape.x + " " + cs.shape.y);
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