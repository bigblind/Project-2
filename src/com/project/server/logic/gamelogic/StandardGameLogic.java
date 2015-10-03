package com.project.server.logic.gamelogic;

import com.project.common.player.PlayerEvent;
import com.project.server.logic.Game;

public class StandardGameLogic extends GameLogic {

	public StandardGameLogic(Game game) {
		super(game);
		game.getBoard().getGrid()[2][1] = 111;
	}

	public void loop() {
		
	}

	public void playerEventPerformed(PlayerEvent e) {
		
	}
}
