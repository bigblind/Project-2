package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class MinMaxRevised extends Algorithm {

	public MinMaxRevised() {
		this.name = "Min Max Revised";
	}
	
	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Node root = new Node(null, game, null, true);
		return super.getActionsToNode(new Tree(root), max(root, player, evaluator, 1));
	}
	
	public Node max(Node node, Bot player, EvaluationFunction evaluator, int depth) {
		if (depth > super.TREE_DEPTH) return node;
		
		Game untouched = node.getGame().copy();
		Game use = node.getGame().copy();
		
		ArrayList<Node> minResults = new ArrayList<Node>();
		
		for (Action action : super.generator.getPossibleActions(node)) {
			Node child = super.performAction(action, use, player, evaluator);
			node.addChild(child);
			child.setParent(node);
			
			for (Node bottom : child.bottomChildren()) {
				minResults.add(min(bottom, player, evaluator, depth + 1));
			}
			
			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}
		
		Collections.sort(minResults);
		for (int i = 0; i < minResults.size() - 1; i++) {
			Node n = minResults.get(i);
			node.removeChild(n);
			minResults.remove(n);
			n = null;
		}
		return minResults.get(0); // TODO return a random move ( last indices of the list )
	}
	
	public Node min(Node node, Bot player, EvaluationFunction evaluator, int depth) {
		if (depth > super.TREE_DEPTH) return node;
		
		Game untouched = node.getGame().copy();
		Game use = node.getGame().copy();
		
		ArrayList<Node> maxResults = new ArrayList<Node>();
		
		for (Action action : super.generator.getPossibleActions(node)) {
			Node child = super.performAction(action, use, player, evaluator);
			node.addChild(child);
			child.setParent(node);
			
			for (Node bottom : child.bottomChildren()) {
				maxResults.add(max(bottom, player, evaluator, depth + 1));
			}
			
			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}
		
		Collections.sort(maxResults);
		for (int i = 1; i < maxResults.size(); i++) {
			Node n = maxResults.get(i);
			node.removeChild(n);
			maxResults.remove(n);
			n = null;
		}
		return maxResults.get(0); // TODO return a random move ( last indices of the list )
	}
}
