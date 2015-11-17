package com.gipf.client.offline.logic;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.PlayerEvent;

public class StandardGameLogic extends GameLogic {

	public StandardGameLogic(Game game, LocalServer controller) {
		super(game, controller);
	}

	public void playerEventPerformed(PlayerEvent e) {
		if (!this.game.getBoard().isValidMove(e.getFromPoint(), e.getToPoint())) {
			this.controller.sendMoveValidity(false);
			return;
		}
		this.controller.sendMoveValidity(true);
		this.game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
		this.getCurrentPlayer().setStoneAccount(this.getCurrentPlayer().getStoneAccount() - 1);

		if (this.handleRows()) return;

		this.moveToNextPlayer();
		if (this.checkForWin()) {
			this.controller.sendWinLoseUpdate(this.returnWinner());
		}
	}

	public boolean checkForWin() {
		if (super.checkForWin()) return true;
		boolean[] containGipfStones = this.game.getBoard().containGipfStones();

		if (!containGipfStones[0]) return true;
		else if (!containGipfStones[1]) return true;
		else return false;
	}

	protected Player returnWinner() {
		if (game.getPlayerOne().getStoneAccount() == 0) return game.getPlayerTwo();

		if (game.getPlayerTwo().getStoneAccount() == 0) return game.getPlayerOne();

		boolean[] containGipfStones = this.game.getBoard().containGipfStones();

		if (!containGipfStones[0]) return this.game.getPlayerOne();
		else if (!containGipfStones[1]) return this.game.getPlayerTwo();

		return null;
	}
}
