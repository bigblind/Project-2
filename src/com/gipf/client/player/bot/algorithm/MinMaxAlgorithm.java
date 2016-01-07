package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;

public class MinMaxAlgorithm extends Algorithm {

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		Node node = max(tree, tree.root(), player);
		return super.getActionsToNode(tree, node);
	}

	public Node max(Tree tree, Node node, Player player) {
		if (tree.isExternal(node)) {
			return node;
		} else {
			ArrayList<Node> children = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				children.add(min(tree, child, player));
			}
			ArrayList<Node> bestNodes = new ArrayList<Node>();
			bestNodes.add(children.get(0));
			if (player.getStoneColor() == Board.WHITE_VALUE) {
				for (Node child : children) {
					if (child.getValue() > bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (child.getValue() == bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			} else {
				for (Node child : children) {
					if (-child.getValue() > -bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (-child.getValue() == -bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			}

			return bestNodes.get((int) (Math.random() * bestNodes.size()));
		}
	}

	public Node min(Tree tree, Node node, Player player) {
		if (tree.isExternal(node)) {
			return node;
		} else {
			ArrayList<Node> children = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				children.add(max(tree, child, player));
			}
			ArrayList<Node> bestNodes = new ArrayList<Node>();
			bestNodes.add(children.get(0));
			if (player.getStoneColor() == Board.BLACK_VALUE) { // black value because of change of player 
				for (Node child : children) {
					if (child.getValue() < bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (child.getValue() == bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			} else {
				for (Node child : children) {
					if (-child.getValue() < -bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (-child.getValue() == -bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			}

			return bestNodes.get((int) (Math.random() * bestNodes.size()));
		}
	}
}
