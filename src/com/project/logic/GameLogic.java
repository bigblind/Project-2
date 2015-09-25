package com.project.logic;

public abstract class GameLogic implements PlayerListener {

	private Player activePlayer;
	private Game game;

	public GameLogic(Game game) {
		this.game = game;
		this.activePlayer = game.getPlayerOne();
	}

	public abstract void loop();

	
	public void eventPerformed(PlayerEvent e) {

	}
	
	public Player getActivePlayer() {
		return this.activePlayer;
	}
}