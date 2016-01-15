package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class IterativeDeepening extends Algorithm { //without sorting

	private IterativeDeepeningAddition algorithm;
	
	public IterativeDeepening(IterativeDeepeningAddition algorithm, String name) {
		super();
		super.name = name;
		this.algorithm = algorithm;
	}
	
	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Node currentOptimum = null;
		for(int i = 1; i <= super.TREE_DEPTH; i++){
			Node result = this.algorithm.calculateBestNode(game, player, evaluator, i);
			if (currentOptimum == null) currentOptimum = result;
			else {
				if (currentOptimum.getValue() < result.getValue()) currentOptimum = result;
				
				if (currentOptimum.getValue() == EvaluationFunction.WIN_VALUE) break;
			}
		}
		return super.getActionsToNode(currentOptimum);
	}

	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator) {
		return null;
	}

}
