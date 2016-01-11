package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.player.bot.generator.ActionGenerator;

public class ProperMinMax extends Algorithm {

	private ActionGenerator actionGenerator;

	public ProperMinMax() {
		this.actionGenerator = new ActionGenerator();
		this.name = "Attempt to Proper MinMax";
	}

	//	public ArrayList<Action> calculateBestAction(Node node, Bot player) {
	//		Node result = max(node, player.getStoneColor(), 0, super.TREE_DEPTH);
	//		return super.getActionsToNode(new Tree(result), result);
	//	}

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		Node result = max(tree.root(), player.getStoneColor(), 0, super.TREE_DEPTH);
		return super.getActionsToNode(tree, result);
	}

	public ArrayList<Action> calculateBestActions(Node node, int depth, Bot player) {
		Node result = max(node, player.getStoneColor(), 0, depth);
		return super.getActionsToNode(new Tree(node), result);
	}

	public Node calculateBestNode(Tree tree, Bot player) {
		Node result = max(tree.root(), player.getStoneColor(), 0, super.TREE_DEPTH);
		return result;
	}

	public Node max(Node node, int stoneColor, int depth, int maxDepth) {
		//		if (time = blaaaa) boolean terminate = true;
		//		if (terminate) return blaaa;
		if (depth == maxDepth) return node;
		else {
			if (node.getPossibleActions().size() == 0) node.setPossibleActions(this.actionGenerator.getPossibleActions(node, 5));
			ArrayList<Node> grandChildren = new ArrayList<Node>();
			for (Action action : node.getPossibleActions()) {
				Node copy = node.copy();
				copy.setAction(action);
				copy.getGame().getBoard().place(stoneColor, action.getPoints()[0], action.getPoints()[1]);
				copy.setParent(node);
				copy.setEndState(true);
				node.addChild(copy);

				grandChildren.add(min(copy, this.opponentStoneColor(stoneColor), depth + 1, maxDepth));
			}

			ArrayList<Node> bestNodes = new ArrayList<Node>();
			bestNodes.add(grandChildren.get(0));
			if (stoneColor == Board.WHITE_VALUE) {
				for (Node child : grandChildren) {
					if (child.getValue() > bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (child.getValue() == bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			} else {
				for (Node child : grandChildren) {
					if (-child.getValue() > -bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (-child.getValue() == -bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			}
			//			for (possibilities) {
			//				do possibility
			//				bla(new node, depth + 1, maxDepth);
			//				
			//				some best so far if it's ont completely done, for choice if time is over ? 
			//				
			//				return best of these ( in min max fasion)
			//			}
			return bestNodes.get((int) (Math.random() * bestNodes.size()));
		}
	}

	public Node min(Node node, int stoneColor, int depth, int maxDepth) {
		//		if (time = blaaaa) boolean terminate = true;
		//		if (terminate) return blaaa;
		if (depth == maxDepth) return node;
		else {
			if (node.getPossibleActions().size() == 0) node.setPossibleActions(this.actionGenerator.getPossibleActions(node, 5));
			ArrayList<Node> grandChildren = new ArrayList<Node>();
			for (Action action : node.getPossibleActions()) {
				Node copy = node.copy();
				copy.setAction(action);
				copy.getGame().getBoard().place(stoneColor, action.getPoints()[0], action.getPoints()[1]);
				copy.setParent(node);
				copy.setEndState(true);
				node.addChild(copy);
				
				grandChildren.add(max(copy, this.opponentStoneColor(stoneColor), depth + 1, maxDepth));
			}

			ArrayList<Node> bestNodes = new ArrayList<Node>();
			bestNodes.add(grandChildren.get(0));
			if (stoneColor == Board.WHITE_VALUE) {
				for (Node child : grandChildren) {
					if (child.getValue() < bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (child.getValue() == bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			} else {
				for (Node child : grandChildren) {
					if (-child.getValue() < -bestNodes.get(0).getValue()) {
						bestNodes.clear();
						bestNodes.add(child);
					} else if (-child.getValue() == -bestNodes.get(0).getValue()) bestNodes.add(child);
				}
			}
			//			for (possibilities) {
			//				do possibility
			//				bla(new node, depth + 1, maxDepth);
			//				
			//				some best so far if it's ont completely done, for choice if time is over ? 
			//				
			//				return best of these ( in min max fasion)
			//			}
			return bestNodes.get((int) (Math.random() * bestNodes.size()));
		}
	}
}
