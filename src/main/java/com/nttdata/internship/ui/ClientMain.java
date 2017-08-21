package com.nttdata.internship.ui;

import com.nttdata.internship.ui.listener.KeysAction;
import com.nttdata.internship.ui.network.SocketConnection;
import com.nttdata.internship.ui.panel.ClientPanel;

public class ClientMain {
	public static void main(String[] args) {
		ClientPanel client = new ClientPanel();
		client.clientFrame();
		SocketConnection sc = new SocketConnection(client);
		sc.start();
		client.addKeyListener(new KeysAction(client));

	}
}
