package com.gipf.client.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.gipf.client.game.GameController;

public class ServerConnector extends Connector {

	private BufferedReader in;
	private PrintWriter out;
	private Socket server;

	private Thread receive;

	public ServerConnector(GameController gameController, String hostName, int portNumber) throws IOException {
		super(gameController);
		this.server = new Socket(hostName, portNumber);
		this.out = new PrintWriter(server.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));

		this.receive = new Thread() {
			public void run() {
				String input;
				try {
					while (!((input = in.readLine()) == null)) {
						receive(input);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		this.receive.start();
	}

	public synchronized void send(String send) {
		this.out.println(send);
	}

	public void receive(String input) {
		if (input.equals("quit")) {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			super.receive(input);
		}
	}
}
