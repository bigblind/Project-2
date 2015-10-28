package com.project.client.visuals.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.gipf.client.resource.ResourceLoader;
import com.gipf.client.utils.Point;

public class BoardPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 8555591759361318869L;

	private Point[][][] connectedLocations;
	private BoardButton[][] buttons;
	private Point[][] coordinates;
	private JButton checkButton;

	private int distance;
	private int yOffset = 10;
	private int tileSize;
	
	private boolean initialised;
	
	public BoardPanel() {
		this.connectedLocations = new Point[9][9][];
		this.buttons = new BoardButton[9][9];
		this.coordinates = new Point[9][9];
		
		this.addComponentListener(this);
		
		this.initButtons();
		this.initConnections();
		this.resize();
		this.initialised = true;
	}
	
	private void initButtons() {
		this.checkButton = new JButton();
		this.checkButton.setIcon(new ImageIcon(ResourceLoader.CHECK_ICON));
		this.checkButton.setContentAreaFilled(false);
		this.checkButton.setFocusPainted(false);
		this.checkButton.setBorder(BorderFactory.createEmptyBorder());
		this.checkButton.setPreferredSize(new Dimension(48, 48));
		this.checkButton.setVisible(false);
		
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				BoardButton button = new BoardButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(j));
				button.setContentAreaFilled(false);
				if (i == 0 || j == 0 || j == 8 || i == 8) button.setIsOuterDot(true);
				else if (i == 5 && j == 1) button.setIsOuterDot(true);
				else if (i == 6 && j == 2) button.setIsOuterDot(true);
				else if (i == 7 && j == 3) button.setIsOuterDot(true);

				this.buttons[i][j] = button;
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				BoardButton button = new BoardButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setName(Integer.toString(i) + Integer.toString(4 + j));
				button.setContentAreaFilled(false);
				if (i == 0 || j == 0 || j == 4 || i == 8) button.setIsOuterDot(true);
				else if (i == 1 && j == 1) button.setIsOuterDot(true);
				else if (i == 2 && j == 2) button.setIsOuterDot(true);
				else if (i == 3 && j == 3) button.setIsOuterDot(true);

				this.buttons[i][4 + j] = button;
			}
		}

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				this.add(this.buttons[i][j]);
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				this.add(this.buttons[i][4 + j]);
			}
		}
		
		this.add(checkButton);
	}

	private void initConnections() {
		Point[] p;

		p = new Point[1];
		p[0] = new Point(1, 1);
		this.connectedLocations[0][0] = p;
		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			for (int j = 0; j < 2; j++) {
				p[j] = new Point(1, i + j);
			}
			this.connectedLocations[0][i] = p;
		}
		p = new Point[1];
		p[0] = new Point(1, 4);
		this.connectedLocations[0][4] = p;

		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			for (int j = 0; j < 2; j++) {
				p[j] = new Point(i + j, 1);
			}
			this.connectedLocations[i][0] = p;
		}
		p = new Point[1];
		p[0] = new Point(4, 1);
		this.connectedLocations[4][0] = p;

		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			p[0] = new Point(3 + i, i);
			p[1] = new Point(4 + i, 1 + i);

			this.connectedLocations[4 + i][i] = p;
		}

		p = new Point[1];
		p[0] = new Point(7, 4);
		this.connectedLocations[8][4] = p;
		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			for (int j = 0; j < 2; j++) {
				p[j] = new Point(7, 4 + i - j);

			}
			this.connectedLocations[8][4 + i] = p;
		}

		p = new Point[1];
		p[0] = new Point(7, 7);
		this.connectedLocations[8][8] = p;
		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			for (int j = 0; j < 2; j++) {
				p[j] = new Point(4 + i - j, 7);

			}
			this.connectedLocations[4 + i][8] = p;
		}
		p = new Point[1];
		p[0] = new Point(4, 7);
		this.connectedLocations[4][8] = p;

		for (int i = 1; i < 4; i++) {
			p = new Point[2];
			p[0] = new Point(i, 3 + i);
			p[1] = new Point(1 + i, 4 + i);

			this.connectedLocations[i][4 + i] = p;
		}
	}
	
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;

		if (!initialised) return;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

		if (this.coordinates[8][8].getX() - this.coordinates[0][0].getX() > 0) {
			Point2D center = new Point2D.Float(this.getWidth() / 2, this.getHeight() / 2);
			float radius = this.getHeight() * 2 / 3;
			float[] dist = { 0.6f, 0.8f };
			Color[] colors = { new Color(25, 207, 67), new Color(23, 178, 67) };
			RadialGradientPaint gp = new RadialGradientPaint(center, radius, dist, colors);
			g.setPaint(gp);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

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
			g.drawLine(coordinates[i][0].getX() + this.tileSize / 2, coordinates[i][0].getY() + this.tileSize / 2, coordinates[8][8 - i].getX() + this.tileSize / 2, coordinates[8][8 - i].getY() + this.tileSize / 2);
		}

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
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[1 + i][5 + i].getX(), coordinates[1 + i][5 + i].getY(), tileSize, tileSize, null);
		}

		for (int i = 0; i < 3; i++) {
			g.drawImage(ResourceLoader.OUTER_DOT, coordinates[5 + i][1 + i].getX(), coordinates[5 + i][1 + i].getY(), tileSize, tileSize, null);
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

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5 + j; i++) {
				this.buttons[i][j].setBounds(this.coordinates[i][j].getX(), this.coordinates[i][j].getY(), this.tileSize, this.tileSize);
			}
		}
		for (int j = 1; j < 5; j++) {
			for (int i = j; i < 9; i++) {
				this.buttons[i][4 + j].setBounds(this.coordinates[i][4 + j].getX(), this.coordinates[i][4 + j].getY(), this.tileSize, this.tileSize);
			}
		}
		
		this.checkButton.setBounds(this.getWidth() - 96 - 50, 50, 64, 64);
		this.repaint();
	}

	public void componentShown(ComponentEvent e) {
		
	}
}