package com.nttdata.internship.ui.network.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nttdata.internship.ui.animation.ObjectShape;
import com.nttdata.internship.ui.panel.GamePanel.GAME_STATUS;

public class GameData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GAME_STATUS gameStatus;
	private List<ObjectShape> objects;
	private int score;

	public GameData() {
		this.objects = new ArrayList<>();
		//this.gamePanel = new GamePanel();
	}

	public List<ObjectShape> getObjects() {
		return objects;
	}

	public void setObjects(List<ObjectShape> objects) {
		this.objects = objects;
	}

	public GAME_STATUS getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GAME_STATUS gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isGameRunning() {

		return GAME_STATUS.RUNNING == gameStatus;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	

}
