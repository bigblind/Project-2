package com.project.client.visuals.state;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gipf.client.game.GameController;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Row;
import com.gipf.client.resource.ResourceLoader;
import com.gipf.client.utils.Point;
import com.project.client.visuals.board.GamePanel;

public class RemoveState extends State {

	private MouseListener mouseListener;
	private ActionListener actionListener;
	private Board originalBoard;
	private Board boardCopy;

	private Point[][] rowPoints;

	private Row[] rows;

	private int rowIndexRemoved = -1;

	public RemoveState(final GamePanel gamePanel, final GameController controller, final Row[] rows) {
		super(gamePanel, controller);
		this.rows = rows;
		this.rowPoints = new Point[this.rows.length][];
		this.originalBoard = gamePanel.getGame().getBoard();
		this.boardCopy = this.originalBoard.copy();
		this.gamePanel.getGame().setBoard(this.boardCopy);
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int name = Integer.parseInt(((Component) (e.getSource())).getName());
				int x = name / 10;
				int y = name - (x * 10);

				int[] activeInRow = activeInRows(x, y);

				if (activeInRow.length == 1) {
					if (!containsGipfStone(rowPoints[activeInRow[0]][0], rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1])) {
						if (!extCurrentPlayerContainGipf(rows[activeInRow[0]].getWhiteExtensionStones(), rows[activeInRow[0]].getBlackExtensionStones())) {
							controller.getConnector().send("/removerow " + rowPoints[activeInRow[0]][0].toString() + " " + rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1].toString() + " " + controller.getThisPlayer());
							for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
								buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
							}
							gamePanel.repaint();
						} else {
							for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
								buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
							}
							gamePanel.getGame().setBoard(originalBoard);
							
							gamePanel.setState(new GipfRowRemoveState(gamePanel, controller, rows[activeInRow[0]]));
						}
						
					} else {
						for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
							buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
						}
						gamePanel.getGame().setBoard(originalBoard);
						
						gamePanel.setState(new GipfRowRemoveState(gamePanel, controller, rows[activeInRow[0]]));
					}
				}
			}
		};
		this.mouseListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				int[] activeInRow = activeInRows(x, y);

				if (activeInRow.length == 1) {
					if (!containsGipfStone(rowPoints[activeInRow[0]][0], rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1])) {
						if (!extCurrentPlayerContainGipf(rows[activeInRow[0]].getWhiteExtensionStones(), rows[activeInRow[0]].getBlackExtensionStones())) {
							controller.getConnector().send("/removerow " + rowPoints[activeInRow[0]][0].toString() + " " + rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1].toString() + " " + controller.getThisPlayer());
							for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
								buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
							}
							gamePanel.repaint();
						} else {
							for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
								buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
							}
							gamePanel.getGame().setBoard(originalBoard);
							
							gamePanel.setState(new GipfRowRemoveState(gamePanel, controller, rows[activeInRow[0]]));
						}
						
					} else {
						for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
							buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
						}
						gamePanel.getGame().setBoard(originalBoard);
						
						gamePanel.setState(new GipfRowRemoveState(gamePanel, controller, rows[activeInRow[0]]));
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				int[] activeInRow = activeInRows(x, y);
				if (activeInRow.length == 1) {
					rowIndexRemoved = activeInRow[0];
					for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
						boardCopy.getGrid()[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()] = Board.EMPTY_TILE;
						buttons[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()].setDraw(true);
					}
					gamePanel.getGame().setBoard(boardCopy);
					gamePanel.repaint();
					gamePanel.revalidate();
				}
			}

			public void mouseExited(MouseEvent e) {
				if (rowIndexRemoved >= 0) {
					for (int i = 0; i < rowPoints[rowIndexRemoved].length; i++) {
						boardCopy.getGrid()[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()] = originalBoard.getGrid()[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()];
						buttons[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()].setDraw(false);
					}
					gamePanel.getGame().setBoard(boardCopy);
					gamePanel.repaint();
					gamePanel.revalidate();
				}
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}
		};
	}

	private boolean containsGipfStone(Point start, Point end) {
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
		for (int j = 0; j < length; j++) {
			int x = start.getX() + (j * dx);
			int y = start.getY() + (j * dy);
			if (this.gameController.getThisPlayer().getStoneColor() == Board.BLACK_VALUE) {
				if (this.originalBoard.getGrid()[x][y] == Board.GIPF_BLACK_VALUE) return true;
			} else {
				if (this.originalBoard.getGrid()[x][y] == Board.GIPF_WHITE_VALUE) return true;
			}
		}
		return false;
	}
	
	private boolean extCurrentPlayerContainGipf(Point[] whiteExt, Point[] blackExt) {
		if (this.gameController.getThisPlayer().getStoneColor() == Board.BLACK_VALUE) {
			for (Point p : blackExt) if (this.originalBoard.getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) return true;
		} else {
			for (Point p : whiteExt) if (this.originalBoard.getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) return true;
		}
		return false;
	}

	public void execute() {
		super.execute();
		for (int i = 0; i < this.rows.length; i++) {
			Point start = this.rows[i].getFromPoint();
			Point end = this.rows[i].getToPoint();

			int xx = end.getX() - start.getX();
			int yy = end.getY() - start.getY();

			int dx, dy;
			if (xx == 0) dx = 0;
			else dx = 1;
			if (yy == 0) dy = 0;
			else dy = 1;

			int length = this.rows[i].getLength();

			this.rowPoints[i] = new Point[length];
			for (int j = 0; j < length; j++) {
				int x = start.getX() + (j * dx);
				int y = start.getY() + (j * dy);
				if (this.buttons[x][y].getMouseListeners().length == 0) this.buttons[x][y].addMouseListener(this.mouseListener);
				if (this.buttons[x][y].getActionListeners().length == 0) this.buttons[x][y].addActionListener(this.actionListener);
				if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				else this.buttons[x][y].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
				this.rowPoints[i][j] = new Point(x, y);
			}
		}
	}

	public int[] activeInRows(int x, int y) { // return the row indices that it's in
		boolean[] activeInRow = new boolean[rows.length];
		int[] indices;
		int[] tmp = new int[this.rowPoints.length];
		int counter = 0;
		for (int i = 0; i < this.rowPoints.length; i++) {
			for (int j = 0; j < this.rowPoints[i].length; j++) {
				if (this.rowPoints[i][j].getX() == x && this.rowPoints[i][j].getY() == y) {
					activeInRow[i] = true;
					tmp[counter] = i;
					counter++;
					break;
				}
			}
		}
		indices = new int[counter];
		for (int i = 0; i < counter; i++) {
			indices[i] = tmp[i];
		}
		return indices;
	}
	
	public Row[] getRows() {
		return this.rows;
	}
}
