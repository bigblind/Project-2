package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class IterativeDeepening extends Algorithm { //without sorting

	private Algorithm algorithm;
	
	public IterativeDeepening(Algorithm algorithm, String name) {
		super();
		super.name = name;
		this.algorithm = algorithm;
	}
	
	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		for(int i = 0; i < super.TREE_DEPTH; i++){
			
		}
		return null;
	}

}
