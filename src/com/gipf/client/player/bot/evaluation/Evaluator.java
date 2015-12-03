package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.bot.tree.NodeOld;
import com.gipf.client.offline.logic.Board;

public class Evaluator {

	private EvaluationFunction function;
	
	public Evaluator(EvaluationFunction function) {
		this.function = function;
	}
	
	public void setEvaulationFunction(EvaluationFunction function) {
		this.function = function;
	}
	
	public NodeOld evalToNode(Board board, int whiteStoneCnt, int blackStoneCnt) {
		return new NodeOld(board, this.function.evaluate(board, whiteStoneCnt, blackStoneCnt));
	}
	
	public int evaluate(Board board, int whiteStoneCnt, int blackStoneCnt) {
		return this.function.evaluate(board, whiteStoneCnt, blackStoneCnt);
	}
}
