package com.project.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String           serverAddress;
    private int              port;
    private Socket           clientSocket;
    private DataInputStream  input;
    private DataOutputStream output;
    private Thread           receiveThread;
    
    public Client(String address, int port) {
	this.serverAddress = address;
	this.port = port;
	
	//open connection to the server
	openConnection(serverAddress, port);
	
	//start a new thread to continuously receive messages from the server
	receiveThread = new Thread() {
	    public void run() {
		try {
		    input = new DataInputStream(clientSocket.getInputStream());
		    while (clientSocket.isConnected()) {
			System.out.println(input.readUTF());
			
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	};
	
	try {
	    this.output = new DataOutputStream(clientSocket.getOutputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
    public static void main(String args[]) {
	
	Client client = new Client("137.120.101.196", 40);
	client.receiveThread.start();
	Scanner in = new Scanner(System.in);
	
	//Wait for user to input an integer and send message 
	while (true) {
	    int x = in.nextInt();
	    String test = "Client sent message: " + x;
	    try {
		client.output.writeUTF(test);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	
    }
    
    private void openConnection(String address, int port) {
	
	try {
	    clientSocket = new Socket(address, port);
	    
	    System.out.println("Connection established");
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
}