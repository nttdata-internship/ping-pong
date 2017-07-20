package com.nttdata.internship.chatapp.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import com.nttdata.internship.chatapp.client.ChatClient;

public class MultiServers {
	ServerSocket server = null;
	Thread thread = null;
	SocketConnectionListener client = null;

	// public MultiServers(int port) {
	// try {
	// System.out.println("Binding to port " + port + " ,please wait...");
	// server = new ServerSocket(port);
	// System.out.println("Server started: " + server);
	// start();
	// } catch (IOException ioe) {
	// System.out.println(ioe);
	// }
	// }

	// public void run() {
	// while (thread != null) {
	// try {
	// System.out.print("Waiting for a client...");
	// addThread(server.accept());
	// } catch (IOException ioe) {
	// System.out.println("Error " + ioe);
	// }
	// }
	// }

	// public void addThread(Socket socket) {
	// System.out.println("Client accepted: " + socket);
	// client = new ChatServerThread(this, socket);
	// try {
	// client.open();
	// client.start();
	// } catch (IOException ioe) {
	//
	// }
	// }

	// public void start() {
	// if (thread == null) {
	// thread = new Thread(this);
	// thread.start();
	// }
	// }

	public void stop() throws InterruptedException {
		if (thread != null) {
			thread.join();
			thread = null;
		}
	}

	public static void main(String args[]) throws IOException {
		ChatServer server = null;
		if (args.length != 1) {
			System.out.println("Usage: java ChatServer port");
			return;
		}

		SocketConnectionListener connectionListener = new SocketConnectionListener(Integer.parseInt(args[0]));
		connectionListener.start();

		System.out.println("Chat Server Thead started");

		while (true) {
			for (Entry<String, Socket> clients : Collections
					.synchronizedMap((Map<String, Socket>) connectionListener.connectedClients).entrySet()) {

				String clientName = clients.getKey();
				Socket clientSocket = clients.getValue();

				DataInputStream dais = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream daos = new DataOutputStream(clientSocket.getOutputStream());

				String read = dais.readUTF();
				System.out.println("read " + read + "  from" + clientName);

			}

		}
	}

}
