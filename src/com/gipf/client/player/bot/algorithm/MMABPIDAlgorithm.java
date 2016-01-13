package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.BotLogic;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.player.bot.algorithm.withouttreegeneration.Algorithm;

/**
 * MinMax with Alpha-Beta pruning and Iterative Deepening Algorithm
 */
public class MMABPIDAlgorithm extends Algorithm {

	private MinMaxABPAlgorithm mmabp;

	public MMABPIDAlgorithm() {
		this.name = "MinMax with Alpha-Beta pruning and Iterative Deepening Algorithm";
		this.mmabp = new MinMaxABPAlgorithm();
	}

	/**
	 * @param tree
	 *            Tree with only the root.
	 * @param player
	 *            Player for whom the best action will be calculated.
	 */
	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		BotLogic logic = player.getLogic();
		int depth = 1;
		
		Node bestNode = null;
		Tree currentTree = null;
		
		while (depth < this.TREE_DEPTH + 1) {
			Node root = tree.root().copy();
			this.treeGenerator.generateTree(depth, root, player, logic);
			currentTree = new Tree(root);
			bestNode = this.mmabp.calculateBestNode(currentTree, player);
			Player winner = bestNode.getGame().getGameLogic().returnWinner();
			depth++;
			if (winner != null && winner.getStoneColor() == player.getStoneColor()) break;
		}

		return super.getActionsToNode(currentTree, bestNode);
	}
	
	public ArrayList<Action> calculateBestActions(Node node, int depth, Bot player) {
		this.treeGenerator.generateTree(depth, node, player, player.getLogic());
		return this.calculateBestActions(new Tree(node), player);
	}

	/**
	 * @param tree
	 *            Tree with only the root.
	 * @param player
	 *            Player for whom the best action will be calculated.
	 */
	public Node calculateBestNode(Tree tree, Bot player) {
		BotLogic logic = player.getLogic();
		int depth = 1;
		
		Node bestNode = null;
		Tree currentTree = null;
		
		while (depth < this.TREE_DEPTH + 1) {
			Node root = tree.root().copy();
			this.treeGenerator.generateTree(depth, root, player, logic);
			currentTree = new Tree(root);
			
			bestNode = this.mmabp.calculateBestNode(currentTree, player);
			Player winner = bestNode.getGame().getGameLogic().returnWinner();
			depth++;
			if (winner != null && winner.getStoneColor() == player.getStoneColor()) break;
		}
		
		return bestNode;
	}
}
