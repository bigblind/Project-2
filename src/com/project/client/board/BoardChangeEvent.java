package com.project.client.board;

public class BoardChangeEvent {

	private Board board;
	
	public BoardChangeEvent(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
