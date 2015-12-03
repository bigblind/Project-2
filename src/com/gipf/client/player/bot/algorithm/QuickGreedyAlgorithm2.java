package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.generator.GameState;
import com.gipf.client.utils.Point;

public class QuickGreedyAlgorithm2 extends Algorithm {

	public QuickGreedyAlgorithm2(EvaluationFunction function) {
		super(function);
	}

	public Point[] returnBestMove(GameState gameState, Player player) {
		ArrayList<GameState> states = this.generator.generateStates(gameState, player, this.game.getGameLogic());
		
		int bestIndex = 0;
		int bestValue = -1;
		
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (int i = 0; i < states.size(); i++) {
				int value = this.evaluator.evaluate(states.get(i).getGame().getBoard(), this.game.getPlayerOne().getStoneAccount(), this.game.getPlayerTwo().getStoneAccount());
				if (value > bestValue) {
					bestValue = value;
					bestIndex = i;
				}
			}
		} else {
			for (int i = 0; i < states.size(); i++) {
				int value = -this.evaluator.evaluate(states.get(i).getGame().getBoard(), this.game.getPlayerOne().getStoneAccount(), this.game.getPlayerTwo().getStoneAccount());
				if (value > bestValue) {
					bestValue = value;
					bestIndex = i;
				}
			}
		}
		
		return new Point[] { states.get(bestIndex).getFromPoint(), states.get(bestIndex).getToPoint() };
	}
}
