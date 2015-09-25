package com.project.visuals;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 1254854874509485288L;

	public SettingsFrame() {
		JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));

		Border border = BorderFactory.createTitledBorder("GameStyle");
		checkBoxPanel.setBorder(border);
		ButtonGroup bg = new ButtonGroup();

		JRadioButton basic = new JRadioButton("Basic");
		bg.add(basic);
		basic.setSelected(true);
		checkBoxPanel.add(basic);

		JRadioButton standard = new JRadioButton("Standard");
		bg.add(standard);
		checkBoxPanel.add(standard);
		JButton save = new JButton("Save changes");
		buttonPanel.add(save);
		JButton discard = new JButton("Cancel");
		buttonPanel.add(discard);

		Container contentPane = this.getContentPane();
		contentPane.add(checkBoxPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(300, 200));
		this.pack();
	}
}
