package com.gipf.client.game.player.bot.action;

import com.gipf.client.utils.Point;

public class Action {

	private Point[] points;
	
	public Action(Point... points) {
		this.points = points;
	}
	
	public Point[] getPoints() {
		return this.points;
	}
	
	public String toString() {
		String result = "[Action: ";
		for (Point p : this.points) result += p + " ";
		result += "]";
		return result;
	}
}
