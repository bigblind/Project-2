package com.gipf.client.game.player;

public class Player {

	private String name;
	private int stoneAccount;
	private int stoneColor;

	public Player() {

	}

	public Player(int stoneColor) {
		this.name = "Player " + String.valueOf(stoneColor);
		this.stoneAccount = 15;
		this.stoneColor = stoneColor;
	}

	public Player(String name, int stones, int stoneColor) {
		this.name = name;
		this.stoneAccount = stones;
		this.stoneColor = stoneColor;
	}
	
	public void update(String state) {
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoneAccount() {
		return this.stoneAccount;
	}

	public void setStoneAccount(int stones) {
		this.stoneAccount = stones;
	}

	public void setStoneColor(int color) {
		this.name = "Player " + String.valueOf(color);
		this.stoneColor = color;
	}

	public int getStoneColor() {
		return this.stoneColor;
	}

	public Player copy() {
		return new Player(this.name, this.stoneAccount, this.stoneColor);
	}

	public String toString() {
		return this.name;
	}

	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player p = (Player) o;
			if (p.getStoneColor() == this.getStoneColor()) return true;
			else return false;
		} else {
			return false;
		}
	}
}
