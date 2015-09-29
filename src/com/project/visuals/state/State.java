package com.project.visuals.state;

import java.awt.Image;

import com.project.logic.Board;
import com.project.logic.Game;
import com.project.logic.Player;
import com.project.visuals.BoardButtons;
import com.project.visuals.BoardPanel;
import com.project.visuals.ResourceLoader;

public abstract class State {

	protected BoardButtons[][] buttons;
	protected Game game;
	protected Player activePlayer;
	protected Image stoneImage;

	public State(BoardPanel boardPanel, Game game) {
		this.buttons = boardPanel.getButtons();
		this.game = game;
	}

	public void execute() {
		this.activePlayer = game.getGameLogic().getActivePlayer();
		if (this.activePlayer.getStoneColor() == Board.BLACK_VALUE) this.stoneImage = ResourceLoader.BLACK_STONE_TRANSPARENT;
		else if (this.activePlayer.getStoneColor() == Board.WHITE_VALUE) this.stoneImage = ResourceLoader.WHITE_STONE_TRANSPARENT;
		this.removeListeners();
	}

	public void removeListeners() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				if (this.buttons[i][j] != null && this.buttons[i][j].getMouseListeners().length > 0) this.buttons[i][j].removeMouseListener(this.buttons[i][j].getMouseListeners()[0]);
			}
		}
	}
}
