package com.nttdata.internship.chatapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServer extends Thread {
	private Socket clientSocket;
	private String clientName;

	public ClientServer(Socket establishedConnection, String clientName) {
		clientSocket = establishedConnection;
		this.clientName = clientName;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String line = null;

			while (true) {

				System.out.println(clientName + ":    " + in.readLine());

			}

			// System.out.println("End thread");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
