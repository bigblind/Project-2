package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Game;

public interface EvaluationFunction {

	int evaluate(Game game, Player player);
}
