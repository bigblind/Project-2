package com.project.client.visuals.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.PlayerEvent;
import com.gipf.client.utils.Point;
import com.project.client.visuals.board.BoardButton;
import com.project.client.visuals.board.GamePanel;

public class MoveStateB extends State {

	private Point[] connectedLocations;
	private BoardButton pressedButton;
	private MouseListener mouseListener;
	private ActionListener actionListener;

	public MoveStateB(final GamePanel gamePanel, final GameController controller, final BoardButton pressedButton) {
		super(gamePanel, controller);

		this.pressedButton = pressedButton;
		this.calculateConnectedLocations();

		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((BoardButton) e.getSource()).equals(pressedButton)) {
					gamePanel.setState(new MoveStateA(gamePanel, controller));
					gamePanel.repaint();
				} else if (((BoardButton) e.getSource()).getIsOuterDot() == true) {
					pressedButton.setDraw(false);
					gamePanel.setState(new MoveStateB(gamePanel, controller, (BoardButton) e.getSource()));
					gamePanel.repaint();
				} else {
					PlayerEvent pe = new PlayerEvent(getButtonPoint(pressedButton), getButtonPoint((BoardButton) e.getSource()), controller.getThisPlayer());
					controller.getConnector().send(pe.toString());
					pressedButton.setDraw(false);
					gamePanel.repaint();
				}
			}
		};
		this.mouseListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (((BoardButton) e.getComponent()).equals(pressedButton)) {
					gamePanel.setState(new MoveStateA(gamePanel, controller));
					gamePanel.repaint();
				} else if (((BoardButton) e.getComponent()).getIsOuterDot() == true) {
					pressedButton.setDraw(false);
					gamePanel.setState(new MoveStateB(gamePanel, controller, (BoardButton) e.getComponent()));
					gamePanel.repaint();
				} else {
					PlayerEvent pe = new PlayerEvent(getButtonPoint(pressedButton), getButtonPoint((BoardButton) e.getComponent()), controller.getThisPlayer());
					controller.getConnector().send(pe.toString());
					pressedButton.setDraw(false);
					gamePanel.repaint();
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
				gamePanel.repaint();
			}

			public void mouseExited(MouseEvent e) {
				if (((BoardButton) e.getComponent()).getIsOuterDot() == true && !(((BoardButton) e.getComponent()).equals(pressedButton))) {
					((BoardButton) e.getComponent()).setDraw(false);
					e.getComponent().repaint();
				} else {
					calculateConnectedLocations();
				}
				gamePanel.repaint();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};
	}

	private void calculateConnectedLocations() {
		int name = Integer.parseInt(this.pressedButton.getName());
		int x = name / 10;
		int y = name - (x * 10);
		this.connectedLocations = this.gamePanel.getConnections()[x][y];
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
			this.buttons[this.connectedLocations[l].getX()][this.connectedLocations[l].getY()].addMouseListener(this.mouseListener);
			this.buttons[this.connectedLocations[l].getX()][this.connectedLocations[l].getY()].addActionListener(this.actionListener);
		}

		for (int i = 0; i < 5; i++) {
			this.buttons[i][0].setImage(this.stoneImage);
			this.buttons[i][0].addMouseListener(this.mouseListener);
			this.buttons[i][0].addActionListener(this.actionListener);
		}
		for (int i = 1; i < 5; i++) {
			this.buttons[0][i].setImage(this.stoneImage);
			this.buttons[0][i].addMouseListener(this.mouseListener);
			this.buttons[0][i].addActionListener(this.actionListener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[1 + i][5 + i].setImage(this.stoneImage);
			this.buttons[1 + i][5 + i].addMouseListener(this.mouseListener);
			this.buttons[1 + i][5 + i].addActionListener(this.actionListener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[5 + i][1 + i].setImage(this.stoneImage);
			this.buttons[5 + i][1 + i].addMouseListener(this.mouseListener);
			this.buttons[5 + i][1 + i].addActionListener(this.actionListener);
		}
		for (int i = 0; i < 4; i++) {
			this.buttons[8][5 + i].setImage(this.stoneImage);
			this.buttons[8][5 + i].addMouseListener(this.mouseListener);
			this.buttons[8][5 + i].addActionListener(this.actionListener);
		}
		for (int i = 0; i < 3; i++) {
			this.buttons[5 + i][8].setImage(this.stoneImage);
			this.buttons[5 + i][8].addMouseListener(this.mouseListener);
			this.buttons[5 + i][8].addActionListener(this.actionListener);
		}
	}

	public Point[] getConnectedLocations() {
		return this.connectedLocations;
	}

	public BoardButton getPressedButton() {
		return this.pressedButton;
	}
}
