package com.nttdata.internship.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public final class SocketUtil {

	private SocketUtil() {

	}

	public static void sendDataToServer(OutputStream os, ObjectShape ss) throws IOException {
		// if (socket != null) {
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(ss);
		out.flush();
		// }

	}

	public static Object readData(ObjectInputStream in) throws IOException, ClassNotFoundException {

		return in.readObject();

	}

}
