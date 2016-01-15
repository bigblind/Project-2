package com.gipf.client.player.bot.algorithm;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public interface IterativeDeepeningAddition {
	
	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator, int ply);
	

}
