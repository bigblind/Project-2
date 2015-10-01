package com.project.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    static final int PORT = 1978;
    
    public static void main (String[] args) 
    {
     PlayerThread players[] = new PlayerThread[2];
     ServerSocket serverSocket = null;
     Socket clientSocket = null;
     
     try {
         serverSocket = new ServerSocket(PORT);
     } catch (IOException e) {
         e.printStackTrace();

     }
     
     //new thread for each client
    for(int i = 0; i < players.length; i++) {
         try {
             players[i] = new PlayerThread(serverSocket.accept());
            players[i].start();
             
         } catch (IOException e) {
             System.out.println("I/O error: " + e);
         }
         
        
     }
    
/*
    // Player X is suspended until Player O connects.
    // Resume player X now.          
    synchronized ( players[ 0 ] ) {
       players[0].threadSuspended = false;   
       players[0].notify();
    }
    */
    }
    
    
}
