package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;

public class MinMaxAlgorithm extends Algorithm {

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		Node node = max(tree, tree.root());
		return super.getActionsToNode(tree, node);
	}

	public Node max(Tree tree, Node node) {
		if (tree.isExternal(node)) {
			// Reached bottom (bare bottom!)
			return node;
		} else {
			ArrayList<Node> children = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				children.add(min(tree, child));
			}
			Node temp = children.get(0);
			for (Node child : children) {
				if (child.getValue() > temp.getValue()) temp = child;
			}
			return temp;
		}

	}

	public Node min(Tree tree, Node node) {
		if (tree.isExternal(node)) {
			// Reached bottom (bare bottom!)
			return node;
		} else {
			ArrayList<Node> children = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				children.add(min(tree, child));
			}
			Node temp = children.get(0);
			for (Node child : children) {
				if (child.getValue() < temp.getValue()) temp = child;
			}
			return temp;
		}
	}

}
