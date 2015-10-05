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
    InputStream       inp             = null;
    DataOutputStream  out             = null;
    
    public PlayerThread(Socket clientSocket, int num) {
	this.clientSocket = clientSocket;
	this.playerNumber = num;
    }
    
    public void run() {
	boolean done = false;
	
	receive(); 
	
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
    }
    
    private  void receive() {
	try { 
	    DataInputStream input = new DataInputStream(clientSocket.getInputStream());
	    System.out.println("Server received client sent: " + input.readUTF());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void send() {
	try {
	    out = new DataOutputStream(clientSocket.getOutputStream());
	    String test = "Server sent message";
	    out.writeUTF(test);
	} catch (IOException e) {
	    e.printStackTrace();
	}	
    }
}
