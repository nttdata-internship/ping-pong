package com.nttdata.internship.chatapp.server;

import java.io.IOException;
import java.net.Socket;

public class MultiServers {
	private Thread thread = null;
	static Socket c;

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
		if (args.length != 1) {
			System.out.println("Usage: java ChatServer port");
			return;
		}

		SocketConnectionListener connectionListener = new SocketConnectionListener(Integer.parseInt(args[0]));
		connectionListener.start();

		System.out.println("Chat Server Thread started");

		/*
		 * while (true) { for (Entry<String, Socket> clients :
		 * connectionListener.connectedClients .entrySet()) {
		 * 
		 * String clientName = clients.getKey(); Socket clientSocket =
		 * clients.getValue();
		 * 
		 * PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
		 * true); BufferedReader in = new BufferedReader( new
		 * InputStreamReader(clientSocket.getInputStream()));
		 * out.println("Stream");
		 * 
		 * String inputLine, outputLine; String read = null; System.out.println(
		 * "read " + read + "  from" + clientName);
		 * 
		 * }
		 */

	}
}
