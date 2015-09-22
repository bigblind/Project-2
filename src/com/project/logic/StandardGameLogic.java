package com.project.logic;

public class StandardGameLogic extends GameLogic {

	public StandardGameLogic(Game game) {
		super(game);
		game.getBoard().getGrid()[2][1] = 111;
	}

	public void loop() {
		
	}

}
