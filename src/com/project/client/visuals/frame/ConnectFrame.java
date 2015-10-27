package com.project.client.visuals.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gipf.client.connector.Connector;
import com.gipf.client.connector.ServerConnector;
import com.project.client.base.Controller;

public class ConnectFrame extends JFrame {

	private static final long serialVersionUID = -5204346655421502023L;

	private final JTextField textIpAdress, textPort;
	private JLabel lblPort, lblIpAdress;
	private JPanel contentPane;
	private JButton btnConnect;

	public ConnectFrame(final Controller controller) {
		final ConnectFrame frame = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 320, 384);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.contentPane.setLayout(null);

		this.lblIpAdress = new JLabel("IP adress:");
		this.lblIpAdress.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblIpAdress.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		this.lblIpAdress.setBounds(113, 47, 90, 20);
		this.contentPane.add(lblIpAdress);

		this.textIpAdress = new JTextField();
		this.textIpAdress.setHorizontalAlignment(SwingConstants.CENTER);
		this.textIpAdress.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		this.textIpAdress.setBounds(98, 80, 120, 25);
		this.textIpAdress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.contentPane.add(textIpAdress);
		this.textIpAdress.setColumns(10);

		this.lblPort = new JLabel("Port:");
		this.lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblPort.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		this.lblPort.setBounds(113, 156, 90, 20);
		this.contentPane.add(lblPort);

		this.textPort = new JTextField();
		this.textPort.setHorizontalAlignment(SwingConstants.CENTER);
		this.textPort.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		this.textPort.setColumns(10);
		this.textPort.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.textPort.setBounds(98, 189, 120, 25);
		this.contentPane.add(textPort);

		this.btnConnect = new JButton("Connect");
		this.btnConnect.setBounds(106, 285, 97, 25);
		this.btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String ip = textIpAdress.getText();
					int port = Integer.parseInt(textPort.getText());

					try {
						Connector connector = new ServerConnector(controller.getGameController(), ip, port);
						controller.setConnector(connector);
						frame.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch(java.lang.NumberFormatException ex) {
					System.err.println("Invalid input");
				}
			}
		});
		this.contentPane.add(btnConnect);
	}
}
