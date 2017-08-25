package com.nttdata.internship.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.nttdata.internship.ui.animation.BallAnimation;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;

public class ServerPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;

	private BallAnimation animationThread;
	private transient BufferedImage paddleS;
	public static Dimension frameSize = new Dimension(640, 560);

	public ServerPanel() {
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setPreferredSize(frameSize);
		JFrame f = new JFrame();
		f.setTitle("SERVER");
		f.add(this);
		f.setVisible(true);
		
		try {
			paddleS = ImageIO.read(new File("C:\\Users\\stefan.neacsu\\Desktop\\Pong Resources\\paddle.png"));
		} catch (IOException e) {
			System.out.println("The paddle is not loading.");
		}
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void componentResized(ComponentEvent e) {
				frameSize = e.getComponent().getSize();
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
		super.startGame();
		animationThread = new BallAnimation(this);
		animationThread.start();

	}

	public void stopGame() {
		super.stopGame();
		try {
			animationThread.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.gameStatus = GAME_STATUS.PAUSED;
		try {
			GameData data = new GameData();
			// List<ObjectShape> paddle = new ArrayList<>();
			// paddle.add(getPaddle());
			// data.setObjects(paddle);
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
		if (paddle != null) {
			g.drawImage(paddleS, paddle.getX(), paddle.getY(), this);
//			g2.setColor(Color.GREEN);
//			g2.fill(new Rectangle2D.Double(paddle.getX(), paddle.getY(), 20, 80));
		}
		if (clientPaddle != null) {
			g.drawImage(paddleS, (int) (ServerPanel.frameSize.getWidth() - 35), 0 + clientPaddle.getY(), this);
//			g2.setColor(Color.ORANGE);
//			g2.fill(new Rectangle2D.Double(ServerPanel.frameSize.getWidth() - 35, 0 + clientPaddle.getY(), 20, 80));
		}
  		paintScore(g2, gameStatus.message);
		if (gameStatus == GAME_STATUS.WIN) {
			paintScore(g2, gameStatus.message);
			paintMessage(g2, gameStatus.message);
			setGameStatus(GAME_STATUS.RESUME);
			setScoreS(getScoreS() + 1);
		}
		if (gameStatus == GAME_STATUS.LOOSE) {
			paintScore(g2, gameStatus.message);
			paintMessage(g2, gameStatus.message);
			setGameStatus(GAME_STATUS.RESUME);
			setScoreC(getScoreC() + 1);
		}

	}

}