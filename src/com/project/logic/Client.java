package com.project.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String           name, address;
    private int              port;
    private Socket           clientSocket;
    private InetAddress      ip;
    private DataInputStream  input;
    private DataOutputStream output;
    private Thread           receiveThread;
    
    public Client(String hostName, String address, int port) {
	this.name = hostName;
	this.address = address;
	this.port = port;
	openConnection("127.0.0.1", 40);
	receiveThread = new Thread() {
	    public void run() {
		try {
		    input = new DataInputStream(clientSocket.getInputStream());
		    //System.out.println("client is listening");
		    if (clientSocket.isConnected()) {
			System.out.println(input.readUTF());
			
		    } else {
			System.out.println("not connected");
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
    
    private void openConnection(String address, int port) {
	
	try {
	    clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), port);
	    
	    System.out.println("Connection estabilished");
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void send() {
	try {
	    Scanner in = new Scanner(System.in);
	    System.out.println("Client: enter an int");
	    int x = in.nextInt();
	    String test = "Client sent message " + x;
	    output.writeUTF(test);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public static void main(String args[]) {
	
	Client client = new Client("leos", "localhost", 40);
	client.receiveThread.start();
	client.send();
	
    }
    
}