package com.nttdata.internship.ui.network.data;

import java.util.List;

import com.nttdata.internship.ui.animation.ObjectShape;

public class GameData {
	protected boolean gameStatus = true;
	private List<ObjectShape> objects;
	private int scoore;
	
	private int getScoore() {
		return scoore;
	}
	private void setScoore(int scoore) {
		this.scoore = scoore;
	}
}
