package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;

public class ActionRetrievalMethod {

	public ActionRetrievalMethod() {

	}
	
	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		ArrayList<Node> search = tree.dfSearch(tree.root(), new ArrayList<Node>());
		int bestValue = -1;

		ArrayList<Node> bestNodes = new ArrayList<Node>();
		bestNodes.add(tree.root().getChildren().get(0));

		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (node.getValue() > bestValue) {
						bestValue = node.getValue();
						bestNodes.clear();
						bestNodes.add(node);
					} else if (node.getValue() == bestValue) {
						bestNodes.add(node);
					}
				}
			}
		} else {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (-node.getValue() > bestValue) {
						bestValue = -node.getValue();
						bestNodes.clear();
						bestNodes.add(node);
					} else if (-node.getValue() == bestValue) {
						bestNodes.add(node);
					}
				}
			}
		}

		return this.getActionsToNode(tree, bestNodes.get((int) (Math.random() * bestNodes.size())));
	}
	
	public ArrayList<Action> getActionsToNode(Tree tree, Node node) {
		ArrayList<Action> result = new ArrayList<Action>();
		while (!node.equals(tree.root())) {
			if (node.getEndState()) {
				result.clear();
				result.add(node.getAction());
			} else {
				result.add(node.getAction());
			}
			node = node.getParent();
		}
		ArrayList<Action> actualResult = new ArrayList<Action>();
		for (int i = result.size() - 1; i >= 0; i--) {
			actualResult.add(result.get(i));
		}
		return actualResult;
	}
	
	public Node calculateBestNode(Tree tree, Bot player) {
		
		
		ArrayList<Node> search = tree.dfSearch(tree.root(), new ArrayList<Node>());
		int bestValue = -1;

		ArrayList<Node> bestNodes = new ArrayList<Node>();
		bestNodes.add(tree.root().getChildren().get(0));

		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (node.getValue() > bestValue) {
						bestValue = node.getValue();
						bestNodes.clear();
						bestNodes.add(node);
					} else if (node.getValue() == bestValue) {
						bestNodes.add(node);
					}
				}
			}
		} else {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (-node.getValue() > bestValue) {
						bestValue = -node.getValue();
						bestNodes.clear();
						bestNodes.add(node);
					} else if (-node.getValue() == bestValue) {
						bestNodes.add(node);
					}
				}
			}
		}

		return bestNodes.get((int) (Math.random() * bestNodes.size()));
	}
}
