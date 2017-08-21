package com.nttdata.internship.chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;

public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	private JButton stopStart;
	private JTextArea chat, event;
	private JTextField tPortNumber;
	private ServerSocket server;

	ServerGUI(int port) {
		super("Chat Server");
		server = null;
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField(" " + port);
		north.add(tPortNumber);

		stopStart = new JButton("Start");
		stopStart.addActionListener(this);
		north.add(stopStart);
		add(north, BorderLayout.NORTH);

		JPanel center = new JPanel(new GridLayout(2, 1));
		chat = new JTextArea(80, 80);
		chat.setEditable(false);
		appendRoom("Chat room.\n");
		center.add(new JScrollPane(chat));
		event = new JTextArea(80, 80);
		event.setEditable(false);
		appendEvent("Event log.\n");
		center.add(new JScrollPane(event));
		add(center);

		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}

	private void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);

	}

	private void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);
	}

	public static void main(String[] args) {
		ServerGUI cg = new ServerGUI(2222);
		cg.setSize(new Dimension(800, 600));
		cg.setVisible(true);
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
