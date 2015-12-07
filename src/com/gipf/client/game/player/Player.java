package com.gipf.client.game.player;

public class Player {

	private int stoneAccount;
	private int stoneColor;

	public Player() {

	}

	public Player(int stoneColor) {
		this.stoneAccount = 15;
		this.stoneColor = stoneColor;
	}

	public Player(int stones, int stoneColor) {
		this.stoneAccount = stones;
		this.stoneColor = stoneColor;
	}
	
	public void update(String state) {
		
	}

	public int getStoneAccount() {
		return this.stoneAccount;
	}

	public void setStoneAccount(int stones) {
		this.stoneAccount = stones;
	}

	public void setStoneColor(int color) {
		this.stoneColor = color;
	}

	public int getStoneColor() {
		return this.stoneColor;
	}

	public Player copy() {
		return new Player(this.stoneAccount, this.stoneColor);
	}

	public String toString() {
		return "[Player: stone color: " + this.stoneColor + " stone account: " + this.stoneAccount + "]";
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
