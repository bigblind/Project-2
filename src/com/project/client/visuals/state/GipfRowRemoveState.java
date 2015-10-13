package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.board.Board;
import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardPanel;
import com.project.client.visuals.ResourceLoader;
import com.project.common.utils.Point;

public class GipfRowRemoveState extends State {

	private MouseListener listener;
	private Point[] rows;
	private int rowValueRemoved;

	public GipfRowRemoveState(final BoardPanel boardPanel, ClientInterface clientInterface, Point[] row) {
		super(boardPanel, clientInterface);

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);
				rowValueRemoved = boardPanel.getBoard().getGrid()[x][y];
				boardPanel.getBoard().getGrid()[x][y] = Board.EMPTY_TILE;
				buttons[x][y].setDraw(true);
				boardPanel.repaint();
			}

			public void mouseExited(MouseEvent e) {
				int name = Integer.parseInt(e.getComponent().getName());
				int x = name / 10;
				int y = name - (x * 10);

				if (rowValueRemoved > 0) {
					boardPanel.getBoard().getGrid()[x][y] = rowValueRemoved;
					buttons[x][y].setDraw(false);
					boardPanel.repaint();
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
		Point[] tmp = new Point[length];
		for (int j = 0; j < length; j++) {
			int x = start.getX() + (j * dx);
			int y = start.getY() + (j * dy);
			if (this.boardPanel.getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE || this.boardPanel.getBoard().getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				if (this.buttons[x][y].getMouseListeners().length == 0) this.buttons[x][y].addMouseListener(this.listener);
				if (this.boardPanel.getBoard().getGrid()[x][y] == Board.WHITE_VALUE || this.boardPanel.getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) this.buttons[x][y].setImage(ResourceLoader.WHITE_STONE_TRANSPARENT);
				else this.buttons[x][y].setImage(ResourceLoader.BLACK_STONE_TRANSPARENT);
			} else {
				counter++;
				tmp[counter] = new Point(x, y);
			}
		}

		String send = "/removepoints ";
		for (int i = 0; i < counter; i++) {
			send += tmp[i].toString() + " ";
		}
		this.clientInterface.removeRowAnswer(send);
	}
}
