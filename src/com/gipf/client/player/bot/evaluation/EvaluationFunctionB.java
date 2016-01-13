package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;

public class EvaluationFunctionB implements EvaluationFunction {

	private String name;

	public EvaluationFunctionB(String name) {
		this.name = name;
	}

	public int evaluate(Game game, Player player) {
		int[] onBoard = this.stonesOnBoard(game.getBoard());
		if (player.getStoneColor() == Board.WHITE_VALUE) return onBoard[0] + game.getPlayerOne().getStoneAccount() - (onBoard[1] + game.getPlayerTwo().getStoneAccount());
		else return -(onBoard[0] + game.getPlayerOne().getStoneAccount() - (onBoard[1] + game.getPlayerTwo().getStoneAccount()));
	}

	public int[] stonesOnBoard(Board board) {
		int whiteStones = 0;
		int blackStones = 0;

		for (int i = 0; i < board.getGrid().length; i++) {
			for (int j = 0; j < board.getGrid()[0].length; j++) {
				if (board.getGrid()[i][j] == Board.WHITE_VALUE) whiteStones++;
				else if (board.getGrid()[i][j] == Board.GIPF_WHITE_VALUE) whiteStones += 2;
				else if (board.getGrid()[i][j] == Board.BLACK_VALUE) blackStones++;
				else if (board.getGrid()[i][j] == Board.GIPF_BLACK_VALUE) blackStones += 2;
			}
		}

		return new int[] { whiteStones, blackStones };
	}

	public String toString() {
		return this.name;
	}
}
