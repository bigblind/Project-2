package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class MinMaxAlphaBeta extends Algorithm {

	private Node currentOptimal;

	public MinMaxAlphaBeta() {
		this.name = "Alpha Beta pruning Min Max Revised";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		this.currentOptimal = null;
		Node root = new Node(null, game, null, true);
		max(root, player, evaluator, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return super.getActionsToNode(new Tree(root), currentOptimal);
	}

	public int max(Node node, Bot player, EvaluationFunction evaluator, int depth, int alpha, int beta) {
		if (depth > super.TREE_DEPTH) {
			if (currentOptimal == null || currentOptimal.getValue() < node.getValue()) currentOptimal = node;
			return node.getValue();
		} else {
			Game untouched = node.getGame().copy();
			Game use = node.getGame().copy();

			for (Action action : super.generator.getPossibleActions(node)) {
				Node child = super.performAction(action, use, player, evaluator);
				node.addChild(child);
				child.setParent(node);

				for (Node bottom : child.bottomChildren()) {
					alpha = Math.max(alpha, min(bottom, player, evaluator, depth + 1, alpha, beta));
				}

				if (beta <= alpha) break;

				use.getBoard().setGrid(untouched.getBoard().getGrid());
				use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
				use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
			}
			return alpha;
		}
	}

	public int min(Node node, Bot player, EvaluationFunction evaluator, int depth, int alpha, int beta) {
		if (depth > super.TREE_DEPTH) {
			if (currentOptimal == null || currentOptimal.getValue() < node.getValue()) currentOptimal = node;
			return node.getValue();
		} else {
			Game untouched = node.getGame().copy();
			Game use = node.getGame().copy();

			for (Action action : super.generator.getPossibleActions(node)) {
				Node child = super.performAction(action, use, player, evaluator);
				node.addChild(child);
				child.setParent(node);

				for (Node bottom : child.bottomChildren()) {
					beta = Math.min(alpha, max(bottom, player, evaluator, depth + 1, alpha, beta));
				}

				if (beta <= alpha) break;

				use.getBoard().setGrid(untouched.getBoard().getGrid());
				use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
				use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
			}
			return beta;
		}
	}
}
