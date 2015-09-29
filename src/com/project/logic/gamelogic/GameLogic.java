package com.project.logic.gamelogic;

import com.project.logic.Game;
import com.project.logic.player.Player;
import com.project.logic.player.PlayerEvent;
import com.project.logic.player.PlayerListener;

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