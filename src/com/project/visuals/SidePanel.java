package com.project.visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel implements FocusListener {

	private static final long serialVersionUID = 4738543826353094709L;

	private Dimension small = new Dimension(50, 50), big = new Dimension(300, 50);
	private Color background;

	private JButton newGame;

	public SidePanel() {
		this.background = new Color(52, 207, 67);
		this.setBackground(this.background);

		this.newGame = new JButton("New Game");
		this.newGame.setFocusPainted(false);
		this.newGame.setBorder(BorderFactory.createEmptyBorder());
		this.newGame.setForeground(Color.BLACK);
		this.newGame.setFont(new Font("Segoe MP", 0, 20));
		this.newGame.setPreferredSize(new Dimension((int) big.getWidth(), 40));
		this.newGame.setHorizontalAlignment(JButton.CENTER);
		this.newGame.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.newGame.setBackground(this.background.brighter());

		this.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(background);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(20));
		box.add(newGame);
		box.add(Box.createVerticalStrut(20));
		box.add(Box.createVerticalGlue());
		panel.add(box);

		this.add(panel, BorderLayout.CENTER);

		BottomButtonPanel bottomButtonPanel = new BottomButtonPanel();
		bottomButtonPanel.setBackground(background);

		this.add(bottomButtonPanel, BorderLayout.SOUTH);

		this.goBig();
	}

	private void goBig() {
		this.setPreferredSize(big);
		this.newGame.setPreferredSize(new Dimension((int) big.getWidth(), 40));
		this.revalidate();
	}

	private void goHome() {
		this.setPreferredSize(small);
		this.newGame.setPreferredSize(new Dimension((int) small.getWidth(), 40));
		this.revalidate();
	}

	public void focusGained(FocusEvent e) {
		this.goBig();
	}

	public void focusLost(FocusEvent e) {
		this.goHome();
	}
}
