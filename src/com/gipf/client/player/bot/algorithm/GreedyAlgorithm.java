package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;

public class GreedyAlgorithm extends Algorithm {

	public GreedyAlgorithm() {
		this.name = "Greedy Algorithm";
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

		return super.getActionsToNode(tree, bestNodes.get((int) (Math.random() * bestNodes.size())));
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

	public ArrayList<Action> calculateBestActions(Node node, int depth, Bot player) {
		this.generator.generateTree(depth, node, player, player.getLogic());
		return this.calculateBestActions(new Tree(node), player);
	}
}
