package com.nttdata.internship.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ActionListener, MouseListener, KeyListener, WindowListener {
	TextArea message_area = null;
	TextField send_area = null;
	String user_name = null;

	ClientGUI(String s) {
		this.addWindowListener(this);
		this.setSize(800, 600);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		message_area = new TextArea();
		message_area.setEditable(false);
		this.add(message_area, "Center");
		message_area.setFont(new Font("Arial", Font.PLAIN, 16));

		Panel p = new Panel();
		p.setLayout(new FlowLayout());

		send_area = new TextField(30);
		send_area.addKeyListener(this);
		send_area.setFont(new Font("Arial", Font.PLAIN, 16));

		p.add(send_area);
		p.setBackground(new Color(221, 221, 221));
		Button send = new Button("Send");
		send.addMouseListener(this);
		p.add(send);
		Button clear = new Button("Clear");
		clear.addMouseListener(this);
		p.add(clear);

		this.add(p, "South");
		this.setVisible(true);
		send_area.requestFocus();

	}

	public static void main(String[] args) {
		ClientGUI cg = new ClientGUI("Chat Version v0.1");
		cg.setSize(800, 600);
		cg.setResizable(true);
		cg.setVisible(true);

	}

	public ClientGUI(String string, int i) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
