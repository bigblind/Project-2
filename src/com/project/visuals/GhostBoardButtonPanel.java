package com.project.visuals;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.project.logic.Game;
import com.project.logic.Point;
import com.project.visuals.state.MoveStateA;
import com.project.visuals.state.State;

public class GhostBoardButtonPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -4895368866649881850L;

	/*
	 * maybe make an init somewhere that then sets the state of the GhostBoardButtonPanel, sets the active player and everything
	 */
	
	private State state; // TODO needs to be changed
	private GhostBoardButton[][] buttons;
	private BoardPanel boardPanel;
	private int tileSize;
	private Point[][] coordinates;

	public GhostBoardButtonPanel(Game game, BoardPanel boardPanel) {
		this.setOpaque(false);
		this.setLayout(null);

		this.boardPanel = boardPanel;
		boardPanel.addComponentListener(this);

		this.coordinates = boardPanel.getCoordinates();
		this.tileSize = boardPanel.getTileSize();

		this.buttons = new GhostBoardButton[9][9];

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				GhostBoardButton button = new GhostBoardButton();
				button.setContentAreaFilled(false);
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(j));
				
				if (i == 0 || j == 0 || j == 8 || i == 8) button.setIsOuterDot(true);
				else if (i == 1 && j == 5) button.setIsOuterDot(true);
				else if (i == 2 && j == 6)  button.setIsOuterDot(true);
				else if (i == 3 && j == 7) button.setIsOuterDot(true);
				else if (i == 5 && j == 1) button.setIsOuterDot(true);
				else if (i == 6 && j == 2) button.setIsOuterDot(true);
				else if (i == 7 && j == 3) button.setIsOuterDot(true);
				
				this.buttons[i][j] = button;
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				GhostBoardButton button = new GhostBoardButton();
				button.setContentAreaFilled(false);
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(4 + j));
				
				if (i == 0 || j == 0 || j == 4 || i == 8) button.setIsOuterDot(true);
				else if (i == 1 && j == 5) button.setIsOuterDot(true);
				else if (i == 2 && j == 6)  button.setIsOuterDot(true);
				else if (i == 3 && j == 7) button.setIsOuterDot(true);
				else if (i == 5 && j == 1) button.setIsOuterDot(true);
				else if (i == 6 && j == 2) button.setIsOuterDot(true);
				else if (i == 7 && j == 3) button.setIsOuterDot(true);
				
				this.buttons[i][4 + j] = button;
			}
		}

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				this.add(this.buttons[i][j]);
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				this.add(this.buttons[i][4 + j]);
			}
		}

		this.state = new MoveStateA(this, game);
		this.state.execute();
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {
		this.coordinates = boardPanel.getCoordinates();
		this.tileSize = boardPanel.getTileSize();
		this.setSize(this.boardPanel.getSize());

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				this.buttons[i][j].setBounds(this.coordinates[i][j].getX(), this.coordinates[i][j].getY(), this.tileSize, this.tileSize);
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				this.buttons[i][4 + j].setBounds(this.coordinates[i][4 + j].getX(), this.coordinates[i][4 + j].getY(), this.tileSize, this.tileSize);
			}
		}
	}

	public void componentShown(ComponentEvent e) {

	}

	public GhostBoardButton[][] getButtons() {
		return this.buttons;
	}

	public void setState(State state) {
		this.state = state;
		this.state.execute();
	}
	
	public Game getGame() {
		return this.getGame();
	}
}
