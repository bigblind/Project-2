package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class MinMax extends Algorithm implements IterativeDeepeningAddition {

	public MinMax() {
		this.name = "Min Max";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Node root = new Node(null, game, null, true);
		return super.getActionsToNode(max(root, player, evaluator, 1, super.TREE_DEPTH));
	}

	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator, int ply) {
		Node root = new Node(null, game, null, true);
		return max(root, player, evaluator, 1, ply);
	}

	public Node max(Node node, Bot player, EvaluationFunction evaluator, int depth, int maxDepth) {
		if (depth > maxDepth) {
			System.out.println(node.getValue());
			if (node.getValue() == EvaluationFunction.WIN_VALUE) {
				System.out.println("a win node has been found");
			}
			return node;
		}

		Game untouched = node.getGame().copy();
		Game use = node.getGame().copy();

		ArrayList<Node> minResults = new ArrayList<Node>();

		for (Action action : super.generator.getPossibleActions(node)) {
			Node child = super.performAction(action, use, player, evaluator);
			node.addChild(child);
			child.setParent(node);

			for (Node bottom : child.bottomChildren()) {
				minResults.add(min(bottom, player, evaluator, depth + 1, maxDepth));
			}

			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}

		Collections.sort(minResults);
		int listSize = minResults.size();
		for (int i = 0; i < listSize - 1; i++) {
			Node n = minResults.get(0);
			node.removeChild(n);
			minResults.remove(n);
			n = null;
		}
		
		return minResults.get(0); // TODO return a random move ( last indices of the list )
	}

	public Node min(Node node, Bot player, EvaluationFunction evaluator, int depth, int maxDepth) {
		if (depth > maxDepth) return node;

		Game untouched = node.getGame().copy();
		Game use = node.getGame().copy();

		ArrayList<Node> maxResults = new ArrayList<Node>();

		for (Action action : super.generator.getPossibleActions(node)) {
			Node child = super.performAction(action, use, player, evaluator);
			node.addChild(child);
			child.setParent(node);

			for (Node bottom : child.bottomChildren()) {
				maxResults.add(max(bottom, player, evaluator, depth + 1, maxDepth));
			}

			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}

		Collections.sort(maxResults);
		int listSize = maxResults.size();
		for (int i = 1; i < listSize; i++) {
			Node n = maxResults.get(1);
			node.removeChild(n);
			maxResults.remove(n);
			n = null;
		}
		return maxResults.get(0); // TODO return a random move ( last indices of the list )
	}

	@Override
	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator) {
		// TODO Auto-generated method stub
		return null;
	}
}
