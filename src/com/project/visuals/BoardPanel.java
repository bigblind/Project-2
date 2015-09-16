package com.project.visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.project.logic.Board;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = -6218578367247380839L;

	private int distance = 60;
	private int yOffset = 25;

	private int tileSize = 40;

	public BoardPanel(Board board) {
		this.setBackground(new Color(185, 0, 0));
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Color.DARK_GRAY));
	}

	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);

		Graphics2D g = (Graphics2D) g2;

		this.distance = (this.getHeight() - 2 * yOffset - tileSize) / 8;
		int yDown = (int) Math.round((distance * Math.sin(Math.toRadians(30))));
		int xDown = (int) Math.round((distance * Math.cos(Math.toRadians(30))));

		// drawing the background
		int[] x = new int[6];
		x[0] = this.getWidth() / 2;
		x[1] = this.getWidth() / 2 - 3 * xDown;
		x[2] = this.getWidth() / 2 - 3 * xDown;
		x[3] = this.getWidth() / 2;
		x[4] = this.getWidth() / 2 + 3 * xDown;
		x[5] = this.getWidth() / 2 + 3 * xDown;
		int[] y = new int[6];
		y[0] = yOffset + distance + tileSize / 2;
		y[1] = yOffset + distance + 3 * yDown + tileSize / 2;
		y[2] = yOffset + 4 * distance + 3 * yDown + tileSize / 2;
		y[3] = yOffset + 7 * distance + tileSize / 2-2;
		y[4] = yOffset + 4 * distance + 3 * yDown + tileSize / 2;
		y[5] = yOffset + distance + 3 * yDown + tileSize / 2;
		g.setColor(Color.GRAY);
		Polygon p = new Polygon(x, y, 6);
		g.fillPolygon(p);

		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);

		// start drawing oblique lines of doom

		// top left right bottom ( top part )
		for (int i = 1; i < 5; i++) {
			g.drawLine(this.getWidth() / 2 - xDown * i, yOffset + i * yDown + tileSize / 2, this.getWidth() / 2 + 4 * xDown, yOffset + 4 * yDown + i * distance + tileSize / 2);
		}

		// top left right bottom ( bottom part )
		for (int i = 1; i < 4; i++) {
			g.drawLine(this.getWidth() / 2 - xDown * 4, yOffset + 4 * yDown + tileSize / 2 + i * distance, this.getWidth() / 2 + 4 * xDown - i * xDown, yOffset + 4 * yDown + 4 * distance + tileSize / 2 + i * yDown);
		}

		// top right to bottom left ( top part )
		for (int i = 1; i < 5; i++) {
			g.drawLine(this.getWidth() / 2 + xDown * i, yOffset + i * yDown + tileSize / 2, this.getWidth() / 2 - 4 * xDown, yOffset + 4 * yDown + i * distance + tileSize / 2);
		}

		// top right to bottom left ( bottom part )
		for (int i = 1; i < 4; i++) {
			g.drawLine(this.getWidth() / 2 + xDown * 4, yOffset + 4 * yDown + tileSize / 2 + i * distance, this.getWidth() / 2 - 4 * xDown + i * xDown, yOffset + 4 * yDown + 4 * distance + tileSize / 2 + i * yDown);
		}

		// drawing dots
		// top centre middle left
		for (int i = 0; i < 4; i++) {
			g.drawLine(this.getWidth() / 2 - i * xDown, yOffset + tileSize / 2 + i * yDown, this.getWidth() / 2 - i * xDown, this.getHeight() - yOffset - (tileSize / 2) - i * yDown);
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 - i * xDown - tileSize / 2, yOffset + i * yDown, tileSize, tileSize, null);
		}

		// top centre top right
		for (int i = 0; i < 4; i++) {
			g.drawLine(this.getWidth() / 2 + i * xDown, yOffset + tileSize / 2 + i * yDown, this.getWidth() / 2 + i * xDown, this.getHeight() - yOffset - (tileSize / 2) - i * yDown);
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 + i * xDown - tileSize / 2, yOffset + i * yDown, tileSize, tileSize, null);
		}

		// middle left bottom centre
		for (int i = 0; i < 4; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 - i * xDown - tileSize / 2, 2 + yOffset + 8 * distance - i * yDown, tileSize, tileSize, null);
		}

		// middle right bottom centre
		for (int i = 0; i < 4; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 + i * xDown - tileSize / 2, 2 + yOffset + 8 * distance - i * yDown, tileSize, tileSize, null);
		}

		// draws vertical down left top
		for (int i = 0; i < 5; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 - 4 * xDown - tileSize / 2, yOffset + 4 * yDown + i * distance + 2, tileSize, tileSize, null);
		}

		// draws vertical top right top
		for (int i = 0; i < 5; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, this.getWidth() / 2 + 4 * xDown - tileSize / 2, yOffset + 4 * yDown + i * distance + 2, tileSize, tileSize, null);
		}
	}
}
