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
		ArrayList<Action> bestActions = new ArrayList<Action>();
		ArrayList<Node> tmp = new ArrayList<Node>(); // Store the nodes which are not external nodes

		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (node.getValue() > bestValue) {
						bestValue = node.getValue();
						for (Node tmpNode : tmp) {
							bestActions.add(tmpNode.getAction());
						}
						bestActions.add(node.getAction());
					}
					tmp.clear();

				} else {
					tmp.add(node);
				}
			}
		} else {
			for (Node node : search) {
				if (tree.isExternal(node)) {
					if (-node.getValue() > bestValue) {
						bestValue = -node.getValue();
						for (Node tmpNode : tmp) {
							bestActions.add(tmpNode.getAction());
						}
						bestActions.add(node.getAction());
					}
					tmp.clear();

				} else {
					tmp.add(node);
				}
			}
		}

		return bestActions;
	}
}
