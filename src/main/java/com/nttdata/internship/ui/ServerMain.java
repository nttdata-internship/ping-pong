package com.nttdata.internship.ui;

import java.io.IOException;

import com.nttdata.internship.ui.listener.KeysAction;
import com.nttdata.internship.ui.network.SocketConnection;
import com.nttdata.internship.ui.panel.ServerPanel;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		ServerPanel server = new ServerPanel();
		server.addKeyListener(new KeysAction(server));
		SocketConnection sc = new SocketConnection(server);
		sc.listenForConnection();
	}
}