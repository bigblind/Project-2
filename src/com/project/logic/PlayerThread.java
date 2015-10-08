package com.project.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

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
	    
	    //play game
	    while (!done) {
		try {
		    String message = receive();
		    
		    if (server.validMove(playerNumber, message)) {
			out.writeUTF("Message sent");
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
	
    }
    
    private String receive() throws IOException {
	
	String message = inp.readUTF();
	
	return message;
    }
    
    /*private void receive() throws IOException{
    
    
        inp = new DataInputStream(clientSocket.getInputStream());
        
        String message = inp.readUTF();
        System.out.println(message);
    
    }*/
    
    public void send(String message) {
	try {
	    //String test = "Server sent message";
	    out.writeUTF(message);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    /*public void send() {
    try {
        out = new DataOutputStream(clientSocket.getOutputStream());

        String test = "Server sent message";
        out.writeUTF(test);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
      }*/
}
