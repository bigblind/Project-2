package com.project.common.player;

import com.project.common.utils.Point;

public class PlayerEvent {

	protected Player player;
	protected Point from, to;
	
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
	
	public String toString() {
		return "[PlayerEvent: FromPoint: " + this.from + " | ToPoint: " + this.to + "| Player: " + this.player + "]";
	}
}
