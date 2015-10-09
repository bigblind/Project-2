package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.board.Board;
import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardButton;
import com.project.client.visuals.BoardPanel;
import com.project.client.visuals.ResourceLoader;
import com.project.common.utils.Point;

public class RemoveState extends State {

	private MouseListener listener;
	private Point[][] rowPoints;
	private Point[] rows;
	
	private int rowIndexRemoved, rowValueRemoved;

	public RemoveState(final BoardPanel boardPanel, final ClientInterface clientInterface, final Point[] rows) {
		super(boardPanel, clientInterface);
		this.rows = rows;
		this.rowPoints = new Point[this.rows.length / 2][];

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				boolean[] activeInRow = activeInRows(x, y);
				int ctr = 0;
				int index = -1;
				for (int i = 0; i < activeInRow.length; i++){
					if (activeInRow[i]) {
						index = i;
						ctr++;
					}
				}
				if (ctr == 1) {
					rowIndexRemoved = index;
					rowValueRemoved = boardPanel.getBoard().getGrid()[x][y];
					for (int i = 0; i < rowPoints[index].length; i++) {
						boardPanel.getBoard().getGrid()[rowPoints[index][i].getX()][rowPoints[index][i].getY()] = Board.EMPTY_TILE;
						((BoardButton) e.getComponent()).setDraw(true);
					}
				}
				System.out.println(rowIndexRemoved);
				boardPanel.repaint();
			}

			public void mouseExited(MouseEvent e) {
				for (int i = 0; i < rowPoints[rowIndexRemoved].length; i++) {
					boardPanel.getBoard().getGrid()[rowPoints[rowIndexRemoved][i].getX()][rowPoints[rowIndexRemoved][i].getY()] = rowValueRemoved;
					((BoardButton) e.getComponent()).setDraw(false);
				}
				boardPanel.repaint();
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}
		};
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
				this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				this.rowPoints[i][j] = new Point(x, y);
			}
		}
	}

	private boolean[] activeInRows(int x, int y) { // make this in int array and return the row indices that it's in
		boolean[] activeInRow = new boolean[rows.length];
		for (int i = 0; i < this.rowPoints.length; i++) {
			for (int j = 0; j < this.rowPoints[i].length; j++) {
				if (this.rowPoints[i][j].getX() == x && this.rowPoints[i][j].getY() == y) {
					activeInRow[i] = true;
					break;
				}
			}
		}
		return activeInRow;
	}
}
