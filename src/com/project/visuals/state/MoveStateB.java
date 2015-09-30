package com.project.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.logic.Game;
import com.project.visuals.BoardButton;
import com.project.visuals.BoardPanel;

public class MoveStateB extends State {

	private BoardButton pressedButton;
	private MouseListener listener;

	public MoveStateB(BoardPanel boardPanel, Game game, BoardButton pressedButton) {
		super(boardPanel, game);

		this.pressedButton = pressedButton;
		
		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (((BoardButton) e.getComponent()).getIsOuterDot() == true) {
					pressedButton.setDraw(false);
					boardPanel.setState(new MoveStateB(boardPanel, game, (BoardButton) e.getComponent()));
					boardPanel.repaint();
				} else {
					// TODO check if it is a possible move ( maybe in game
					// logic, that makes more sense ) so just make player fire
					// it anyway
					// for server purpose, the player doesn't do the turn until
					// the server / logic verifies it as a valid move
					// TODO player needs to be notified so it can fire the event
				}
			}

			public void mouseEntered(MouseEvent e) {
				((BoardButton) e.getComponent()).setDraw(true);
				e.getComponent().repaint();
			}

			public void mouseExited(MouseEvent e) {
				if (!((BoardButton) e.getComponent()).equals(pressedButton)) ((BoardButton) e.getComponent()).setDraw(false);
				e.getComponent().repaint();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};
	}

	public void execute() {
		super.execute();

		for (int i = 0; i < 5; i++) {
			this.buttons[i][0].setImage(this.stoneImage);
			this.buttons[i][0].addMouseListener(this.listener);
		}
		for (int i = 0; i < 6; i++) {
			this.buttons[i][1].setImage(this.stoneImage);
			this.buttons[i][1].addMouseListener(this.listener);
		}

		for (int i = 1; i < 5; i++) {
			this.buttons[0][i].setImage(this.stoneImage);
			this.buttons[0][i].addMouseListener(this.listener);
		}
		for (int i = 1; i < 5; i++) {
			this.buttons[1][i].setImage(this.stoneImage);
			this.buttons[1][i].addMouseListener(this.listener);
		}

		for (int i = 0; i < 4; i++) {
			this.buttons[1 + i][5 + i].setImage(this.stoneImage);
			this.buttons[1 + i][5 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 3; i++) {
			this.buttons[2 + i][5 + i].setImage(this.stoneImage);
			this.buttons[2 + i][5 + i].addMouseListener(this.listener);
		}

		for (int i = 0; i < 4; i++) {
			this.buttons[5 + i][8].setImage(this.stoneImage);
			this.buttons[5 + i][8].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[5 + i][7].setImage(this.stoneImage);
			this.buttons[5 + i][7].addMouseListener(this.listener);
		}

		for (int i = 0; i < 3; i++) {
			this.buttons[8][6 - i].setImage(this.stoneImage);
			this.buttons[8][6 - i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[7][6 - i].setImage(this.stoneImage);
			this.buttons[7][6 - i].addMouseListener(this.listener);
		}
		
		this.buttons[6][2].setImage(this.stoneImage);
		this.buttons[6][2].addMouseListener(this.listener);
		
		this.buttons[5][2].setImage(this.stoneImage);
		this.buttons[5][2].addMouseListener(this.listener);
		
		this.buttons[6][3].setImage(this.stoneImage);
		this.buttons[6][3].addMouseListener(this.listener);
	}
}
