package com.project.logic.gamelogic;

import com.project.logic.Game;

public class StandardGameLogic extends GameLogic {

	public StandardGameLogic(Game game) {
		super(game);
		game.getBoard().getGrid()[2][1] = 111;
	}

	public void loop() {
		
	}

}
