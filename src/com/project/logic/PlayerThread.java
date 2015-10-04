package com.project.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PlayerThread extends Thread {
    
    protected Socket  clientSocket;
    protected boolean threadSuspended = true;
    private int       playerNumber;
    
    public PlayerThread(Socket clientSocket, int num) {
	this.clientSocket = clientSocket;
	this.playerNumber = num;
    }
    
    public void run() {
	boolean done = false;
	InputStream inp = null;
	BufferedInputStream buff = null;
	DataOutputStream out = null;
	try {
	    inp = clientSocket.getInputStream();
	    buff = new BufferedInputStream(new DataInputStream(inp));
	    out = new DataOutputStream(clientSocket.getOutputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	// wait for another player to arrive
	try {
	    
	    System.out.println("Player " + playerNumber + " connected");
	    if (playerNumber == 0) {
		System.out.println("Waiting for another player");
		
		try {
		    synchronized (this) {
			while (threadSuspended)
			    wait();
		    }
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		out.writeUTF("Player 2 connected, your move!");
		
	    }
	    
	    
	    while(!done){
		//play game
	    }
	    
	    
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
