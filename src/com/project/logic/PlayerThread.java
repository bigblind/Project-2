package com.project.logic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PlayerThread extends Thread {
    
	    protected Socket clientSocket;
	    protected boolean threadSuspended = true;
	    public PlayerThread(Socket clientSocket) {
	        this.clientSocket = clientSocket;
	    }

	    public void run() {
	        InputStream inp = null;
	        BufferedInputStream buff = null;
	        DataOutputStream out = null;
	        try {
	            inp = clientSocket.getInputStream();
	            buff = new BufferedInputStream(new DataInputStream(inp));
	            out = new DataOutputStream(clientSocket.getOutputStream());
	        } catch (IOException e) {
	            return;
	        }
	       
	        
	     }
	}

