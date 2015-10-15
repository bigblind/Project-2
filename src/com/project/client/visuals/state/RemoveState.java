package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardPanel;
import com.project.common.utils.Point;

public class RemoveState extends State {

	private MouseListener listener;
	private Point[][] rowPoints;
	private Point[] rows;

	public RemoveState(BoardPanel boardPanel, ClientInterface clientInterface, Point[] rows) {
		super(boardPanel, clientInterface);
		this.rows = rows;
		this.rowPoints = new Point[this.rows.length / 2][];

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				System.out.println(e.getComponent().getName());
				
//				System.out.println(activeInNumberOfRows());
			}

			public void mouseExited(MouseEvent e) {

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
				this.buttons[start.getX() + (j * dx)][start.getY() + (j * dy)].addMouseListener(this.listener);
				this.rowPoints[i][j] = new Point(start.getX() + (j * dx), start.getY() + (j * dy));
			}
		}
	}
	
	private int activeInNumberOfRows(int x, int y) {
		int amount = 0;
		for (int i = 0; i < this.rowPoints.length; i++) {
			for (int j = 0; j < this.rowPoints[i].length; j++) {
				if (this.rowPoints[i][j].getX() == x && this.rowPoints[i][j].getY() == y) {
					amount++;
					break;
				}
			}
		}
		return amount;
	}
}
