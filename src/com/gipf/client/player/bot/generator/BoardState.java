package com.gipf.client.player.bot.generator;

import com.gipf.client.offline.logic.Board;
import com.gipf.client.utils.Point;

public class BoardState {

	private Board board;
	private Point from;
	private Point to;
	
	public BoardState(Board board, Point from, Point to) {
		this.board = board;
		this.from = from;
		this.to = to;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Point getFromPoint() {
		return this.from;
	}
	
	public Point getToPoint() {
		return this.to;
	}
}
