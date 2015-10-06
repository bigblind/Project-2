package com.project.server;

import java.util.ArrayList;

import com.project.client.board.Board;
import com.project.client.connection.ClientInterface;
import com.project.common.player.Player;
import com.project.common.player.PlayerEvent;
import com.project.common.player.PlayerListener;
import com.project.server.logic.Game;
import com.project.server.logic.gamelogic.BasicGameLogic;
import com.project.server.logic.gamelogic.GameLogic;
import com.project.server.logic.gamelogic.PlayerChangeEvent;
import com.project.server.logic.gamelogic.PlayerChangeListener;

public class LocalServer implements PlayerListener, PlayerChangeListener {

	/*
	 * Supposed to be created Then add players Give the server the logic Then
	 * initialise game ( with logic type maybe )
	 * 
	 * loop:
	 * 
	 * player makes move, server checks valid move
	 * 			yes:
	 * 				move gets made, logic checks for rows. simple row ? remove, complicated, 
	 * 				send client that it needs to go to remove state, player removes
	 * 				???
	 * 				change to opponentturnstate
	 * 			no: send client invalid move and repeat
	 * 
	 * for rows, split up in white / black rows, remove / make active player remove rows, then check again if there is rows left and see if they are simple or complicated
	 * 
	 * GameLogic events - activeplayer changed - invalid move - rows need removing
	 */

	private ArrayList<PlayerListener> listeners;
	private ClientInterface[] clients;
	private GameLogic logic;
	private Game game;

	public LocalServer() {
		this.clients = new ClientInterface[2];
		this.listeners = new ArrayList<PlayerListener>();
	}

	public void addClient(ClientInterface clientInterface) {
		if (this.clients[0] == null) this.clients[0] = clientInterface;
		else this.clients[1] = clientInterface;
	}

	public void init() throws ServerNotPreparedException {
		this.game = new Game();
		this.game.getBoard().print();
		this.logic = new BasicGameLogic(this.game);
		this.logic.addPlayerChangeListener(this);

		if (this.logic == null) throw new ServerNotPreparedException();
		this.game.setGameLogic(this.logic);

		if (this.clients[0] == null || this.clients[1] == null) throw new ServerNotPreparedException();

		this.game.setPlayerOne(new Player(Board.WHITE_VALUE));
		this.game.setPlayerTwo(new Player(Board.BLACK_VALUE));

		this.game.setGameLogic(logic);

		this.sendClientInit();

		this.logic.setCurrentPlayer(this.game.getPlayerOne());
		this.clients[0].addPlayerListener(this);
		this.addPlayerListener(logic);
	}

	private void sendClientInit() {
		String send = "/i " + Board.WHITE_VALUE + " 15 15 " + this.game.getBoard().toString();
		this.clients[0].receive(send.getBytes());
		send = "/i " + Board.BLACK_VALUE + " 15 15 " + this.game.getBoard().toString();
		this.clients[1].receive(send.getBytes());
	}

	private void sendGameUpdate() {
		String send;
		send = "/u " + this.game.getPlayerOne().getStoneAccount() + " " + this.game.getPlayerTwo().getStoneAccount() + " " + this.game.getBoard().toString();
		this.clients[0].receive(send.getBytes());
		send = "/u " + this.game.getPlayerTwo().getStoneAccount() + " " + this.game.getPlayerOne().getStoneAccount() + " " + this.game.getBoard().toString();
		this.clients[1].receive(send.getBytes());
	}

	private void sendStateUpdate(PlayerChangeEvent e) {
		if (e.getFromPlayer() == game.getPlayerOne()) {
			String send;
			send = "/s opponent";
			this.clients[0].receive(send.getBytes());
			send = "/s move";
			this.clients[1].receive(send.getBytes());
		} else {
			String send;
			send = "/s move";
			this.clients[0].receive(send.getBytes());
			send = "/s opponent";
			this.clients[1].receive(send.getBytes());
		}
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
		for (PlayerListener l : this.listeners)
			l.playerEventPerformed(e);
	}

	public void changeEventPerformed(PlayerChangeEvent e) {
		if (e.getFromPlayer() == game.getPlayerOne()) {
			this.clients[0].removePlayerListener(this);
			this.clients[1].addPlayerListener(this);
		} else {
			this.clients[1].removePlayerListener(this);
			this.clients[0].addPlayerListener(this);
		}
		this.sendGameUpdate();
		this.sendStateUpdate(e);
	}
}
