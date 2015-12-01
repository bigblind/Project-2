package com.project.client.board;

import com.gipf.client.offline.logic.Board;

public class BoardChangeEvent {

	private Board board;
	
	public BoardChangeEvent(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
