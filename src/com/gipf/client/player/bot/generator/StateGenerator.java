package com.gipf.client.player.bot.generator;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.utils.Point;

public class StateGenerator {

	public StateGenerator() {

	}

	public ArrayList<BoardState> generateStates(Board board, Player player) {
		ArrayList<BoardState> states = new ArrayList<BoardState>();

		Point from;
		Point to1;
		Point to2;

		//correct, bottom right
		for (int i = 5; i < 8; i++) {
			from = new Point(i, 8);
			to1 = new Point(i - 1, 7);
			to2 = new Point(i, 7);
			if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
			if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		}
		// correct, right
		for (int j = 7; j >= 5; j--) {
			from = new Point(8, j);
			to1 = new Point(7, j - 1);
			to2 = new Point(7, j);
			if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
			if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		}
		// correct, top left
		for (int i = 1; i < 4; i++) {
			from = new Point(i, 0);
			to1 = new Point(i, 1);
			to2 = new Point(i + 1, 1);
			if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
			if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		}
		// correct, left
		for (int j = 1; j < 4; j++) {
			from = new Point(0, j);
			to1 = new Point(1, j);
			to2 = new Point(1, j + 1);
			if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
			if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		}
		
		// top right
		from = new Point(5, 1);
		to1 = new Point(4, 1);
		to2 = new Point(5, 2);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);

		from = new Point(6, 2);
		to1 = new Point(5, 2);
		to2 = new Point(6, 3);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		
		from = new Point(7, 3);
		to1 = new Point(6, 3);
		to2 = new Point(7, 4);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		
		// bottom left
		from = new Point(1, 5);
		to1 = new Point(1, 4);
		to2 = new Point(2, 5);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		
		from = new Point(2, 6);
		to1 = new Point(2, 5);
		to2 = new Point(3, 6);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		
		from = new Point(3, 7);
		to1 = new Point(3, 6);
		to2 = new Point(4, 7);
		if (board.isValidMove(from, to1)) this.addState(board, player, from, to1, states);
		if (board.isValidMove(from, to2)) this.addState(board, player, from, to2, states);
		
		from = new Point(8, 4);
		to1 = new Point(7, 4);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		from = new Point(8, 8);
		to1 = new Point(7, 7);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		from = new Point(4, 8);
		to1 = new Point(4, 7);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		from = new Point(0, 4);
		to1 = new Point(1, 4);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		from = new Point(0, 0);
		to1 = new Point(1, 1);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		from = new Point(4, 0);
		to1 = new Point(4, 1);
		if (board.isValidMove(from, to1)) addState(board, player, from, to1, states);

		return states;
	}

	private void addState(Board board, Player player, Point from, Point to, ArrayList<BoardState> states) {
		Board tmp = board.copy();
		tmp.place(player.getStoneColor(), from, to);
		states.add(new BoardState(tmp, from, to));
	}
}
