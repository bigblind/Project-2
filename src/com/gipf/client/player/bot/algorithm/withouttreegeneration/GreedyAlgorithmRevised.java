package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionC;

public class GreedyAlgorithmRevised extends Algorithm {

	public GreedyAlgorithmRevised() {
		super();
		this.name = "Greedy Algorithm Revised P+O";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Game untouched = game.copy();
		Game use = game.copy();

		Node root = new Node(null, game, null, true);
		root.setPossibleActions(generator.getPossibleActions(root));

		ArrayList<Node> optimal = new ArrayList<Node>();
		for (Action action : root.getPossibleActions()) {
			Game untouchedChild = use.copy();
			Game useChild = use.copy();

			Node child = super.performAction(action, use, player, evaluator);
			root.addChild(child);
			child.setParent(root);

			child.setPossibleActions(generator.getPossibleActions(child));

			boolean care = false;
			for (Action childAction : child.getPossibleActions()) {
				Node grandChild = super.performAction(childAction, useChild, player, evaluator);
				child.addChild(grandChild);
				grandChild.setParent(child);

				if (optimal.size() == 0) {
					optimal.add(grandChild);
					care = true;
				} else if (grandChild.getValue() > optimal.get(0).getValue()) {
					optimal.clear();
					optimal.add(grandChild);
					care = true;
				} else if (grandChild.getValue() == optimal.get(0).getValue()) {
					optimal.add(grandChild);
					care = true;
				}

				useChild.getBoard().setGrid(untouchedChild.getBoard().getGrid());
				useChild.getPlayerOne().setStoneAccount(untouchedChild.getPlayerOne().getStoneAccount());
				useChild.getPlayerTwo().setStoneAccount(untouchedChild.getPlayerTwo().getStoneAccount());
			}

			if (!care) {
				root.removeChild(child);
				child = null;
			}

			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}
		return super.getActionsToNode(new Tree(root),  optimal.get((int) (Math.random() * optimal.size())));
	}

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		return calculateBestActions(tree.root().getGame(), player, new EvaluationFunctionC());
	}
}
