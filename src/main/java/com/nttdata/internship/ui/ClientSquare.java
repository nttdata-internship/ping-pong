package com.nttdata.internship.ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSquare {

	static Socket socket = null;
	static int port = 2222;
	
	
	public static void input() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		while ((line = br.readLine()) != null) {
			sendDataToServer(line+"\n");
		}
		
	
	}
	
	public static void sendDataToServer(String sir) throws IOException{
		
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		out.writeUTF(sir);
		out.flush();
		
	}
	
	public static void main(String[] args) throws IOException{
		
		socket = new Socket("localhost", port);
		input();
		
	}
}
