package com.project.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    private String           name, address;
    private int              port;
    private Socket           clientSocket;
    private InetAddress      ip;
    private DataInputStream  input;
    private DataOutputStream output;
    
    public Client(String hostName, String address, int port) {
	this.name = hostName;
	this.address = address;
	this.port = port;
	
    }
    
    private void openConnection(String address, int port) throws UnknownHostException, IOException {
	
	try {
	    clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), port);
	    
	    System.out.println("Connection estabilished");
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void send() {
	try {
	    output = new DataOutputStream(clientSocket.getOutputStream());
	    String test = "Client says hi";
	    output.writeUTF(test);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void receive() {
	try {
	    input = new DataInputStream(clientSocket.getInputStream());
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public static void main(String args[]) {
	Client client = new Client("leos", "localhost", 1650);
	try {
	    client.openConnection("localhost", 1650);
	    //client.receive();
	    client.send();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
}