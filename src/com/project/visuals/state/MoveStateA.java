package com.project.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.logic.Game;
import com.project.visuals.GhostBoardButton;
import com.project.visuals.GhostBoardButtonPanel;

public class MoveStateA extends State {

	private MouseListener listener;

	public MoveStateA(GhostBoardButtonPanel boardButtonPanel, Game game) {
		super(boardButtonPanel, game);

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				boardButtonPanel.setState(new MoveStateB(boardButtonPanel, game, (GhostBoardButton) e.getComponent()));
			}

			public void mouseEntered(MouseEvent e) {
				((GhostBoardButton) e.getComponent()).setDraw(true);
				e.getComponent().repaint();
			}

			public void mouseExited(MouseEvent e) {
				((GhostBoardButton) e.getComponent()).setDraw(false);
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
		for (int i = 1; i < 5; i++) {
			this.buttons[0][i].setImage(this.stoneImage);
			this.buttons[0][i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[1 + i][5 + i].setImage(this.stoneImage);
			this.buttons[1 + i][5 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[5 + i][1 + i].setImage(this.stoneImage);
			this.buttons[5 + i][1 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[8][5 + i].setImage(this.stoneImage);
			this.buttons[8][5 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 3; i++) {
			this.buttons[5 + i][8].setImage(this.stoneImage);
			this.buttons[5 + i][8].addMouseListener(this.listener);
		}
	}
}