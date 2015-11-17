package com.project.client.visuals.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class Frame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = 7105409066216982655L;

	private Dimension normalSize;
	private Point normalLocation;
	
	public Frame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(1500, 1500 / 16 * 9));
		this.addComponentListener(this);
		
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
					setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					setVisible(true);
				}
			}
		});
	}

	public void componentHidden(ComponentEvent e) {
		
	}

	public void componentMoved(ComponentEvent e) {
		
	}

	public void componentResized(ComponentEvent e) {
		this.setPreferredSize(this.getSize());
	}

	public void componentShown(ComponentEvent e) {
		
	}
}
