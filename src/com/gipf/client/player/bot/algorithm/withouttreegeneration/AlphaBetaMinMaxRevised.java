package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;
import java.util.List;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class AlphaBetaMinMaxRevised extends Algorithm {

	private MinMaxRevised minmax;
	private Node currentOptimal;

	public AlphaBetaMinMaxRevised() {
		this.minmax = new MinMaxRevised();
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {

	}

	public int max(Node node, Bot bot, EvaluationFunction evaluator, int depth, int alpha, int beta) {
		if (depth > super.TREE_DEPTH) {
			if ()
		}
	}

	public int min(Node node, Bot bot, EvaluationFunction evaluator, int depth, int alpha, int beta) {

	}

	public int alphaBeta(Node node, int depth, int alpha, int beta) {
		if (win()) {
			return playerWin ? winRating : -winRating;
		} else if (depth <= 0) {
			return nodeRating;
		}

		List<Node> children = generateChildren(); // generates children. also rates them and applies move to copy of field. 

		if (currentPlayer == ai) { // ai tries to maximize the score
			for (Node child : children) {
				alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta));

				if (beta <= alpha) {
					break; // cutoff
				}
			}
			return alpha;
		} else { // enemy tries to minimize the score
			for (Node child : children) {
				beta = Math.min(beta, alphaBeta(child, depth - 1, alpha, beta));
				if (beta <= alpha) {
					break; // cutoff
				}
			}
			return beta;
		}
	}

}
