package com.nttdata.internship.chatapp.client;

import java.io.*;
import java.net.*;

public class ChatClient {
	public static void main(String[] args) {
		try {
			Socket server = new Socket("127.0.0.1", 1200);
			DataInputStream dis = new DataInputStream(server.getInputStream());
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());

			BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));
			String msgin = "";
			String msgout = "";
			while (!msgin.equals("End")) {
				msgout = breader.readLine();
				dos.writeUTF(msgout);
				msgin = dis.readUTF();
				System.out.println(msgin);
			}

		} catch (Exception e) {
			// Handle exceptions
		}
	}

}
