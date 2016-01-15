package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionC;

public class GreedyAlgorithmPO extends Algorithm {

	public GreedyAlgorithmPO() {
		super();
		this.name = "Greedy Algorithm Revised P+O";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Game untouched = game.copy();
		Game use = game.copy();

		Node root = new Node(null, game, null, true);

		ArrayList<Node> optimal = new ArrayList<Node>();

		for (Action action : generator.getPossibleActions(root)) {
			Game untouchedChild = use.copy();
			Game useChild = use.copy();

			Node child = super.performAction(action, use, player, evaluator);
			root.addChild(child);
			child.setParent(root);

			boolean care = false;
			for (Node bottom1 : child.bottomChildren()) {
				for (Action childAction : this.generator.getPossibleActions(bottom1)) {
					System.out.println(childAction);
					Node grandChild = super.performAction(childAction, useChild, player, evaluator);
					bottom1.addChild(grandChild);
					grandChild.setParent(bottom1);

					for (Node bottom2 : child.bottomChildren()) {
						if (optimal.size() == 0) {
							optimal.add(bottom2);
							care = true;
						} else if (bottom2.getValue() > optimal.get(0).getValue()) {
							optimal.clear();
							optimal.add(bottom2);
							care = true;
						} else if (bottom2.getValue() == optimal.get(0).getValue()) {
							optimal.add(bottom2);
							care = true;
						}
					}
					useChild.getBoard().setGrid(untouchedChild.getBoard().getGrid());
					useChild.getPlayerOne().setStoneAccount(untouchedChild.getPlayerOne().getStoneAccount());
					useChild.getPlayerTwo().setStoneAccount(untouchedChild.getPlayerTwo().getStoneAccount());
				}

			}

			if (!care) {
				root.removeChild(child);
				child = null;
			}

			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}
		return super.getActionsToNode(new Tree(root), optimal.get((int) (Math.random() * optimal.size())));
	}

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		return calculateBestActions(tree.root().getGame(), player, new EvaluationFunctionC());
	}
}
