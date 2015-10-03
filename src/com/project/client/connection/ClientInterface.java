package com.project.client.connection;

import java.util.ArrayList;

import com.project.client.board.Board;
import com.project.common.player.Player;
import com.project.common.player.PlayerEvent;
import com.project.common.player.PlayerListener;

public class ClientInterface implements PlayerListener {

	private ArrayList<PlayerListener> listeners;

	private int opponentAccount;
	private Board board;

	private Player thisPlayer; // TODO get it's stoneColor and number

	public ClientInterface() {
		this.board = new Board();
		this.thisPlayer = new Player();
		this.listeners = new ArrayList<PlayerListener>();
		this.thisPlayer.addPlayerListener(this);
	}

	public void initCall(byte stoneColor, byte numberOfStones, byte opponentStoneAccount, String boardString) {
		this.thisPlayer.setStoneAccount(numberOfStones);
		this.thisPlayer.setStoneColor(stoneColor);
		this.opponentAccount = opponentStoneAccount;
		this.readBoard(boardString);
	}

	public void receive(byte[] bytes) {
		String received = new String(bytes);
		if (received.startsWith("/i")) {
			// "/i Board.BLACK_VALUE thisStonesAccount opponentStonesAccount boardString";
			String info = received.split("/i ")[1];
			String boardString = received.substring(3 + info.split(" ")[0].length() + 1 + info.split(" ")[1].length()
					+ 1 + info.split(" ")[2].length() + 1);
			this.initCall(Byte.parseByte(info.split(" ")[0]), Byte.parseByte(info.split(" ")[1]),
					Byte.parseByte(info.split(" ")[2]), boardString);
		} else if (received.startsWith("/u")) {
			// "/u 18 boardString";
			String[] subParts = received.split(" ");
			this.opponentAccount = Integer.parseInt(subParts[1]);

			String boardString = received.substring(subParts[0].length() + subParts[1].length() + 2);
			this.readBoard(boardString);
		}
	}
	// updates the amounts of the stones of the opposing player

	// move made => server receives, server validates move, server pushes the
	// changes, server listens to other player

	private void readBoard(String boardString) {
		String[] subParts = boardString.split(" ");

		int[][] newBoardGrid = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				newBoardGrid[i][j] = Integer.parseInt(subParts[i * 9 + j]);
			}
		}
		this.board.setGrid(newBoardGrid);
	}

	public void send() {

	}

	public Board getBoard() {
		return this.board;
	}

	public Player getThisPlayer() {
		return this.thisPlayer;
	}

	public int getOpponentAccount() {
		return this.opponentAccount;
	}

	public void playerEventPerformed(PlayerEvent e) {
		this.notifyListeners(e);
	}

	public void addPlayerListener(PlayerListener listener) {
		this.listeners.add(listener);
	}

	public void removePlayerListener(PlayerListener listener) {
		this.listeners.remove(listener);
	}

	private void notifyListeners(PlayerEvent e) {
		for (int i = 0; i < this.listeners.size(); i++)
			this.listeners.get(i).playerEventPerformed(e);
	}
}
