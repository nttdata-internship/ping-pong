package com.nttdata.internship.ui.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;

import dataBase.DatabaseUtil;

public class ClientPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Dimension frameSize = new Dimension(640, 560);
	private BufferedImage paddleC;
	protected ObjectShape shape;

	private Connection con = null;
	private PreparedStatement st = null;

	public ClientPanel() {
		setFocusable(true);
		gameStatus = GAME_STATUS.RUNNING;
		setPreferredSize(ServerPanel.frameSize);
		Connection con = DatabaseUtil.getConnection();
		ResultSet rs;
		try {
			rs = con.prepareStatement("select max(id) from score").executeQuery();
			setRowId(rs.next() ? rs.getInt(1) : -1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void clientFrame() {
		JFrame f = new JFrame();
		f.setTitle("CLIENT");
		f.add(this);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			paddleC = ImageIO.read(this.getClass().getClassLoader().getResource("paddle.png"));
		} catch (IOException e) {
			System.out.println("The paddle is not loading.");
		}

		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {
				ServerPanel.frameSize = e.getComponent().getSize();
				getBall().setFrameSize(frameSize);

			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});

		f.pack();
	}

	@Override
	public void startGame() {
		super.startGame();

		this.gameStatus = GAME_STATUS.PAUSED;
		try {
			GameData data = new GameData();

			data.setGameStatus(GAME_STATUS.RUNNING);

			SocketUtil.sendDataToServer(os, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopGame() {
		super.stopGame();
		this.gameStatus = GAME_STATUS.RUNNING;
		try {
			GameData data = new GameData();
			data.setGameStatus(GAME_STATUS.PAUSED);
			SocketUtil.sendDataToServer(os, data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (clientPaddle != null) {
			g.drawImage(paddleC, clientPaddle.getX(), clientPaddle.getY(), this);

		}
		if (paddle != null) {
			g.drawImage(paddleC, (int) (ServerPanel.frameSize.getWidth() - 35), 0 + paddle.getY(), this);

		}
		List<Integer> score=getScore();
		Collections.reverse(score);
		if(!score.isEmpty() && score.size()>1)
		paintScore(score, g2);

		if (gameStatus == GAME_STATUS.WIN) {

			setGameStatus(GAME_STATUS.RESUME);
		}

		if (gameStatus == GAME_STATUS.LOOSE) {
			setGameStatus(GAME_STATUS.RESUME);
		}

		this.repaint();
	}

}