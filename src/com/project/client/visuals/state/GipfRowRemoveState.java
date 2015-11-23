package com.project.client.visuals.state;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.resource.ResourceLoader;
import com.gipf.client.utils.Point;
import com.project.client.board.Board;
import com.project.client.board.Row;
import com.project.client.visuals.board.GamePanel;

public class GipfRowRemoveState extends State {

	private ActionListener actionListener;
	private MouseListener mouseListener;
	private Row row;
	private Point[] gipfStonePoints;
	private Board originalBoard;
	private Board boardCopy;
	private boolean[] gipfIsGhost;

	public GipfRowRemoveState(final GamePanel gamePanel, final GameController controller, Row row) {
		super(gamePanel, controller);
		this.row = row;

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
		
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int name = Integer.parseInt(((Component) (e.getSource())).getName());
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
		};
		this.mouseListener = new MouseListener() {
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
		this.originalBoard = gamePanel.getGame().getBoard();

		Point start = this.row.getFromPoint();
		Point end = this.row.getToPoint();

		int xx = end.getX() - start.getX();
		int yy = end.getY() - start.getY();

		int dx, dy;
		if (xx == 0) dx = 0;
		else dx = 1;
		if (yy == 0) dy = 0;
		else dy = 1;

		int length = this.row.getLength();

		int counter = 0;
		int cntr = 0;
		Point[] tmp = new Point[length];
		Point[] gipfPoints = new Point[length];
		for (int j = 0; j < length; j++) {
			int x = start.getX() + (j * dx);
			int y = start.getY() + (j * dy);
			if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				if (this.buttons[x][y].getMouseListeners().length == 0) this.buttons[x][y].addMouseListener(this.mouseListener);
				if (this.buttons[x][y].getActionListeners().length == 0) this.buttons[x][y].addActionListener(this.actionListener);
				if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				else this.buttons[x][y].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
				gipfPoints[cntr] = new Point(x, y);
				cntr++;
			} else {
				tmp[counter] = new Point(x, y);
				counter++;
			}
		}
		
		
		ArrayList<Point> extGipfStones = new ArrayList<Point>();
		ArrayList<Point> tmp2 = new ArrayList<Point>();
		if (this.gameController.getThisPlayer().getStoneColor() == Board.WHITE_VALUE) {
			for (Point p : row.getWhiteExtensionStones()) {
				if (this.originalBoard.getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
					this.gamePanel.getButtons()[p.getX()][p.getY()].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
					if (this.buttons[p.getX()][p.getY()].getMouseListeners().length == 0) this.buttons[p.getX()][p.getY()].addMouseListener(this.mouseListener);
					extGipfStones.add(p);
				} else {
					tmp2.add(p);
				}
			}
			for (Point p : row.getBlackExtensionStones()) {
				tmp2.add(p);
			}
		} else {
			for (Point p : row.getBlackExtensionStones()) {
				if (this.originalBoard.getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) {
					this.gamePanel.getButtons()[p.getX()][p.getY()].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
					if (this.buttons[p.getX()][p.getY()].getMouseListeners().length == 0) this.buttons[p.getX()][p.getY()].addMouseListener(this.mouseListener);
					extGipfStones.add(p);
				} else {
					tmp2.add(p);
				}
			}
			for (Point p : row.getWhiteExtensionStones()) {
				tmp2.add(p);
			}
		}

		this.gipfStonePoints = new Point[cntr + extGipfStones.size()];
		this.gipfIsGhost = new boolean[cntr + extGipfStones.size()];
		for (int i = 0; i < cntr; i++) {
			this.gipfStonePoints[i] = gipfPoints[i];
		}
		for (int i = 0; i < extGipfStones.size(); i++) {
			this.gipfStonePoints[cntr + i] = extGipfStones.get(i);
		}

		String send = "/removepoints ";
		for (int i = 0; i < counter; i++) {
			send += tmp[i].toString() + " ";
		}
		
		for (int i = 0; i < tmp2.size(); i++) {
			send += tmp2.get(i).toString() + " ";
		}
		this.gameController.getConnector().send(send);
		this.boardCopy = this.originalBoard.copy();
		this.gamePanel.getGame().setBoard(this.boardCopy);
	}
}
