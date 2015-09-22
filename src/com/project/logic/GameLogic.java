package com.project.logic;

public abstract class GameLogic implements PlayerListener {

	private Game game;

	public GameLogic(Game game) {
		this.game = game;
	}

	public abstract void loop();

	
	public void eventPerformed(PlayerEvent e) {

	}
}