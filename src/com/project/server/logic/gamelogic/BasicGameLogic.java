package com.project.server.logic.gamelogic;

import com.project.common.player.PlayerEvent;
import com.project.server.logic.Game;

public class BasicGameLogic extends GameLogic {

	public BasicGameLogic(Game game) {
		super(game);
	}

	public void playerEventPerformed(PlayerEvent e) {
		if (!this.game.getBoard().isValidMove(e.getFromPoint(), e.getToPoint())) return;
		this.game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
		this.getCurrentPlayer().setStoneAccount(this.getCurrentPlayer().getStoneAccount() - 1);

		if (this.handleRows()) return;

		this.moveToNextPlayer();
		if (this.checkForWin()) System.out.println("someone won");
	}
}