package com.project.client.visuals.sidepanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class HelpFrame extends JFrame {

	private static final long serialVersionUID = 6391476249885102767L;

	public HelpFrame() {
		createFrameHelp();
	}

	public void createFrameHelp() {

		JTextPane t = new JTextPane();
		HTMLEditorKit kit = new HTMLEditorKit();
		t.setEditorKit(kit);
		StyleSheet style = kit.getStyleSheet();
		style.addRule("h1 {font-size: 18px; border-bottom: 1px solid #cccccc; margin-top: 20px; margin-bottom: 10px}");
		String aim = "<h1 style=\"margin-top:5px\" >BASIC RULES:</h1> 1. There are 24 dots at the edges of the pattern on the board. Dots aren't part of the play area; they serve to position a piece before bringing it into play."
        + "\n 2. The play area is made up of 37 37 spots (i.e. the central part of the board). Only the pieces covering a spot are part of the game."
        + "\n 3. The lines indicate the directions in which pieces may be moved. ";
		Document doc = kit.createDefaultDocument();
		t.setDocument(doc);

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		t.setText(aim);
		this.getContentPane().add(BorderLayout.PAGE_START, scrollPane);
		t.setEditable(false);

		// frame.setResizable(false);
		
		
		
		this.setPreferredSize(new Dimension(500, 600));
		this.pack();
	}

}
