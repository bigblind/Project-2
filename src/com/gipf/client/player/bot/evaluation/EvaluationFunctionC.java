package com.gipf.client.player.bot.evaluation;

import com.gipf.client.offline.logic.Board;

/**
 * @author Simon, Franziska
 *
 *         This evaluation function is symmetric This function will strongly be
 *         influenced by removing opponent stones, and especially by gipf
 *         stones.
 */
public class EvaluationFunctionC implements EvaluationFunction {

	public int evaluate(Board board, int whiteStoneCnt, int blackStoneCnt, boolean isStandard) {
		if (whiteStoneCnt == 0) return -1000000;
		else if (blackStoneCnt == 0) return 1000000;

		int whiteGipfStones = 0;
		int whiteStones = 0;
		int blackGipfStones = 0;
		int blackStones = 0;

		for (int i = 0; i < board.getGrid().length; i++) {
			for (int j = 0; j < board.getGrid()[i].length; j++) {
				if (board.getGrid()[i][j] == Board.WHITE_VALUE) whiteStones++;
				else if (board.getGrid()[i][j] == Board.GIPF_WHITE_VALUE) whiteGipfStones++;
				else if (board.getGrid()[i][j] == Board.BLACK_VALUE) blackStones++;
				else if (board.getGrid()[i][j] == Board.GIPF_BLACK_VALUE) blackGipfStones++;
			}
		}

		if (isStandard) {
			if (whiteGipfStones == 0) return -1000000;
			else if (blackGipfStones == 0) return 1000000;
		}

		return 10 * (whiteGipfStones - blackGipfStones) + 1 * (whiteStones - blackStones);
	}

	public String toString() {
		return "Stone Taker";
	}
}
