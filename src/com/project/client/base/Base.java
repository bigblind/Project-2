package com.project.client.base;

import java.io.IOException;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.GameFrame;
import com.project.client.visuals.ResourceLoader;
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
		frameOne.setTitle("Player One");
		GameFrame frameTwo = new GameFrame(interfaceTwo);
		frameTwo.setTitle("Player Two");
		frameOne.setVisible(true);
		frameTwo.setVisible(true);
		
		LocalServer server = new LocalServer();
		server.addClient(interfaceOne);
		server.addClient(interfaceTwo);
		
		try {
			server.init();
		} catch (ServerNotPreparedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
