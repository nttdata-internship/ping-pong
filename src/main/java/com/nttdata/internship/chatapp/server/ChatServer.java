package com.nttdata.internship.chatapp.server;

import java.io.*;
import java.net.*;

public class ChatServer {
	public static void main(String[] args) {
		final int maxClientsCount = 10;
		try {
			ServerSocket server = new ServerSocket(1200);
			Socket socket = server.accept();

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));

			String msgin = "";
			String msgout = "";
			boolean isConnected = false;
			while (!msgin.equals("End")) {
				Socket clientSocket = null;

				try {

					if (!isConnected) {
						clientSocket = server.accept();
						isConnected = true;

						DataInputStream disz = new DataInputStream(socket.getInputStream());
						DataOutputStream dosz = new DataOutputStream(socket.getOutputStream());

						BufferedReader breaderz = new BufferedReader(new InputStreamReader(System.in));

						String msgin2 = "";
						String msgout2 = "";

						msgin2 = disz.readUTF();
						System.out.println(msgin2);
						msgout2 = breaderz.readLine();
						dosz.writeUTF(msgout2);
						dosz.flush();

					}

					msgin = dis.readUTF();
					System.out.println(msgin);
					msgout = breader.readLine();
					dos.writeUTF(msgout);
					dos.flush();

				}

				catch (IOException ioex) {
					throw ioex;
				} finally {

				}

			}
			socket.close();

		} catch (Exception e) {
			// Handle the exceptions
		}
	}
}
