package com.nttdata.internship.ui;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		ServerSquare server = new ServerSquare();
		Data d = new Data(server);
		d.listenForConnection();
		server.addKeyListener(new KeysAction(d, server,new ClientSquare()));

	}

}
