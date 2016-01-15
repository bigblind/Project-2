package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Game;

public interface EvaluationFunction {

	public static final int WIN_VALUE = 100000;
	
	int evaluate(Game game, Player player);
}
