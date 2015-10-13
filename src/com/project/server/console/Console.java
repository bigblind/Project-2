package com.project.server.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.project.server.Server;

public class Console extends JFrame {

	private static final long serialVersionUID = -9190079062520041930L;

	private Server server;
	private final JTextField input;
	private JTextArea output;
	
	public Console(Server server) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.server = server;
		
		this.setPreferredSize(new Dimension(800, 400));
		this.setLayout(new BorderLayout());
		
		this.input = new JTextField();
		this.output = new JTextArea();
		
		this.input.setPreferredSize(new Dimension(50, 50));
		this.output.setPreferredSize(new Dimension(50, 300));

		this.output.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.black), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK,2), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		this.output.setEditable(false);
		this.input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!input.equals("")) handleInput(input.getText());
				input.setText("");
			}
		});
		
		DefaultCaret caret = (DefaultCaret) this.output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);      
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(output);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setAutoscrolls(true);
		this.output.setFont(new Font("Segoe MP", 0, 15));
		this.input.setFont(new Font("Segoe MP", 0, 15));
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(this.input, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
		
		this.init();
	}
	
	public void init() {
		this.output.append("Welcome to console v1.0 \n");
		this.output.append("Commands usable at this point in time: \n");
		this.output.append("/gl standard | /gl basic \n \n");
	}
	
	public void handleInput(String text) {
		this.output.append(text + "\n");
		this.output.setCaretPosition(this.output.getDocument().getLength());
	}
}
