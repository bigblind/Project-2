package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.player.bot.algorithm.withouttreegeneration.Algorithm;

public class MinMaxABPAlgorithm extends Algorithm {

	public MinMaxABPAlgorithm() {
		this.name = "MinMax with Alpha-Beta pruning Algorithm";
	}

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		Node alpha = new Node(null, null, null, false);
		alpha.setValue(-10000000);
		Node beta = new Node(null, null, null, false);
		beta.setValue(10000000);
		Node node = max(tree, tree.root(), player, alpha, beta);
		return super.getActionsToNode(tree, node);
	}
	
	public ArrayList<Action> calculateBestActions(Node node, int depth, Bot player) {
		this.treeGenerator.generateTree(depth, node, player, player.getLogic());
		return this.calculateBestActions(new Tree(node), player);
	}

	public Node calculateBestNode(Tree tree, Bot player) {
		Node alpha = new Node(null, null, null, false);
		alpha.setValue(-10000000);
		Node beta = new Node(null, null, null, false);
		beta.setValue(10000000);
		Node node = max(tree, tree.root(), player, alpha, beta);
		return node;
	}

	public Node max(Tree tree, Node node, Player player, Node alpha, Node beta) {
		if (tree.isExternal(node)) {
			return node;
		} else {
			for (Node child : node.getChildren()) {
				Node score = min(tree, child, this.opponent(player), alpha, beta);
				if (player.getStoneColor() == Board.WHITE_VALUE) {
					if (score.getValue() > alpha.getValue()) alpha = score;
					if (alpha.getValue() >= beta.getValue()) return alpha;
				} else {
					if (-score.getValue() > alpha.getValue()) alpha = score;
					if (alpha.getValue() >= beta.getValue()) return alpha;
				}
			}
			return alpha;
		}
	}

	public Node min(Tree tree, Node node, Player player, Node alpha, Node beta) {
		if (tree.isExternal(node)) {
			return node;
		} else {
			for (Node child : node.getChildren()) {
				Node score = max(tree, child, this.opponent(player), alpha, beta);
				if (player.getStoneColor() == Board.WHITE_VALUE) {
					if (score.getValue() < beta.getValue()) beta = score;
					if (alpha.getValue() >= beta.getValue()) return beta;
				} else {
					if (-score.getValue() < beta.getValue()) beta = score;
					if (alpha.getValue() >= beta.getValue()) return beta;
				}
			}
			return beta;
		}
	}
}
