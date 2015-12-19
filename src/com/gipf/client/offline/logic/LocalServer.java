package com.gipf.client.offline.logic;

import com.gipf.client.connector.Connector;
import com.gipf.client.connector.LocalConnector;
import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.PlayerEvent;
import com.gipf.client.utils.Point;

/**
 * @author simon This class is meant to serve as the replacement of the online
 *         server. It is connected with 2 local connectors to fake the
 *         connection. Implementation of LocalServer and online server are
 *         identical except for the way of sending and retrieving. The use of
 *         both classes is identical.
 */
public class LocalServer {

	private Connector c1, c2;

	private GameLogic logic;
	private Game game;

	public LocalServer(LocalConnector c1, LocalConnector c2, String logic) {
		this.c1 = c1;
		this.c2 = c2;
		this.game = new Game();
		this.game.setPlayerOne(new Player(Board.WHITE_VALUE));
		this.game.setPlayerTwo(new Player(Board.BLACK_VALUE));
		if (logic.equals("basic")) {
			this.game.getBoard().basicInit();
			this.logic = new GameLogic(this.game, this, false);
		} else {
			this.game.getBoard().standardInit();
			this.logic = new GameLogic(this.game, this, true);
		}
		this.logic.setCurrentPlayer(this.game.getPlayerOne());
		this.game.setGameLogic(this.logic);
	}

	public void setConnectors(Connector c1, Connector c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public void start() {
		this.sendClientInit();
	}

	public synchronized void sendToAll(String send) {
		c1.receive(send);
		c2.receive(send);
	}

	public synchronized void sendToClient(String send, int index) {
		if (index == 0) c1.receive(send);
		else c2.receive(send);
	}

	public void clientInput(String received, int id) {
		if (received.contains("PlayerEvent")) {
			int x1 = Integer.parseInt("" + received.split("x = ")[1].charAt(0));
			int y1 = Integer.parseInt("" + received.split("y = ")[1].charAt(0));
			int x2 = Integer.parseInt("" + received.split("x = ")[2].charAt(0));
			int y2 = Integer.parseInt("" + received.split("y = ")[2].charAt(0));

			if (id == 1) {
				PlayerEvent pe = new PlayerEvent(new Point(x1, y1), new Point(x2, y2), game.getPlayerOne());
				this.logic.playerEventPerformed(pe);
			} else {
				PlayerEvent pe = new PlayerEvent(new Point(x1, y1), new Point(x2, y2), game.getPlayerTwo());
				this.logic.playerEventPerformed(pe);
			}
		} else if (received.startsWith("/removerow")) { //THIS ONE 
			String[] subPartsX = received.split("Point: x = ");
			String[] subPartsY = received.split("y = ");

			int x1 = Integer.parseInt(subPartsX[1].substring(0, 1));
			int y1 = Integer.parseInt(subPartsY[1].substring(0, 1));

			int x2 = Integer.parseInt(subPartsX[2].substring(0, 1));
			int y2 = Integer.parseInt(subPartsY[2].substring(0, 1));

			Point start = new Point(x1, y1);
			Point end = new Point(x2, y2);
			this.logic.removeRowFromPoints(start, end);
		} else if (received.startsWith("/removepoints")) {
			String[] subPartsX = received.split("Point: x = ");
			String[] subPartsY = received.split("y = ");

			Point[] points = new Point[subPartsX.length - 1];

			for (int i = 0; i < subPartsX.length - 1; i++) {
				int x = Integer.parseInt(subPartsX[1 + i].substring(0, 1));
				int y = Integer.parseInt(subPartsY[1 + i].substring(0, 1));

				points[i] = new Point(x, y);
			}

			Player tmp;
			String player = received.split("stone color: ")[1].substring(0, 1);
			if (player.equals("1")) tmp = this.game.getPlayerOne();
			else tmp = this.game.getPlayerTwo();
			
			if (received.endsWith("checkrows")) this.logic.removePoints(points, tmp, true);
			else this.logic.removePoints(points, tmp, false);
			this.sendGameUpdate();
		}
	}

	public void sendClientInit() {
		String send = "/i " + Board.WHITE_VALUE + " 15 15 " + this.game.getBoard().toString();
		this.sendToClient(send, 0);
		send = "/i " + Board.BLACK_VALUE + " 15 15 " + this.game.getBoard().toString();
		this.sendToClient(send, 1);
	}

	public void sendGameUpdate() {
		String send;
		send = "/u " + this.game.getPlayerOne().getStoneAccount() + " " + this.game.getPlayerTwo().getStoneAccount() + " " + this.game.getBoard().toString();
		this.sendToClient(send, 0);
		send = "/u " + this.game.getPlayerTwo().getStoneAccount() + " " + this.game.getPlayerOne().getStoneAccount() + " " + this.game.getBoard().toString();
		this.sendToClient(send, 1);
	}

	public void rowRemoveRequestEventPerformed(RowRemovalRequestEvent e) {
		String send = "/s remove";
		for (int i = 0; i < e.getRows().size(); i++) {
			send += " " + e.getRows().get(i).toString() + "endRow ";
		}
		if (e.getRows().get(0).getPlayer().getStoneColor() == Board.WHITE_VALUE) {
			this.sendToClient(send, 0);
			this.sendToClient("/s wait", 1);
		} else {
			this.sendToClient(send, 1);
			this.sendToClient("/s wait", 0);
		}
	}

	public void changeEventPerformed(PlayerChangeEvent e) {
		this.sendGameUpdate();
		if (e.getFromPlayer() == game.getPlayerOne()) {
			this.sendToClient("/s wait", 0);
			this.sendToClient("/s move", 1);
		} else {
			this.sendToClient("/s wait", 1);
			this.sendToClient("/s move", 0);
		}
	}

	public void sendMoveValidity(boolean valid) {
		if (valid) {
			if (this.game.getGameLogic().getCurrentPlayer().getStoneColor() == Board.WHITE_VALUE) this.sendToClient("/m valid", 0);
			else this.sendToClient("/m valid", 1);
		} else {
			if (this.game.getGameLogic().getCurrentPlayer().getStoneColor() == Board.WHITE_VALUE) this.sendToClient("/m invalid", 0);
			else this.sendToClient("/m invalid", 1);
		}
	}

	public void sendWinLoseUpdate(Player player) {
		this.game.finish();
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			this.sendToClient("/g win", 0);
			this.sendToClient("/g lose", 1);
		} else {
			this.sendToClient("/g win", 1);
			this.sendToClient("/g lose", 0);
		}
	}

	public void receive(String string, LocalConnector connector) {
		if (c1.equals(connector)) this.clientInput(string, 1);
		else this.clientInput(string, 2);
	}

	public GameLogic getGameLogic() {
		return this.logic;
	}
}
