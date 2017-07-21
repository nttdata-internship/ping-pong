package com.nttdata.internship.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ActionListener {
	JLabel label;
	JTextField tf;
	JTextField tfServer, tfPort;
	JTextArea ta;
	int defaultPort;
	String defaultHost;

	ClientGUI(String host, int port) {
		super("Chat Client");
		defaultPort = port;
		defaultHost = host;

		JPanel northPanel = new JPanel(new GridLayout(3, 1));
		JPanel serverAndPort = new JPanel(new GridLayout(3, 5 ));
		tfServer = new JTextField(host);
		tfPort = new JTextField(" " + port);
		tfPort.setHorizontalAlignment(SwingConstants.LEFT);

		serverAndPort.add(new JLabel("Server Adress: "));
		serverAndPort.add(tfServer);
		serverAndPort.add(new JLabel("Port number: "));
		serverAndPort.add(tfPort);
		serverAndPort.add(new JLabel(""));
		northPanel.add(serverAndPort);
		this.add(northPanel);
	}

	public ClientGUI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		ClientGUI cg = new ClientGUI("localhost",2222);
		cg.setSize(new Dimension(666, 666));
		cg.setVisible(true);
		cg.setResizable(true);

	}

}
