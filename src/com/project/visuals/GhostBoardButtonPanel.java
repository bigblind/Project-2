package com.project.visuals;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.project.logic.Board;
import com.project.logic.Player;
import com.project.logic.Point;

public class GhostBoardButtonPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -4895368866649881850L;

	private TestButton[][] buttons;
	private BoardPanel boardPanel;
	private int tileSize;
	private Point[][] coordinates;

	public GhostBoardButtonPanel(BoardPanel boardPanel) {
		this.setOpaque(false);
		this.setLayout(null);

		this.boardPanel = boardPanel;
		boardPanel.addComponentListener(this);

		this.coordinates = boardPanel.getCoordinates();
		this.tileSize = boardPanel.getTileSize();

		this.buttons = new TestButton[9][9];

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				TestButton button = new TestButton();
				button.setContentAreaFilled(false);
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(j));
				button.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						
					}

					public void mouseEntered(MouseEvent e) {
						button.setPlayer(new Player(Board.WHITE_VALUE));
					}

					public void mouseExited(MouseEvent e) {
						button.setPlayer(null);
					}

					public void mousePressed(MouseEvent e) {
						
					}

					public void mouseReleased(MouseEvent e) {
						
					}
				});
				this.buttons[i][j] = button;
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				TestButton button = new TestButton();
				button.setContentAreaFilled(false);
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(4 + j));
				button.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						
					}

					public void mouseEntered(MouseEvent e) {
						button.setPlayer(new Player(Board.WHITE_VALUE));
					}

					public void mouseExited(MouseEvent e) {
						button.setPlayer(null);
					}

					public void mousePressed(MouseEvent e) {
						
					}

					public void mouseReleased(MouseEvent e) {
						
					}
				});
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
				this.add(this.buttons[i][4+j]);
			}
		}
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

}
