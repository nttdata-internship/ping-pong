package com.nttdata.internship.chatapp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class SocketConnectionListener extends Thread {
	Socket socket = null;
	ChatServer server = null;
	int serverPort = -1;
	DataInputStream streamIn = null;

	Map<String, Socket> connectedClients;

	public SocketConnectionListener(int serverPort) {
		connectedClients = new LinkedHashMap<>();
		this.serverPort = serverPort;
	}

	public void run() {
		// System.out.println("Server thread " + ID + " running.");
		System.out.println("Binding to port " + serverPort + " ,please wait...");
		try {
			ServerSocket server = new ServerSocket(serverPort);
			int clientCount = 0;
			while (clientCount<=10) {
				System.out.println("waiting for connection....");
				
				
				
				String clientName = "clientName " + clientCount++;
				Socket establishedConnection = server.accept();
				connectedClients.put(clientName, establishedConnection);

				// System.out.println(streamIn.readUTF());
				
				System.out.println("Connection established ..."+clientName);
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
