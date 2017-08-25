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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.nttdata.internship.ui.animation.BallAnimation;
import com.nttdata.internship.ui.network.SocketUtil;
import com.nttdata.internship.ui.network.data.GameData;

import dataBase.Driver;

public class ServerPanel extends GamePanel implements Serializable {

	private static final long serialVersionUID = 1L;

	private BallAnimation animationThread;

	public static Dimension frameSize = new Dimension(640, 560);
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement st = null;

	public ServerPanel() {
		con = Driver.DB();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setPreferredSize(frameSize);
		JFrame f = new JFrame();
		f.setTitle("SERVER");
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
			// data.setScore(getScoreS());
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
			g2.setColor(Color.GREEN);
			g2.fill(new Rectangle2D.Double(paddle.getX(), paddle.getY(), 20, 80));
		}
		if (clientPaddle != null) {
			g2.setColor(Color.ORANGE);
			g2.fill(new Rectangle2D.Double(ServerPanel.frameSize.getWidth() - 35, 0 + clientPaddle.getY(), 20, 80));
		}
		//paintScore(g2, gameStatus.message);

		if (gameStatus == GAME_STATUS.WIN) {
			//paintScore(g2, gameStatus.message);
			//setScoreS(getScoreS() + 1);
			
			

			         try{
			        	 
			            String sql="Update Score SET score = score + 1 where id = 1";
			            st = con.prepareStatement(sql);
			            st.executeUpdate();

			        } catch(Exception e){
			            JOptionPane.showMessageDialog(null,e);
			        }finally{
			            try{
			                st.close();
			        }catch(Exception e){
//			            catch(SQLException e){
			                JOptionPane.showMessageDialog(null, e);
			            }
			        }
			
			paintMessage(g2, gameStatus.message);
			paintScore(g2, gameStatus.message);
			setGameStatus(GAME_STATUS.RESUME);
		}
		if (gameStatus == GAME_STATUS.LOOSE) {
			paintScore(g2, gameStatus.message);
			//setScoreC(getScoreC() + 1);
			paintMessage(g2, gameStatus.message);
			setGameStatus(GAME_STATUS.RESUME);
		}
		paintScore(g2, gameStatus.message);

	}

}