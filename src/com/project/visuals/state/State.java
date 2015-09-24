package com.project.visuals.state;

import com.project.logic.Game;
import com.project.logic.Player;
import com.project.visuals.GhostBoardButton;

public abstract class State {

	protected GhostBoardButton[][] buttons;
	protected Game game;
	protected Player activePlayer;

	public State(GhostBoardButton[][] buttons, Game game) {
		this.buttons = buttons;
		this.game = game;
	}

	public void execute() {
		this.activePlayer = game.getGameLogic().getA
		this.removeListeners();
		
	}

	public void removeListeners() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				if (this.buttons[i][j].getMouseListeners().length > 0) this.buttons[i][j].removeMouseListener(this.buttons[i][j].getMouseListeners()[0]);
			}
		}
	}
}
