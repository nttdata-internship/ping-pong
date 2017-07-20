package com.nttdata.internship.chatapp.client;

import java.io.*;
import java.net.*;

import com.nttdata.internship.chatapp.server.ChatServer;

public class ChatClient {
	Socket socket = null;
	DataInputStream console = null;
	DataOutputStream streamOut = null;

	public ChatClient(String serverName, int serverPort) {
		System.out.println("Establishing connection, pleas wait...");
		try {
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
		String line = "";
		while (!line.equals("Bye")) {
			try {
				line = console.readLine();
				streamOut.writeUTF(line);
				streamOut.flush();
			} catch (IOException ioe) {
				System.out.println("Sending an erorr: " + ioe.getMessage());
			}
		}
	}

	public void start() throws IOException {
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());

	}

	public void stop() throws IOException {
		try {
			if (console != null) {
				console.close();
			}
			if (streamOut != null) {
				streamOut.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException ioe) {
			System.out.println("Error closing...");
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// ChatClient client = null;
		// if (args.length != 2) {
		// System.out.println("Usage: Java ChatClient host port");
		// } else {
		// client = new ChatClient(args[0], Integer.parseInt(args[1]));
		// }
		Socket socket = new Socket("localhost", 2222);

		socket.getOutputStream().write("Client".getBytes());

	}
}
