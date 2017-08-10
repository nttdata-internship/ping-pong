package com.nttdata.internship.ui.panel;

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

	private int x = 0;
	private int y = 0;
	private int length = 50;
	private int width = 50;

	protected ObjectShape shape;

	boolean isDown = false;
	int startX;
	int startY;

	// private static Dimension frameSize = new Dimension(700, 600);

	static ObjectInputStream in;

	public ClientPanel() {

		setFocusable(true);
		setPreferredSize(ServerPanel.frameSize);
	}

	// public static void main(String[] args) throws IOException {
	// ClientSquare cs = new ClientSquare();
	// cs.clientFrame();
	// ball = new Ball(frameSize);
	// ClientConnection caca = new ClientConnection(ball, cs);
	// caca.start();
	//
	// }
	//
	// @Override
	// public void keyTyped(KeyEvent e) {
	//
	// }
	//
	// @Override
	// public synchronized void keyPressed(KeyEvent e) {
	//
	// int code = e.getKeyCode();
	// int prevX = x;
	// int prevY = y;
	// // boolean ox = true;
	// // boolean oy = true;
	//
	// if (code == KeyEvent.VK_UP) {
	// y -= SPEED_INCREMENT;
	// }
	// if (code == KeyEvent.VK_DOWN) {
	// y += SPEED_INCREMENT;
	// // startY = y - prevY;
	// }
	// if (code == KeyEvent.VK_LEFT) {
	// x -= SPEED_INCREMENT;
	// }
	// if (code == KeyEvent.VK_RIGHT) {
	// x += SPEED_INCREMENT;
	// }
	//
	// // int dx = startX - shape.getX();
	// // int dy = startY - shape.getY();
	// // isDown = (dx * dx + dy * dy < shape.getRadius() * shape.getRadius());
	//
	// if (paddle.getX() < 0 || paddle.getX() > ServerSquare.frameSize.getWidth() -
	// 60) {
	// paddle.setX(prevX);
	//
	// } else if (paddle.getY() < 0 || paddle.getY() >
	// ServerSquare.frameSize.getHeight() - 90) {
	// paddle.setY(prevY);
	// }
	//
	// // shape.setX(shape.getX() + dx);
	// // shape.setY(shape.getY() + dy);
	//
	// if (collision()) {
	// System.out.println("s-au ciocnit");
	//
	// } else {
	// System.out.println("nu s-au ciocnit");
	// }
	//
	// try {
	// this.paddle = new ObjectShape();
	// paddle.setX(prevX);
	// paddle.setY(prevY);
	// if (socket != null)
	// SocketUtil.sendDataToServer(socket.getOutputStream(), this);
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	//
	// // }
	//
	// repaint();
	// }
	//
	// public boolean collision() {
	// float distX = Math.abs(ball.getX() - x - width / 2);
	// float distY = Math.abs(ball.getY() - y - length / 2);
	//
	// if (distX > (width / 2 + ball.getRadius())) {
	// return false;
	// }
	// if (distY > (length / 2 + ball.getRadius())) {
	// return false;
	// }
	//
	// if (distX <= (width / 2)) {
	// return true;
	// }
	// if (distY <= (length / 2)) {
	// return true;
	// }
	//
	// float dx = distX - width / 2;
	// float dy = distY - length / 2;
	// return (dx * dx + dy * dy <= (ball.getRadius() * ball.getRadius()));
	//
	// }
	//
	// @Override
	// public void keyReleased(KeyEvent e) {
	//
	// }

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