package com.project.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private ServerSocket serverSocket;
    private Socket       clientSocket;
    static final int     PORT = 1978;
    private PlayerThread players[];
    private int          currentPlayer;
    
    public Server() {
	players = new PlayerThread[2];
	currentPlayer = 0;
	
	try {
	    serverSocket = new ServerSocket(PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	    
	}
    }
    
    public static void main(String args[]) {
	Server game = new Server();
	game.execute();
	
	System.exit(1);
    }
    
    public void execute() {
	for (int i = 0; i < players.length; i++) {
	    try {
		clientSocket = serverSocket.accept();
		System.out.println("connection estabilished woop woop");
		players[i] = new PlayerThread(clientSocket, i + 1); //start a new thread for each player
		players[i].start();
		
	    } catch (IOException e) {
		System.out.println("I/O error: " + e);
	    }
	    
	}
	
	// Player 1 is suspended until Player 2 connects.
	// Resume player 1 now.          
	synchronized (players[0]) {
	    players[0].threadSuspended = false;
	    players[0].notify();
	}
	
    }
    
    // Determine if a move is valid.
    // This method is synchronized because only one move can be
    // made at a time.
    public synchronized boolean validMove(int player) {
	
	
	while (player != currentPlayer) {
	    try {
		wait();     //wait for thread of other player to notify when to move
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	if(true){                        
	    //conditions for a valid move
	currentPlayer = (currentPlayer + 1) % 2;
	notify(); //notify other player to continue
	return true;
	}
	else 
	    return false;
	
    }
    
}
