package com.project.server;

public abstract class Server {

	public abstract void sendClientInit();
	
	public abstract void sendGameUpdate();
	
	public abstract void sendMoveValidity(boolean valid);

	public abstract void receive(byte[] bytes);
}
