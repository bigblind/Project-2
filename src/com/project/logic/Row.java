package com.project.logic;

import com.project.logic.player.PlayerEvent;
import com.project.logic.player.Player;

public class Row extends PlayerEvent{
	private int whiteExtensionStones;
	private int blackExtensionStones;
	private int length;
	
	public Row(Point from, Point to, Player player, int length, int whiteExtensionStones, int blackExtensionStones) {
		super(from, to, player);
		this.whiteExtensionStones = whiteExtensionStones;
		this.blackExtensionStones = blackExtensionStones;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getWhiteExtensionStones(){
		return whiteExtensionStones;
	}
	
	public int getBlackExtensionStones(){
		return blackExtensionStones;
	}

}
