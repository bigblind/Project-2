package com.gipf.client.offline.logic;

import com.gipf.client.game.player.Player;

public class PlayerChangeEvent {

	private Player fromPlayer, toPlayer;
	
	public PlayerChangeEvent(Player fromPlayer, Player toPlayer) {
		this.fromPlayer = fromPlayer;
		this.toPlayer = toPlayer;
	}
	
	public Player getFromPlayer() {
		return this.fromPlayer;
	}
	
	public Player getToPlayer() {
		return this.toPlayer;
	}
	
	public String toString() {
		return "[ChangePlayerEvent: " + this.fromPlayer + " to " + this.toPlayer + "]";
	}
}
