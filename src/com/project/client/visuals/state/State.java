package com.project.client.visuals.state;

import java.awt.Image;

import com.gipf.client.game.GameController;
import com.gipf.client.resource.ResourceLoader;
import com.project.client.board.Board;
import com.project.client.visuals.board.BoardButton;
import com.project.client.visuals.board.GamePanel;

public abstract class State {

	protected GamePanel gamePanel;
	protected BoardButton[][] buttons;
	protected GameController gameController;	
	protected Image stoneImage;

	public State(final GamePanel gamePanel, final GameController controller) {
		this.buttons = gamePanel.getButtons();
		this.gamePanel = gamePanel;
		this.gameController = controller;
	}

	public void execute() {
		if (this.gameController.getThisPlayer().getStoneColor() == Board.BLACK_VALUE) this.stoneImage = ResourceLoader.BLACK_STONE_TRANSPARENT;
		else if (this.gameController.getThisPlayer().getStoneColor() == Board.WHITE_VALUE) this.stoneImage = ResourceLoader.WHITE_STONE_TRANSPARENT;
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
