package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

public class ClientPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Dimension frameSize = new Dimension(640, 560);

	protected ObjectShape shape;

	public ClientPanel() {
		setFocusable(true);
		gameStatus = GAME_STATUS.RUNNING;
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
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

		super.stopGame();
		this.gameStatus = GAME_STATUS.PAUSED;
		try {
			GameData data = new GameData();
			List<ObjectShape> paddle = new ArrayList<>();
			paddle.add(getPaddle());
			data.setObjects(paddle);
			data.setGameStatus(GAME_STATUS.RUNNING);
			//data.setScore(getScoreC());
			SocketUtil.sendDataToServer(os, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stopGame() {
		super.stopGame();
		this.gameStatus = GAME_STATUS.PAUSED;
		try {
			GameData data = new GameData();
			List<ObjectShape> clientPaddle = new ArrayList<>();
			clientPaddle.add(getClientPaddle());
			data.setObjects(clientPaddle);
			data.setGameStatus(GAME_STATUS.PAUSED);
			data.setScore(getScoreS());
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
			g2.setColor(Color.GREEN);
			g2.fill(new Rectangle2D.Double(clientPaddle.getX(), clientPaddle.getY(), 20, 80));
		}
		if (paddle != null) {
			g2.setColor(Color.ORANGE);
			g2.fill(new Rectangle2D.Double(ServerPanel.frameSize.getWidth() - 35, 0 + paddle.getY(), 20, 80));
		}
		if (gameStatus == GAME_STATUS.WIN)
			setScoreC(getScoreC() + 1);
	}

}