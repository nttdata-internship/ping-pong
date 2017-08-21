package com.nttdata.internship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Square extends JPanel implements
		/* ActionListener, */
		KeyListener, MouseListener {

	private double x = 275, y = 265, Ox = 0, Oy = 0;
	private double xc = 0, yc = 0, Oxc = 2, Oyc = 2;

	private boolean running;
	private Thread t = new Thread(new Runnable() {
		public void run() {
			while (true) {

				try {

					Thread.sleep(6);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				repaint();
			}

		}
	}

	);

	public Square() {
		t.start();
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		setBackground(Color.pink);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle2D.Double(x, y, 50, 50));
		Graphics2D circle = (Graphics2D) g;
		circle.fill(new Ellipse2D.Double(xc, yc, 50, 50));
		circle.setColor(Color.black);

	}

	public void up() {
		Oy -= 1;
		Ox = 0;
	}

	public void down() {
		Oy += 1;
		Ox = 0;
	}

	public void left() {
		Ox -= 1;
		Oy = 0;
	}

	public void right() {
		Ox += 1;
		Oy = 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP)
			up();
		if (code == KeyEvent.VK_DOWN)
			down();
		if (code == KeyEvent.VK_LEFT)
			left();
		if (code == KeyEvent.VK_RIGHT)
			right();

		if (code == KeyEvent.VK_W) {
			Oyc -= 1;
			Oxc = 0;

		}
		if (code == KeyEvent.VK_S) {
			Oyc = 1;
			Oxc = 0;
		}
		if (code == KeyEvent.VK_A) {
			Oxc -= 1;
			Oyc = 0;
		}
		if (code == KeyEvent.VK_D) {
			Oxc += 1;
			Oyc = 0;
		}

		if (x < 0 || x > 660)
			Ox = -Ox;
		if (y < 0 || y > 560)
			Oy = -Oy;

		x += Ox;
		y += Oy;
		/*
		 * if (xc < 0 || xc > 660) Oxc = -Oxc; if (yc < 0 || yc > 560) Oyc = -Oyc;
		 * 
		 * xc += Oxc; yc += Oyc;
		 */

	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		Square square = new Square();
		f.add(square);
		f.addKeyListener(square);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mesaj");
		Thread t2 = null;

		if (e.getButton() == MouseEvent.BUTTON1) {
			running = true;
			t2 = new Thread(new Runnable() {
				public void run() {
					while (running) {
						if (xc < 0 || xc > 660)
							Oxc = -Oxc;
						if (yc < 0 || yc > 560)
							Oyc = -Oyc;

						xc += Oxc;
						yc += Oyc;

						repaint();
						try {
							Thread.sleep(10);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}
			});
			t2.start();
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			running = false;

			if (t2 != null && !(t2.getState() == Thread.State.TERMINATED)) {
				// TODO: Make the thread stop

			}

		}

		try {
			Thread.sleep(2220);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
