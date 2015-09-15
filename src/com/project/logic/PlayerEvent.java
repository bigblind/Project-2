package com.project.logic;

public class PlayerEvent {

	private Player player;
	private Point from, to;
	
	public PlayerEvent(Point from, Point to, Player player) {
		this.player = player;
		this.from = from;
		this.to = to;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Point getFromPoint() {
		return this.from;
	}
	
	public Point getToPoint() {
		return this.to;
	}
}
