package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class MinMaxAlphaBeta extends Algorithm implements IterativeDeepeningAddition {

	private Node currentOptimal;

	public MinMaxAlphaBeta() {
		this.name = "Min Max w/ Alpha Beta pruning";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		this.currentOptimal = null;
		Node root = new Node(null, game, null, true);
		max(root, player, evaluator, 1, super.TREE_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return super.getActionsToNode(currentOptimal);
	}
	
	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator, int ply) {
		this.currentOptimal = null;
		Node root = new Node(null, game, null, true);
		max(root, player, evaluator, 1, ply, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return this.currentOptimal;
	}

	public int max(Node node, Bot player, EvaluationFunction evaluator, int depth, int maxDepth, int alpha, int beta) {
		if (depth > maxDepth) {
			if (currentOptimal == null || currentOptimal.getValue() < node.getValue()) this.currentOptimal = node;
			if (node.getValue() == EvaluationFunction.WIN_VALUE) {
				System.out.println("a win node has been found");
			}
			return node.getValue();
		} else {
			Game untouched = node.getGame().copy();
			Game use = node.getGame().copy();

			for (Action action : super.generator.getPossibleActions(node)) {
				Node child = super.performAction(action, use, player, evaluator);
				node.addChild(child);
				child.setParent(node);

				for (Node bottom : child.bottomChildren()) {
					alpha = Math.max(alpha, min(bottom, player, evaluator, depth + 1, maxDepth, alpha, beta));
				}

				if (beta <= alpha) break;

				use.getBoard().setGrid(untouched.getBoard().getGrid());
				use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
				use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
			}
			return alpha;
		}
	}

	public int min(Node node, Bot player, EvaluationFunction evaluator, int depth, int maxDepth, int alpha, int beta) {
		if (depth > maxDepth) {
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
					beta = Math.min(alpha, max(bottom, player, evaluator, depth + 1, maxDepth, alpha, beta));
				}

				if (beta <= alpha) break;

				use.getBoard().setGrid(untouched.getBoard().getGrid());
				use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
				use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
			}
			return beta;
		}
	}

	@Override
	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator) {
		// TODO Auto-generated method stub
		return null;
	}

}
