package com.project.client.base;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.GameFrame;
import com.project.client.visuals.ResourceLoader;
import com.project.client.visuals.TotalFrame;
import com.project.server.LocalServer;
import com.project.server.ServerNotPreparedException;

public class Base {

	/*
	 * This should be the normal Client main method
	 */
	//	public static void main(String[] args) {
	//		
	//		ResourceLoader loader = new ResourceLoader();
	//		try {
	//			loader.init();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//		ClientInterface clientInterface = new ClientInterface();
	//
	//		GameFrame frame = new GameFrame(clientInterface);
	//		frame.setVisible(true);
	//	}

	/*
	 * A constructor for a game test with the offline server
	 */

	public static void main(String[] args) {

		ResourceLoader loader = new ResourceLoader();
		try {
			loader.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		ClientInterface interfaceOne = new ClientInterface();
		ClientInterface interfaceTwo = new ClientInterface();

		GameFrame frameOne = new GameFrame(interfaceOne);
		GameFrame frameTwo = new GameFrame(interfaceTwo);

		frameOne.setTitle("Player One");
		frameTwo.setTitle("Player Two");

		interfaceOne.setBoardPanel(frameOne.getGamePanel().getBoardPanel());
		interfaceTwo.setBoardPanel(frameTwo.getGamePanel().getBoardPanel());
		
		LocalServer server = new LocalServer();

		server.addClient(interfaceOne);
		server.addClient(interfaceTwo);
		
		interfaceOne.setServer(server);
		interfaceTwo.setServer(server);
		
		try {
			server.init();
		} catch (ServerNotPreparedException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		frameOne.setVisible(true);
		frameTwo.setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension originalSize = frameOne.getSize();
		double width = screenSize.getWidth() / 2;
		double height = originalSize.getHeight() * (width / originalSize.getWidth());
		frameOne.setSize((int) width, (int) height);
		frameTwo.setSize((int) width, (int) height);
		frameOne.setLocation(0, 0);
		frameTwo.setLocation((int) width, 0);
		
		TotalFrame totalFrame = new TotalFrame(frameOne, frameTwo);
		totalFrame.setPreferredSize(new Dimension(1500, 1500/ 16 * 9));
		totalFrame.pack();
		totalFrame.setVisible(true);
		totalFrame.setTitle("Project Gipf");
	}
}
