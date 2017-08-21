package com.nttdata.internship.ui.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.nttdata.internship.ui.network.data.GameData;

public final class SocketUtil {

	private SocketUtil() {

	}

	public static void sendDataToServer(OutputStream os, GameData gameData) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(gameData);
		System.out.println("gameStatus sent= " + gameData.getGameStatus());
		out.flush();

	}

	public static Object readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
		if (in != null)
			return in.readObject();
		return null;

	}

}
