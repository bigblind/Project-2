package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;

public class QuickGreedyAlgorithm extends Algorithm {

	public ArrayList<Action> calculateBestActions(Tree tree, Player player) {
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
}
