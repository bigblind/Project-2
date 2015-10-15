package com.project.client.visuals;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

import com.project.client.visuals.sidepanel.BottomButtonPanel;

public class TotalFrame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = -225611070772146092L;

	private BoardPanel active;
	private BoardPanel one, two;
	private Dimension normalSize;
	private Point normalLocation;
	private BottomButtonPanel buttons;
	private JLayeredPane layer;

	public TotalFrame(GameFrame one, GameFrame two) {
		this.addComponentListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.one = one.getGamePanel().getBoardPanel();
		this.two = two.getGamePanel().getBoardPanel();

		one.setVisible(false);
		two.setVisible(false);

		this.buttons = new BottomButtonPanel();
		buttons.setVisible(true);
		buttons.setSize(400, 150);
		buttons.setLocation(0, this.getHeight() - 300);

		Object fullscreenKey = new Object();
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), fullscreenKey);
		this.getRootPane().getActionMap().put(fullscreenKey, new AbstractAction() {
			private static final long serialVersionUID = -8738826952161833281L;

			public void actionPerformed(ActionEvent e) {
				if (getExtendedState() == JFrame.MAXIMIZED_BOTH && isUndecorated()) {
					dispose();
					setUndecorated(false);
					setPreferredSize(normalSize);
					setExtendedState(JFrame.NORMAL);
					setLocation(normalLocation);
					pack();
					setVisible(true);
				} else {
					normalSize = getSize();
					normalLocation = getLocationOnScreen();
					dispose();
					setUndecorated(true);
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					setVisible(true);
				}
			}
		});

		this.layer = this.getLayeredPane();
		layer.removeAll();

		layer.add(this.one, new Integer(0));
		layer.add(buttons, new Integer(1));
		this.active = this.one;
		this.one.setTotalFrame(this);
		this.two.setTotalFrame(this);
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {
		int width = (int) (this.getSize().getWidth() - this.getInsets().left - this.getInsets().right);
		int height = (int) (this.getSize().getHeight() - this.getInsets().top - this.getInsets().bottom);

		this.one.setSize(width, height);
		this.two.setSize(width, height);
		buttons.setSize(width / 2, height / 10);
		buttons.setLocation(0, height - height / 10);
		buttons.setOpaque(false);
		buttons.componentResized(e);
		this.revalidate();
	}

	public void showBoard(BoardPanel panel) {
		if (panel.equals(one) && !this.active.equals(one)) {
			this.layer.remove(two);
			this.layer.add(one, new Integer(0));
			this.active = one;
		} else if (panel.equals(two) && !this.active.equals(two)) {
			this.layer.remove(one);
			this.layer.add(two, new Integer(0));
			this.active = two;
		}
	}

	public void componentShown(ComponentEvent e) {

	}
}
