package com.nttdata.internship.ui.network.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nttdata.internship.ui.animation.ObjectShape;

public class GameData implements Serializable {
	private boolean gameRunning = false;
	private List<ObjectShape> objects;
	private int score = 0;

	public GameData() {
		this.objects = new ArrayList<>();
	}

	public List<ObjectShape> getObjects() {
		return objects;
	}

	
	public void setObjects(List<ObjectShape> objects) {
		this.objects = objects;
	}

	

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

}
