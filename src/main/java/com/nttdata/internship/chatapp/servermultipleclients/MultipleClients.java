package com.nttdata.internship.chatapp.servermultipleclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultipleClients {
	private ServerSocket serverSocket;

	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		while (true)
			new EchoClientHandlerThread(serverSocket.accept()).run();
	}

	public void stop() throws IOException {
		serverSocket.close();
	}

	public class EchoClientHandlerThread extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		public EchoClientHandlerThread(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try {

				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (".".equals(inputLine)) {
						out.println("bye");
						break;
					}
					out.println(inputLine);
				}

				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}