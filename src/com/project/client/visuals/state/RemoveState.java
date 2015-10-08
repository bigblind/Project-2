package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardPanel;
import com.project.common.utils.Point;

public class RemoveState extends State {

	private MouseListener listener;
	private Point[] rowPoints;

	public RemoveState(BoardPanel boardPanel, ClientInterface clientInterface, Point[] rowPoints) {
		super(boardPanel, clientInterface);
		this.rowPoints = rowPoints;

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				System.out.println("efjk");
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
		for (int i = 0; i < this.rowPoints.length / 2; i++) {
			Point start = this.rowPoints[i * 2];
			Point end = this.rowPoints[i * 2 + 1];

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
			System.out.println("LENGTH: " + length + " i " + i);
			for (int j = 0; j <= length; j++) {
				System.out.println(start.getX() + (j * dx) + " " + (start.getY() + (j * dy)));
				this.buttons[start.getX() + (j * dx)][start.getY() + (j * dy)].addMouseListener(this.listener);
			}
		}
	}
}
