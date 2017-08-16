package com.nttdata.internship.ui.panel;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import com.nttdata.internship.ui.animation.ObjectShape;

public class ClientPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;
	static Socket socket = null;
	static int port = 2222;
	protected boolean gameStarted = false;
	public static Dimension frameSize = new Dimension(640, 560);


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


}