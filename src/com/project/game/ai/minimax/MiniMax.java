package com.project.game.ai.minimax;

import com.project.client.board.Board;
import com.project.common.player.Player;
import com.project.common.utils.Point;
import com.project.server.logic.Game;

public class MiniMax {
	private Game game;
	
	public MiniMax(Game game){
		this.game = game;
	}
	
	
	public Player miniMax(Board board, int depth, boolean onTurn){
		if(depth == 0 || game.getGameLogic().checkForWin())
			return null;
		return null;
	}
	
	
	
	
	
	

}
