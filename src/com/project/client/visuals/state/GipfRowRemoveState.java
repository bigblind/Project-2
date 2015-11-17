package com.project.client.visuals.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gipf.client.game.GameController;
import com.gipf.client.resource.ResourceLoader;
import com.gipf.client.utils.Point;
import com.project.client.board.Board;
import com.project.client.visuals.board.GamePanel;

public class GipfRowRemoveState extends State {

	private MouseListener listener;
	private Point[] rows;
	private Point[] gipfStonePoints;
	private Board originalBoard;
	private Board boardCopy;
	private boolean[] gipfIsGhost;

	public GipfRowRemoveState(final GamePanel gamePanel, final GameController controller, Point[] row) {
		super(gamePanel, controller);
		this.rows = row;

		this.gamePanel.getCheckButton().setVisible(true);
		this.gamePanel.getCheckButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.getCheckButton().removeActionListener(this);

				String send = "/removepoints ";
				for (int i = 0; i < gipfIsGhost.length; i++) {
					if (gipfIsGhost[i]) {
						buttons[gipfStonePoints[i].getX()][gipfStonePoints[i].getY()].setDraw(false);
						send += gipfStonePoints[i];
					}
				}
				send += " checkrows";
				gamePanel.getGame().setBoard(originalBoard);
				gamePanel.getCheckButton().setVisible(false);
				gamePanel.getCheckButton().removeActionListener(this);
				gameController.getConnector().send(send);
			}
		});

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				for (int i = 0; i < gipfStonePoints.length; i++) {
					if (gipfStonePoints[i].getX() == x && gipfStonePoints[i].getY() == y) {
						if (gipfIsGhost[i]) {
							boardCopy.getGrid()[gipfStonePoints[i].getX()][gipfStonePoints[i].getY()] = originalBoard.getGrid()[gipfStonePoints[i].getX()][gipfStonePoints[i].getY()];
							buttons[x][y].setDraw(false);
							gipfIsGhost[i] = false;
							gamePanel.repaint();
						} else {
							boardCopy.getGrid()[gipfStonePoints[i].getX()][gipfStonePoints[i].getY()] = 0;
							buttons[x][y].setDraw(true);
							gipfIsGhost[i] = true;
							gamePanel.repaint();
						}
						break;
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				boardCopy.getGrid()[x][y] = Board.EMPTY_TILE;
				buttons[x][y].setDraw(true);
				gamePanel.repaint();
			}

			public void mouseExited(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				for (int i = 0; i < gipfStonePoints.length; i++) {
					if (gipfStonePoints[i].getX() == x && gipfStonePoints[i].getY() == y) {
						if (!gipfIsGhost[i]) {
							boardCopy.getGrid()[x][y] = originalBoard.getGrid()[x][y];
							buttons[x][y].setDraw(false);
							gamePanel.repaint();
						}
						break;
					}
				}

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}
		};
	}

	public void execute() {
		super.execute();
		Point start = this.rows[0];
		Point end = this.rows[1];

		int xx = end.getX() - start.getX();
		int yy = end.getY() - start.getY();

		int dx, dy;
		if (xx == 0) dx = 0;
		else dx = 1;
		if (yy == 0) dy = 0;
		else dy = 1;

		int length;
		if (xx == 0) length = yy;
		else if (yy == 0) length = xx;
		else length = xx;
		length++;

		int counter = 0;
		int cntr = 0;
		Point[] tmp = new Point[length];
		Point[] gipfPoints = new Point[length];
		for (int j = 0; j < length; j++) {
			int x = start.getX() + (j * dx);
			int y = start.getY() + (j * dy);
			if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				if (this.buttons[x][y].getMouseListeners().length == 0) this.buttons[x][y].addMouseListener(this.listener);
				if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				else this.buttons[x][y].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
				gipfPoints[cntr] = new Point(x, y);
				cntr++;
			} else {
				tmp[counter] = new Point(x, y);
				counter++;
			}
		}

		this.gipfStonePoints = new Point[cntr];
		this.gipfIsGhost = new boolean[cntr];
		for (int i = 0; i < cntr; i++) {
			this.gipfStonePoints[i] = gipfPoints[i];
		}

		String send = "/removepoints ";
		for (int i = 0; i < counter; i++) {
			send += tmp[i].toString() + " ";
		}
		this.gameController.getConnector().send(send);
		this.originalBoard = gamePanel.getGame().getBoard();
		this.boardCopy = this.originalBoard.copy();
		this.gamePanel.getGame().setBoard(this.boardCopy);
	}
}
