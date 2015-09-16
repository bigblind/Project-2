package com.project.visuals;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BottomButtonPanel extends MenuComponent {

	private static final long serialVersionUID = 3471989499685962625L;

	private JButton help, sound, settings;

	public BottomButtonPanel() {
		this.setPreferredSize(new Dimension(400, 150));
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	
		Dimension buttonDimension = new Dimension(48, 48);
		
		this.help = new JButton();
		this.help.setContentAreaFilled(false);
		this.help.setFocusPainted(false);
		this.help.setBorder(BorderFactory.createEmptyBorder());
		this.help.setPreferredSize(buttonDimension);
		this.help.setIcon(new ImageIcon(ResourceLoader.HELP_ICON));
		
		this.settings = new JButton();
		this.settings.setContentAreaFilled(false);
		this.settings.setFocusPainted(false);
		this.settings.setBorder(BorderFactory.createEmptyBorder());
		this.settings.setPreferredSize(buttonDimension);
		this.settings.setIcon(new ImageIcon(ResourceLoader.SETTINGS_ICON));
		
		this.sound = new JButton();
		this.sound.setContentAreaFilled(false);
		this.sound.setFocusPainted(false);
		this.sound.setBorder(BorderFactory.createEmptyBorder());
		this.sound.setPreferredSize(buttonDimension);
		this.sound.setIcon(new ImageIcon(ResourceLoader.SOUND_ICON));
		
		this.sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(Box.createHorizontalStrut(20));
		box.add(this.sound);
		box.add(Box.createHorizontalStrut(50));
		box.add(this.settings);
		box.add(Box.createHorizontalStrut(50));
		box.add(this.help);
		box.add(Box.createHorizontalStrut(20));
		box.add(Box.createHorizontalGlue());
		this.add(box);
	}
	
	public void goHome() {
		
	}

	public void goBig() {
		
	}

	public void addActionListener(ActionListener al) {
		
	}
}
