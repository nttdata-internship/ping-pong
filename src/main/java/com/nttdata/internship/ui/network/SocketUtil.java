package com.nttdata.internship.ui.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.nttdata.internship.ui.animation.ObjectShape;

public final class SocketUtil {

	private SocketUtil() {

	}

	public static void sendDataToServer(OutputStream os, ArrayList<ObjectShape> objects) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(objects);
		out.flush();

	}

	public static Object readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		if (in != null)
			return in.readObject();
		return null;

	}

}
