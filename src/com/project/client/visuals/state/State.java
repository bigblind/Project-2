package com.project.client.visuals.state;

import java.awt.Image;

import com.project.client.board.Board;
import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardButton;
import com.project.client.visuals.BoardPanel;
import com.project.client.visuals.ResourceLoader;

public abstract class State {

	protected BoardPanel boardPanel;
	protected BoardButton[][] buttons;
	protected ClientInterface clientInterface;	
	protected Image stoneImage;

	public State(BoardPanel boardPanel, ClientInterface clientInterface) {
		this.buttons = boardPanel.getButtons();
		this.boardPanel = boardPanel;
		this.clientInterface = clientInterface;
	}

	public void execute() {
		if (this.clientInterface.getThisPlayer().getStoneColor() == Board.BLACK_VALUE) this.stoneImage = ResourceLoader.BLACK_STONE_TRANSPARENT;
		else if (this.clientInterface.getThisPlayer().getStoneColor() == Board.WHITE_VALUE) this.stoneImage = ResourceLoader.WHITE_STONE_TRANSPARENT;
		this.removeListeners();
	}

	public void removeListeners() {
		System.out.println("ejfkle;");
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				if (this.buttons[i][j] != null && this.buttons[i][j].getMouseListeners().length > 0) this.buttons[i][j].removeMouseListener(this.buttons[i][j].getMouseListeners()[0]);
			}
		}
	}
}
