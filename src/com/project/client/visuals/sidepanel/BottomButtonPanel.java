package com.project.client.visuals.sidepanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.project.client.visuals.ResourceLoader;

public class BottomButtonPanel extends MenuComponent {

	private static final long serialVersionUID = 3471989499685962625L;

	private JButton help, sound, settings;
 
	private HelpFrame helpFrame = new HelpFrame();
	private SettingsFrame settingsFrame = new SettingsFrame();
	private boolean isSmall = false;
	
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
		this.help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpFrame frame = new HelpFrame();
				frame.setVisible(true);
			}
		});

		this.settings = new JButton();
		this.settings.setContentAreaFilled(false);
		this.settings.setFocusPainted(false);
		this.settings.setBorder(BorderFactory.createEmptyBorder());
		this.settings.setPreferredSize(buttonDimension);
		this.settings.setIcon(new ImageIcon(ResourceLoader.SETTINGS_ICON));
		this.settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFrame frame = new SettingsFrame();
				frame.setVisible(true);
			}
		});

		this.sound = new JButton();
		this.sound.setContentAreaFilled(false);
		this.sound.setFocusPainted(false);
		this.sound.setBorder(BorderFactory.createEmptyBorder());
		this.sound.setPreferredSize(buttonDimension);
		
		final ImageIcon soundOn = new ImageIcon(ResourceLoader.SOUND_ICON);
		final ImageIcon soundMuted = new ImageIcon(ResourceLoader.SOUND_MUTED_ICON);
		final ImageIcon soundOnSmall = new ImageIcon(ResourceLoader.SOUND_ICON_SMALL);
		final ImageIcon soundMutedSmall = new ImageIcon(ResourceLoader.SOUND_MUTED_ICON_SMALL);

		this.sound.setIcon(soundOn);
		this.sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sound.getIcon().equals(soundOn)){
					if(isSmall) sound.setIcon(soundOnSmall);
					else sound.setIcon(soundOn);
				}
				else{
					if(isSmall) sound.setIcon(soundMutedSmall);
					else sound.setIcon(soundMuted);
				}
				revalidate();
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
	
	public void componentResized(ComponentEvent e){
		/*isSmall = false;
		if(this.getWidth() < 200 || this.getHeight() < 90 	){
			this.help.setIcon(new ImageIcon(ResourceLoader.HELP_ICON_SMALL));
			this.settings.setIcon(new ImageIcon(ResourceLoader.HELP_ICON_SMALL));
			isSmall = true;
		}
		System.out.println("small: "+isSmall+ " ("+this.getWidth()+","+this.getHeight()+")");
		final ImageIcon soundOn = new ImageIcon(ResourceLoader.SOUND_ICON);
		final ImageIcon soundMuted = new ImageIcon(ResourceLoader.SOUND_MUTED_ICON);
		final ImageIcon soundOnSmall = new ImageIcon(ResourceLoader.SOUND_ICON_SMALL);
		final ImageIcon soundMutedSmall = new ImageIcon(ResourceLoader.SOUND_MUTED_ICON_SMALL);
		if (sound.getIcon().equals(soundOn)){
			if(isSmall) sound.setIcon(soundOnSmall);
			else sound.setIcon(soundOn);
		}
		else{
			if(isSmall) sound.setIcon(soundMutedSmall);
			else sound.setIcon(soundMuted);
		}*/
	}

	public void goHome() {

	}

	public void goBig() {

	}

	public void addActionListener(ActionListener al) {

	}
}
