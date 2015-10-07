package com.project.client.visuals.sidepanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

public class SettingsFrame extends JFrame{

	private static final long serialVersionUID = 1254854874509485288L;
	private Dimension size = new Dimension(300,300);


	public SettingsFrame() {
	
		JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttonPanel = new JPanel(new GridLayout(0, 3));
		JPanel timerPanel = new JPanel(new GridLayout(0,1));

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
		
		JButton save = new JButton("Save");
		buttonPanel.add(save);
		JButton cancel = new JButton("Cancel");
		buttonPanel.add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		
		Border border2 = BorderFactory.createTitledBorder("Timer");
		timerPanel.setBorder(border2);
		ButtonGroup bgTimer = new ButtonGroup();
		JRadioButton on = new JRadioButton("On");
		bgTimer.add(on);
		timerPanel.add(on);
		
		JRadioButton off = new JRadioButton("Off");	
		bgTimer.add(off);
		off.setSelected(true);
		timerPanel.add(off);
		
		checkBoxPanel.setPreferredSize(new Dimension(size.width/3, size.height/3));
		buttonPanel.setPreferredSize(new Dimension(size.width/4, size.height/5));
		timerPanel.setMinimumSize(new Dimension(size.width, size.height));
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(0, 4));
		contentPane.add(checkBoxPanel, BorderLayout.NORTH);
		contentPane.add(timerPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		contentPane.setPreferredSize(new Dimension(size.width, size.height));
		
		this.setPreferredSize(new Dimension(size.width, size.height));
		this.pack();
	}



}
