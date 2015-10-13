package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardButton;
import com.project.client.visuals.BoardPanel;
import com.project.common.utils.Point;

public class MoveStateB extends State {

	private Point[] connectedLocations;
	private BoardButton pressedButton;
	private MouseListener listener;

	public MoveStateB(final BoardPanel boardPanel, final ClientInterface clientInterface, final BoardButton pressedButton) {
		super(boardPanel, clientInterface);

		this.pressedButton = pressedButton;
		this.calculateConnectedLocations();

		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (((BoardButton) e.getComponent()).equals(pressedButton)) {
					boardPanel.setState(new MoveStateA(boardPanel, clientInterface));
					boardPanel.repaint();
				} else if (((BoardButton) e.getComponent()).getIsOuterDot() == true) {
					pressedButton.setDraw(false);
					boardPanel.setState(new MoveStateB(boardPanel, clientInterface, (BoardButton) e.getComponent()));
					boardPanel.repaint();
				} else {
					clientInterface.getThisPlayer().locationsClicked(getButtonPoint(pressedButton), getButtonPoint(((BoardButton) e.getComponent())));
					pressedButton.setDraw(false);
					boardPanel.repaint();
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (((BoardButton) e.getComponent()).getIsOuterDot() == true && !(((BoardButton) e.getComponent()).equals(pressedButton))) {
					((BoardButton) e.getComponent()).setDraw(true);
				} else {
					Point hover = getButtonPoint((BoardButton) e.getComponent());
					for (Point p : connectedLocations) {
						if (p.equals(hover)) {
							connectedLocations = new Point[1];
							connectedLocations[0] = hover;
						}
					}
				}
				boardPanel.repaint();
			}

			public void mouseExited(MouseEvent e) {
				if (((BoardButton) e.getComponent()).getIsOuterDot() == true && !(((BoardButton) e.getComponent()).equals(pressedButton))) {
					((BoardButton) e.getComponent()).setDraw(false);
					e.getComponent().repaint();
				} else {
					calculateConnectedLocations();
				}
				boardPanel.repaint();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};
	}

	private void calculateConnectedLocations() {
		int name = Integer.parseInt(pressedButton.getName());
		int x = name / 10;
		int y = name - (x * 10);
		this.connectedLocations = boardPanel.getConnections()[x][y];
	}

	private Point getButtonPoint(BoardButton button) {
		int name = Integer.parseInt(button.getName());
		int x = name / 10;
		int y = name - (x * 10);
		return new Point(x, y);
	}

	public void execute() {
		super.execute();

		for (int l = 0; l < this.connectedLocations.length; l++) {
			this.buttons[this.connectedLocations[l].getX()][this.connectedLocations[l].getY()].setImage(this.stoneImage);
			this.buttons[this.connectedLocations[l].getX()][this.connectedLocations[l].getY()].addMouseListener(this.listener);
		}

		for (int i = 0; i < 5; i++) {
			this.buttons[i][0].setImage(this.stoneImage);
			this.buttons[i][0].addMouseListener(this.listener);
		}
		for (int i = 1; i < 5; i++) {
			this.buttons[0][i].setImage(this.stoneImage);
			this.buttons[0][i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[1 + i][5 + i].setImage(this.stoneImage);
			this.buttons[1 + i][5 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[5 + i][1 + i].setImage(this.stoneImage);
			this.buttons[5 + i][1 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[8][5 + i].setImage(this.stoneImage);
			this.buttons[8][5 + i].addMouseListener(this.listener);
		}
		for (int i = 0; i < 3; i++) {
			this.buttons[5 + i][8].setImage(this.stoneImage);
			this.buttons[5 + i][8].addMouseListener(this.listener);
		}
	}

	public Point[] getConnectedLocations() {
		return this.connectedLocations;
	}

	public BoardButton getPressedButton() {
		return this.pressedButton;
	}
}
