package com.project.visuals;

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
		String aim = "<h1 style=\"margin-top:5px\" >Game Board:</h1> The board shows a pattern of lines. Each line starts and ends with a dot, and has in between these dots a number of intersections with other lines. These intersections are called spots"
				+"<p>1. There are 24 dots at the edges of the pattern on the board. Dots aren't part of the play area, they serve to position a piece before bringing it into play"
				+"<p>2. The play area is made up of 37 spots. Only the pieces covering a spot are part of the game."
				+"<p>3. The lines indicate the directions in which the pieces may be moved."
				+ "<h1> The board: </h1> There are 24 dots at the edges of the pattern on "
				+ "the board. Dots are not part of the play area; they serve to position a "
				+ "piece before bringing it into play. The play area is made up of 37 spots (intersections)."
				+ " Only the pieces covering a spot are part of the game. The lines indicate in which directions "
				+ "pieces may be moved. "
				+ "<h1>First move: </h1>GIPF-piece: Draw lots for your color. If you draw white, you begin. Your first move "
				+ "must be a GIPF-piece. Take two pieces out of your reserve and stack them on top of each other; put "
				+ "the double piece on any of the black dots and next push it onto an adjacent spot. You may move a piece "
				+ "only one spot at a time, never two or more. Then your opponent brings a GIPF-piece into play.";
		
		Document doc = kit.createDefaultDocument();
		t.setDocument(doc);

		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		t.setText(aim);
		this.getContentPane().add(BorderLayout.PAGE_START, scrollPane);
		t.setEditable(false);

		// frame.setResizable(false);
		this.pack();
		this.setPreferredSize(new Dimension(500, 600));
		this.setVisible(true);
	}

}
