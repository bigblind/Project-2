package com.gipf.client.player.bot.evaluation;

import com.gipf.client.offline.logic.Board;

public class EvaluationFunctionB implements EvaluationFunction {

	public int evaluate(Board board, int whiteStoneCnt, int blackStoneCnt) {
		int[] onBoard = this.stonesOnBoard(board);
		return onBoard[0] + whiteStoneCnt - (onBoard[1] + blackStoneCnt);
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
}
