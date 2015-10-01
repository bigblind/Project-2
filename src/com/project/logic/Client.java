package com.project.logic;

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
    
    public Client(String hostName, String address, int port){
	this.name = hostName;
	this.address = address;
	this.port = port;
	
	openConnection(name, port);
    }
    
    private void openConnection(String address, int port) throws UnknownHostException, IOException{
	try{
	
	ip = InetAddress.getByName(address);
	clientSocket = new Socket( name, port);
	}
	catch(UnknownHostException e){
	    e.printStackTrace();
	    return false;
	}
	catch (SocketException e){
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    
}
