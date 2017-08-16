package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import com.nttdata.internship.ui.animation.ObjectShape;

public class ClientPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int SPEED_INCREMENT = 5;
	static Socket socket = null;
	static int port = 2222;
	private GamePanel gamePanel;
	protected boolean gameStarted = false;
	public static Dimension frameSize = new Dimension(640, 560);

	private int x = 0;
	private int y = 0;
	private int length = 50;
	private int width = 50;

	protected ObjectShape shape;

	boolean isDown = false;
	int startX;
	int startY;

	static ObjectInputStream in;

	public ClientPanel() {
		setFocusable(true);
		setPreferredSize(ServerPanel.frameSize);
	}


	public void clientFrame() {
		JFrame f = new JFrame();
		f.setTitle("CLIENT");
		f.add(this);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				ServerPanel.frameSize = e.getComponent().getSize();
				getBall().setFrameSize(frameSize);

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
	protected void paintWelcomeMessage(Graphics g) {
		
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("surpriza nu scrie ca la celalalt", 250, 200);
		
	}


}