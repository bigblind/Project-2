package com.gipf.client.player.bot.evaluation;

import com.gipf.client.offline.logic.Board;

public interface EvaluationFunction {

	int evaluate(Board board, int whiteStoneCnt, int blackStoneCnt, boolean isStandard);
}
