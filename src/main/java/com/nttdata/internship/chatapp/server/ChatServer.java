package com.nttdata.internship.chatapp.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.nttdata.internship.chatapp.client.ChatClient;

public class ChatServer {
	Socket socket = null;
	ServerSocket server = null;
	DataInputStream streamIn = null;

	public ChatServer(int port) {
		try {
			System.out.println("Bindint to a port " + port + " ,please wait...");
			server = new ServerSocket(port);
			System.out.println("Server started. " + server);
			System.out.println("Waitinf for a client...");
			socket = server.accept();
			System.out.println("Client accepted. " + socket);
			open();
			boolean done = false;
			while (!done) {
				try {
					String line = streamIn.readUTF();
					System.out.println(line);
					done = line.equals("Bye");
				} catch (IOException ioe) {
					done = true;
				}
			}
			close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
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

	public static void main(String[] args) {
		ChatServer server = null;
		if (args.length != 1) {
			System.out.println("Usage: Java ChatServer port.");
		} else {
			server = new ChatServer(Integer.parseInt(args[0]));
		}
	}

}