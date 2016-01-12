package com.project.client.visuals.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gipf.client.game.GameController;
import com.project.client.visuals.board.BoardButton;
import com.project.client.visuals.board.GamePanel;

public class MoveStateA extends State {

	private MouseListener mouseListener;
	private ActionListener actionListener;

	public MoveStateA(final GamePanel gamePanel, final GameController controller) {
		super(gamePanel, controller);

		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.setState(new MoveStateB(gamePanel, controller, (BoardButton) e.getSource()));
				gamePanel.repaint();
			}
		};

		this.mouseListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				gamePanel.setState(new MoveStateB(gamePanel, controller, (BoardButton) e.getComponent()));
				gamePanel.repaint();
			}

			public void mouseEntered(MouseEvent e) {
				((BoardButton) e.getComponent()).setDraw(true);
				e.getComponent().repaint();
			}

			public void mouseExited(MouseEvent e) {
				((BoardButton) e.getComponent()).setDraw(false);
				e.getComponent().repaint();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};
	}

	public void execute() {
		super.execute();
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
}

