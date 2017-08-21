package com.nttdata.internship.chatapp.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private Socket socket = null;
	private DataInputStream console = null;
	DataOutputStream streamOut = null;

	public ChatClient(String serverName, int serverPort) throws IOException {
		System.out.println("Establishing connection, please wait...");
		try {
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
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
		Socket socket = new Socket("10.224.20.171", 2222);
		DataInputStream input = new DataInputStream(socket.getInputStream());
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;

		while ((line = br.readLine()) != null) {
			System.out.println("Sending " + line);
			output.write(line + System.lineSeparator());
			output.flush();

		}
		input.close();
		output.close();
		socket.close();

	}
}
