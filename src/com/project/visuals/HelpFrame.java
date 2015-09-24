package com.project.visuals;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class HelpFrame extends JFrame{

	public HelpFrame(){
		createFrameHelp();
	}
	
public static void createFrameHelp() {
		
		
		JFrame frame = new JFrame("Help");

		
		JTextPane t = new JTextPane();
		HTMLEditorKit kit = new HTMLEditorKit();
		t.setEditorKit(kit);
		StyleSheet style = kit.getStyleSheet();
		style.addRule("h1 {font-size: 18px; border-bottom: 1px solid #cccccc; margin-top: 20px; margin-bottom: 10px}");
		String aim = "<h1 style=\"margin-top:5px\" >Aim:</h1> The first player to create a situation "
				+ "where his opponent either has no more pieces in his reserve "
				+ "or no more GIPF-pieces on the board, wins the game. So, keep in "
				+ "mind: make sure to always have at least one piece in reserve and "
				+ "always at least one GIPF-piece on the board."
				+ "<h1> The board: </h1> There are 24 dots at the edges of the pattern on "
				+ "the board. Dots are not part of the play area; they serve to position a "
				+ "piece before bringing it into play. The play area is made up of 37 spots (intersections)."
				+ " Only the pieces covering a spot are part of the game. The lines indicate in which directions "
				+ "pieces may be moved. "
				+ "First move: GIPF-piece: Draw lots for your color. If you draw white, you begin. Your first move "
				+ "must be a GIPF-piece. Take two pieces out of your reserve and stack them on top of each other; put "
				+ "the double piece on any of the black dots and next push it onto an adjacent spot. You may move a piece "
				+ "only one spot at a time, never two or more. Then your opponent brings a GIPF-piece into play.";
		Document doc = kit.createDefaultDocument();
		t.setDocument(doc);
		
		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);	
		t.setText(aim);
		frame.getContentPane().add(BorderLayout.PAGE_START, scrollPane);
		t.setEditable(false);
		
		//frame.setResizable(false);
		frame.pack();
		frame.setSize(500,600);
		frame.setVisible(true);
	}
	
}
