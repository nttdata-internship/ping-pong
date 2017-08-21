package com.nttdata.internship.chatapp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketConnectionListener extends Thread {
	private Socket socket = null;
	private ChatServer server = null;
	private int serverPort = -1;
	private DataInputStream streamIn = null;

	private Map<String, Socket> connectedClients;

	public SocketConnectionListener(int serverPort) {
		connectedClients = new ConcurrentHashMap<>();
		this.serverPort = serverPort;
	}

	public void run() {
		// System.out.println("Server thread " + ID + " running.");
		System.out.println("Binding to port " + serverPort + " ,please wait...");
		try {
			ServerSocket server = new ServerSocket(serverPort);
			int clientCount = 0;
			while (clientCount <= 10) {
				System.out.println("waiting for connection....");

				String clientName = "clientName " + clientCount++;
				Socket establishedConnection = server.accept();
				// connectedClients.put(clientName, establishedConnection);

				// System.out.println(streamIn.readUTF());

				ClientServer cs = new ClientServer(establishedConnection, clientName);
				cs.start();

				System.out.println("Connection established ..." + clientName);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Map<String, Socket> getConnectedClients() {
		return connectedClients;
	}

	public void setConnectedClients(Map<String, Socket> connectedClients) {
		this.connectedClients = connectedClients;
	}

	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

	}

	public void close() throws IOException {
		if (socket != null) {
			socket.close();
		}
		if (streamIn != null) {
			streamIn.close();
		}
	}

}
