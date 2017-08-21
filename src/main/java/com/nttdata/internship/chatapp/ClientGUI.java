package com.nttdata.internship.chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ActionListener, MouseListener, KeyListener, WindowListener {
	private TextArea message_area;
	private TextField send_area;
	private Button clear;
	private Button send;

	ClientGUI() {
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
		send = new Button("Send");
		send.addActionListener(this);
		p.add(send);

		this.clear = new Button("Clear");
		clear.addActionListener(this);
		p.add(clear);

		this.add(p, "South");
		this.setVisible(true);
		send_area.requestFocus();

	}

	public static void main(String[] args) {
		ClientGUI cg = new ClientGUI();
		cg.setSize(800, 600);
		cg.setResizable(true);
		cg.setVisible(true);

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		String st = send_area.getText().trim();

		if (e.getSource() == send) {

			if (st.length() == 0)
				st = null;
			message_area.append("\n" + st);

		} else if (e.getSource() == clear)
			message_area.setText("");

		send_area.setText("");
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
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
