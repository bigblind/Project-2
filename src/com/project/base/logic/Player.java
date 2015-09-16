package com.project.base.logic;

public class Player {
  
    private int numbOfStones;
    private String name;
    
    public Player(String name){
	this.name = name;
    }
    
    public int getNumbOfStones(){
	return this.numbOfStones;
    }
}
