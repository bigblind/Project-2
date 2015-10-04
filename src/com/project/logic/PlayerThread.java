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
	send();
	
	
	
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
    
    private void receive() {
	try {
	    inp = clientSocket.getInputStream();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void send() {
	try {
	    out = new DataOutputStream(clientSocket.getOutputStream());
	    //String test = "Server sendt message";
	    out.writeByte(5);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
}
