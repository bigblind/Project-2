package com.gipf.client.player.bot.generator;

import com.gipf.client.offline.logic.Game;
import com.gipf.client.utils.Point;

public class GameState {

	private Game game;
	private Point from;
	private Point to;
	
	public GameState(Game game, Point from, Point to) {
		this.game = game;
		this.from = from;
		this.to = to;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public Point getFromPoint() {
		return this.from;
	}
	
	public Point getToPoint() {
		return this.to;
	}
}
