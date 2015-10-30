package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gipf.client.game.GameController;
import com.gipf.client.resource.ResourceLoader;
import com.gipf.client.utils.Point;
import com.project.client.board.Board;
import com.project.client.visuals.board.GamePanel;

public class RemoveState extends State {

	protected MouseListener listener;
	private Board originalBoard;
	private Board boardCopy;
	private Point[][] rowPoints;
	private Point[] rows;

	private int rowIndexRemoved = -1;

	public RemoveState(final GamePanel gamePanel, final GameController controller, final Point[] rows) {
		super(gamePanel, controller);
		this.rows = rows;
		this.rowPoints = new Point[this.rows.length / 2][];
		this.originalBoard = gamePanel.getGame().getBoard();
		this.boardCopy = this.originalBoard.copy();
		this.gamePanel.getGame().setBoard(this.boardCopy);
		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				int[] activeInRow = activeInRows(x, y);

				if (activeInRow.length == 1) {
					if (!containsGipfStone(rowPoints[activeInRow[0]][0], rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1])) {
						controller.getConnector().send("/removerow " + rowPoints[activeInRow[0]][0].toString() + " " + rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1].toString());
						for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
							buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
						}
						gamePanel.repaint();
					} else {
						for (int i = 0; i < rowPoints[activeInRow[0]].length; i++) {
							buttons[rowPoints[activeInRow[0]][i].getX()][rowPoints[activeInRow[0]][i].getY()].setDraw(false);
						}
						gamePanel.getGame().setBoard(originalBoard);
						gamePanel.setState(new GipfRowRemoveState(gamePanel, controller, new Point[] { rowPoints[activeInRow[0]][0], rowPoints[activeInRow[0]][rowPoints[activeInRow[0]].length - 1] }));
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
				}
			}

			public void mouseExited(MouseEvent e) {
				if (rowIndexRemoved >= 0) {
					for (int i = 0; i < rowPoints[rowIndexRemoved].length; i++) {
						boardCopy.getGrid()[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()] = originalBoard.getGrid()[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()];
						buttons[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()].setDraw(false);
					}
					gamePanel.getGame().setBoard(boardCopy);
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
			if (this.originalBoard.getGrid()[x][y] == Board.GIPF_WHITE_VALUE || this.originalBoard.getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				return true;
			}
		}
		return false;
	}

	public void execute() {
		super.execute();
		for (int i = 0; i < this.rows.length / 2; i++) {
			Point start = this.rows[i * 2];
			Point end = this.rows[i * 2 + 1];

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

			this.rowPoints[i] = new Point[length];
			for (int j = 0; j < length; j++) {
				int x = start.getX() + (j * dx);
				int y = start.getY() + (j * dy);
				if (this.buttons[x][y].getMouseListeners().length == 0) this.buttons[x][y].addMouseListener(this.listener);
				if (this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.WHITE_VALUE || this.gamePanel.getGame().getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				else this.buttons[x][y].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
				this.rowPoints[i][j] = new Point(x, y);
			}
		}
	}

	private int[] activeInRows(int x, int y) { // make this in int array and return the row indices that it's in
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
}