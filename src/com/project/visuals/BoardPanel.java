package com.project.visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.project.logic.Board;
import com.project.logic.Point;

public class BoardPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = -6218578367247380839L;

	private int distance;
	private int yOffset = 10;

	private int tileSize;

	private Point[][] coordinates;

	public BoardPanel(Board board) {
		this.coordinates = new Point[9][9];
		this.resize();
		this.setBackground(new Color(185, 0, 0));
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Color.DARK_GRAY));
		this.addComponentListener(this);
	}

	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);

		Graphics2D g = (Graphics2D) g2;

		// drawing the background
		int[] x = new int[6];
		int[] y = new int[6];
		x[0] = this.coordinates[1][1].getX() + tileSize / 2;
		y[0] = this.coordinates[1][1].getY() + tileSize / 2;
		x[1] = this.coordinates[4][1].getX() + tileSize / 2;
		y[1] = this.coordinates[4][1].getY() + tileSize / 2;
		x[2] = this.coordinates[7][4].getX() + tileSize / 2;
		y[2] = this.coordinates[7][4].getY() + tileSize / 2;
		x[3] = this.coordinates[7][7].getX() + tileSize / 2;
		y[3] = this.coordinates[7][7].getY() + tileSize / 2;
		x[4] = this.coordinates[4][7].getX() + tileSize / 2;
		y[4] = this.coordinates[4][7].getY() + tileSize / 2;
		x[5] = this.coordinates[1][4].getX() + tileSize / 2;
		y[5] = this.coordinates[1][4].getY() + tileSize / 2;
		g.setColor(Color.GRAY);
		Polygon p = new Polygon(x, y, 6);
		g.fillPolygon(p);

		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

		for (int j = 1; j < 5; j++) {
			g.drawLine(coordinates[0][j].getX() + this.tileSize / 2, coordinates[0][j].getY() + this.tileSize / 2, coordinates[4 + j][j].getX() + this.tileSize / 2, coordinates[4 + j][j].getY() + this.tileSize / 2);
		}
		for (int j = 1; j < 4; j++) {
			g.drawLine(coordinates[0 + j][j + 4].getX() + this.tileSize / 2, coordinates[0 + j][j + 4].getY() + this.tileSize / 2, coordinates[8][j + 4].getX() + this.tileSize / 2, coordinates[8][j + 4].getY() + this.tileSize / 2);
		}

		for (int i = 1; i < 5; i++) {
			g.drawLine(coordinates[i][0].getX() + this.tileSize / 2, coordinates[i][0].getY() + this.tileSize / 2, coordinates[i][4 + i].getX() + this.tileSize / 2, coordinates[i][4 + i].getY() + this.tileSize / 2);
		}

		for (int i = 0; i < 3; i++) {
			g.drawLine(coordinates[i + 5][1 + i].getX() + this.tileSize / 2, coordinates[i + 5][1 + i].getY() + this.tileSize / 2, coordinates[i + 5][8].getX() + this.tileSize / 2, coordinates[i + 5][8].getY() + this.tileSize / 2);
		}
		for (int i = 0; i < 4; i++) {
			g.drawLine(coordinates[0][3 - i].getX() + this.tileSize / 2, coordinates[0][3 - i].getY() + this.tileSize / 2, coordinates[5 + i][8].getX() + this.tileSize / 2, coordinates[5 + i][8].getY() + this.tileSize / 2);
		}

		for (int i = 1; i < 4; i++) {
			g.drawLine(coordinates[i][0].getX() + this.tileSize / 2, coordinates[i][0].getY() + this.tileSize / 2, coordinates[8][8-i].getX() + this.tileSize / 2, coordinates[8][8-i].getY() + this.tileSize / 2);
		}

//		for (int j = 0; j < 9; j++) {
//			for (int i = 0; i < 9; i++) {
//				if (coordinates[i][j] != null) g.drawImage(ResourceLoader.BLACK_STONE, coordinates[i][j].getX(), coordinates[i][j].getY(), tileSize, tileSize, null);
//			}
//		}
		
		for (int i = 0; i < 5; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[i][0].getX(), coordinates[i][0].getY(), tileSize, tileSize, null);
		}
		
		for (int i = 4; i < 9; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[i][8].getX(), coordinates[i][8].getY(), tileSize, tileSize, null);
		}
		
		for (int j = 1; j < 5; j++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[0][j].getX(), coordinates[0][j].getY(), tileSize, tileSize, null);
		}
		
		for (int j = 4; j < 9; j++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[8][j].getX(), coordinates[8][j].getY(), tileSize, tileSize, null);
		}
		
		for (int i = 0; i < 3; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[1+i][5+i].getX(), coordinates[1+i][5+i].getY(), tileSize, tileSize, null);
		}
		
		for (int i = 0; i < 3; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[5+i][1+i].getX(), coordinates[5+i][1+i].getY(), tileSize, tileSize, null);
		}
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {
		this.resize();
	}

	private void resize() {
		this.tileSize = this.getHeight() / 20;

		this.distance = (this.getHeight() - 2 * yOffset - tileSize) / 8;
		int yDifference = (int) Math.round((distance * Math.sin(Math.toRadians(30))));
		int xDifference = (int) Math.round((distance * Math.cos(Math.toRadians(30))));

		Point start = new Point(this.getWidth() / 2 - 4 * xDifference, yOffset + 4 * yDifference);
		this.coordinates[0][0] = start;

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				this.coordinates[i][j] = new Point(start.getX() + i * xDifference - tileSize / 2, start.getY() - i * yDifference + (j * 2) * yDifference);
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				this.coordinates[i][4 + j] = new Point(start.getX() + i * xDifference - tileSize / 2, start.getY() - i * yDifference + (8 + (j * 2)) * yDifference);
			}
		}
		this.repaint();
	}

	public void componentShown(ComponentEvent e) {

	}
}
