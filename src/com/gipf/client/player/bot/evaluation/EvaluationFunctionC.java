package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;

/**
 * @author Simon, Franziska
 *
 *         This evaluation function is symmetric This function will strongly be
 *         influenced by removing opponent stones, and especially by gipf
 *         stones.
 */
public class EvaluationFunctionC implements EvaluationFunction {

	public int evaluate(Game game, Player player) {
		Board board = game.getBoard();
		
		int score = 0;
		
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			if (game.getPlayerOne().getStoneAccount() == 0) return -1000000;
			else if (game.getPlayerTwo().getStoneAccount() == 0) return 1000000;
		} else {
			if (game.getPlayerOne().getStoneAccount() == 0) return 1000000;
			else if (game.getPlayerTwo().getStoneAccount() == 0) return -1000000;
		}

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

		if (game.isStandard()) {
			if (player.getStoneColor() == Board.WHITE_VALUE) {
				if (whiteGipfStones == 0) return -1000000;
				else if (blackGipfStones == 0) return 1000000;
			} else {
				if (whiteGipfStones == 0) return 1000000;
				else if (blackGipfStones == 0) return -1000000;
			}
		}
		
		score = 10 * (whiteGipfStones - blackGipfStones) + 1 * ((whiteStones + game.getPlayerOne().getStoneAccount()) - (blackStones + game.getPlayerTwo().getStoneAccount()));
		
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			if (game.getPlayerOne().getStoneAccount() < 7) {
				score += 10 * game.getPlayerOne().getStoneAccount();
			} else if (game.getPlayerTwo().getStoneAccount() < 7) {
				score += 5 * whiteStones;
			}
		} else {
			if (game.getPlayerOne().getStoneAccount() < 7) {
				score -= 10 * game.getPlayerTwo().getStoneAccount();
			} else if (game.getPlayerOne().getStoneAccount() < 7) {
				score -= 5 * whiteStones;
			}
		}
		
//		System.out.println("white stack: " + game.getPlayerOne().getStoneAccount() + " black stack: " + game.getPlayerTwo().getStoneAccount() + " white on board: " + whiteStones + " black on board: " + blackStones);
		
		if (player.getStoneColor() == Board.WHITE_VALUE) return score;
		else return -score;
	}

	public String toString() {
		return "Stone Taker";
	}
}
