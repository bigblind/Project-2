package com.project.server.logic;

import com.project.common.player.Player;
import com.project.common.player.PlayerEvent;
import com.project.common.utils.Point;

public class Row extends PlayerEvent {
	
	private int whiteExtensionStones;
	private int blackExtensionStones;
	private int length;

	public Row(Point from, Point to, Player player, int length, int whiteExtensionStones, int blackExtensionStones) {
		super(from, to, player);
		this.whiteExtensionStones = whiteExtensionStones;
		this.blackExtensionStones = blackExtensionStones;
	}

	public int getLength() {
		return length;
	}

	public int getWhiteExtensionStones() {
		return whiteExtensionStones;
	}

	public int getBlackExtensionStones() {
		return blackExtensionStones;
	}

	public String toString() {
		return "[Row from: " + this.from + " to: " + this.to + " player: " + this.player + " length: " + this.length + " whiteExtensions: " + this.whiteExtensionStones + " blackExtensions " + this.blackExtensionStones + "]";
	}
}
