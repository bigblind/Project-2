package com.project.client.board;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.PlayerEvent;
import com.gipf.client.utils.Point;

public class Row extends PlayerEvent {
	
	private Point[] whiteExtensionStones;
	private Point[] blackExtensionStones;
	private int length;

	public Row(Point from, Point to, Player player, int length, Point[] whiteExtensionStones, Point[] blackExtensionStones) {
		super(from, to, player);
		this.whiteExtensionStones = whiteExtensionStones;
		this.blackExtensionStones = blackExtensionStones;
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public Point[] getWhiteExtensionStones() {
		return whiteExtensionStones;
	}

	public Point[] getBlackExtensionStones() {
		return blackExtensionStones;
	}

	public String toString() {
		String result = "[Row from: " + this.from + " to: " + this.to + " player: " + this.player + " length: " + this.length + " whiteExtensions: {";
			for (Point p : this.whiteExtensionStones) {
				result += " " + p;
			}
			result += "} blackExtensions: {";
			for (Point p : this.blackExtensionStones) {
				result += " " + p;
			}
			result += "}]";
		return result;
	}
}