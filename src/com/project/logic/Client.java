package com.project.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {
    private String name, address;
    private int port;
    private Socket clientSocket;
    private InetAddress ip;
    private DataInputStream input;
    private DataOutputStream output;
    
    public Client(String hostName, String address, int port){
	this.name = hostName;
	this.address = address;
	this.port = port;
	
    }
    
    private void openConnection(String address, int port) throws UnknownHostException, IOException{
	//ip = InetAddress.getLocalHost();
	try {
	        clientSocket = new Socket(
	            InetAddress.getByName( "127.0.0.1" ), 1978);
	         input = new DataInputStream(
	                        clientSocket.getInputStream() );
	         output = new DataOutputStream(
	                        clientSocket.getOutputStream() );
	      }
	      catch ( IOException e ) {
	         e.printStackTrace();         
	      }
	 
	//ip = InetAddress.getByName(address);
	System.out.println("Connection estabilished");
	//clientSocket = new Socket(ip, port);
	
    }
    
    public static void main(String args[]){
	Client client = new Client("leos", "localhost", 1978);
	try{
	client.openConnection("localhost", 1978);
	}
	catch(IOException e){
	   e.printStackTrace();
	}
	finally{
	    System.out.println("done");
	    System.exit(1);
	}
    }
}