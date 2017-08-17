package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;

public class ClientPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;
	static Socket socket = null;
	static int port = 2222;
	public static Dimension frameSize = new Dimension(640, 560);

	protected ObjectShape shape;

	public ClientPanel() {
		setFocusable(true);
		gameStarted = true;
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
		g.drawString("Waiting for game to start", 250, 200);

	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

		super.stopGame();
		this.gameStarted = false;
		try {
			GameData data = new GameData();
			List<ObjectShape> paddle = new ArrayList<>();
			paddle.add(getPaddle());
			data.setObjects(paddle);
			data.setGameRunning(true);
			SocketUtil.sendDataToServer(os, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stopGame() {
		super.stopGame();
		this.gameStarted = false;
		try {
			GameData data = new GameData();
			List<ObjectShape> paddle = new ArrayList<>();
			paddle.add(getPaddle());
			data.setObjects(paddle);
			data.setGameRunning(false);
			SocketUtil.sendDataToServer(os, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (clientPaddle != null) {
			g2.setColor(Color.red);
			g2.fill(new Rectangle2D.Double(clientPaddle.getX(), clientPaddle.getY(), 20, 80));
		}
		if (paddle != null) {
			g2.setColor(Color.blue);
			g2.fill(new Rectangle2D.Double(ServerPanel.frameSize.getWidth() - 10, 0 + paddle.getY(), 20, 80));
		}
	}

}