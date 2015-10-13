package com.project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerThread extends Thread {
    
    protected Socket         clientSocket;
    protected boolean        threadSuspended = true;
    private int              playerNumber;
    private DataInputStream  inp;
    private DataOutputStream out;
    private Server           server;
    
    public PlayerThread(Socket clientSocket, int num, Server server) {
	this.clientSocket = clientSocket;
	this.playerNumber = num;
	this.server = server;
	try {
	    this.inp = new DataInputStream(clientSocket.getInputStream());
	    this.out = new DataOutputStream(clientSocket.getOutputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
    public void run() {
	boolean done = false;
	
	System.out.println("Player " + playerNumber + " connected");
	if (playerNumber == 1) {
	    System.out.println("Waiting for another player");
	    
	    // wait for another player to arrive
	    try {
		synchronized (this) {
		    while (threadSuspended)
			wait();
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    
	 
	    
	}
	
	
	   //play game
	while (!done) {
	    try {
		
		//receive messages from the client
		String message = receive();
		
		if (server.validMove(playerNumber - 1, message)) {
		    out.writeUTF("Server: message sent");
		} else
		    out.writeUTF("Invalid message, try again");
		
		if (server.gameOver())
		    done = true;
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	try {
	    clientSocket.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
    private String receive() throws IOException {
	
	String message = inp.readUTF();
	
	return message;
    }
    
    public void send(String message) {
	try {
	    
	    out.writeUTF(message);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}