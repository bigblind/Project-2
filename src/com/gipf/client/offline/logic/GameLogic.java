package com.gipf.client.offline.logic;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.PlayerEvent;
import com.gipf.client.utils.Point;

public class GameLogic {

	public LocalServer controller;

	public Game game;
	public Player currentPlayer;
	public ArrayList<Row> removeOptions = new ArrayList<Row>();
	public RowRemovalRequestEvent rowRemovalEvent;

	private boolean standard;

	public GameLogic(Game game, LocalServer controller, boolean standard) {
		this.controller = controller;
		this.game = game;
		this.standard = standard;
	}

	public void playerEventPerformed(PlayerEvent e) {
		if (!this.game.getBoard().isValidMove(e.getFromPoint(), e.getToPoint())) {
			this.controller.sendMoveValidity(false);
			return;
		}
		this.controller.sendMoveValidity(true);
		this.game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
		this.getCurrentPlayer().setStoneAccount(this.getCurrentPlayer().getStoneAccount() - 1);

		if (this.handleRows()) return;

		if (this.checkForWin()) {
			this.controller.sendGameUpdate();
			this.controller.sendWinLoseUpdate(this.returnWinner());
		} else {
			this.moveToNextPlayer();
		}
	}

	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}

	public void removeRowFromPoints(Point start, Point end) {
		ArrayList<Row> rows = this.rowRemovalEvent.getRows();
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).getFromPoint().equals(start) && rows.get(i).getToPoint().equals(end)) {
				this.game.getBoard().removeRowAndExtensions(rows.get(i));
				rows.get(i).getPlayer().setStoneAccount(rows.get(i).getPlayer().getStoneAccount() + rows.get(i).getLength());
				this.handleExtensions(rows.get(i));
				break;
			}
		}
		this.rowRemovalEvent = null;
		this.controller.sendGameUpdate();
		if (!this.handleRows()) {
			moveToNextPlayer();
		}
	}

	private boolean containsGipfStone(Player player, Point start, Point end) {
		int xx = end.getX() - start.getX();
		int yy = end.getY() - start.getY();

		int dx, dy;
		if (xx == 0) dx = 0;
		else dx = 1;
		if (yy == 0) dy = 0;
		else dy = 1;

		int length;
		if (xx == 0) length = yy;
		else if (yy == 0) length = xx;
		else length = xx;
		length++;
		for (int j = 0; j < length; j++) {
			int x = start.getX() + (j * dx);
			int y = start.getY() + (j * dy);
			if (player.getStoneColor() == Board.BLACK_VALUE) {
				if (this.game.getBoard().getGrid()[x][y] == Board.GIPF_BLACK_VALUE) return true;
			} else {
				if (this.game.getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) return true;
			}
		}
		return false;
	}

	private boolean extPlayerContainGipf(Player player, Point[] whiteExt, Point[] blackExt) {
		if (player.getStoneColor() == Board.BLACK_VALUE) {
			for (Point p : blackExt)
				if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) return true;
		} else {
			for (Point p : whiteExt)
				if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) return true;
		}
		return false;
	}

	public boolean handleRows() {
		System.out.println(this.currentPlayer);
		ArrayList<Row> rows = this.game.getBoard().checkForLines();
		if (rows.size() == 0) return false;
		else if (rows.size() == 1) {
			Row row = rows.get(0);
			if (row.getPlayer().equals(this.currentPlayer)) {
				if (this.containsGipfStone(this.currentPlayer, row.getFromPoint(), row.getToPoint()) || this.extPlayerContainGipf(this.currentPlayer, row.getWhiteExtensionStones(), row.getBlackExtensionStones())) {
					// There are gipf stones in the row or in the extensions, and there is one row.
					this.controller.sendGameUpdate();
					this.emitRowRemovalRequest(new RowRemovalRequestEvent(rows));
					return true;
				} else {
					// There are no gipf stones in the row or extensions, and there is one row.
					int stones = row.getLength();
					this.game.getBoard().removeRowAndExtensions(row);
					this.handleExtensions(row);
					row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
				}
			} else {
				if (this.containsGipfStone(this.getDisabledPlayer(), row.getFromPoint(), row.getToPoint()) || this.extPlayerContainGipf(this.getDisabledPlayer(), row.getWhiteExtensionStones(), row.getBlackExtensionStones())) {
					// There are gipf stones in the row or in the extensions, and there is one row.
					this.controller.sendGameUpdate();
					this.emitRowRemovalRequest(new RowRemovalRequestEvent(rowsForPlayer(this.getDisabledPlayer().getStoneColor(), rows)));
					return true;
				} else {
					// There are no gipf stones in the row or extensions, and there is one row.
					int stones = row.getLength();
					this.game.getBoard().removeRowAndExtensions(row);
					this.handleExtensions(row);
					row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
				}
			}

		} else {
			// there is more than 1 row.
			ArrayList<Row> activeRows = rowsForPlayer(this.currentPlayer.getStoneColor(), rows);
			this.controller.sendGameUpdate();
			if (activeRows.size() > 0) {
				this.emitRowRemovalRequest(new RowRemovalRequestEvent(activeRows));
				return true;
			} else {
				this.emitRowRemovalRequest(new RowRemovalRequestEvent(rowsForPlayer(this.getDisabledPlayer().getStoneColor(), rows)));
				return true;
			}
		}
		return false;
	}

	public void removePoints(Point[] points, Player player, boolean checkRows) {
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Point p : points) {
				if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				this.game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		} else {
			for (Point p : points) {
				if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (this.game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				this.game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		}
		if (checkRows) {
			if (!this.handleRows()) {
				moveToNextPlayer();
			}
		}
	}

	public boolean existsRowForPlayer(Player player, ArrayList<Row> rows) {
		for (Row row : rows)
			if (row.getPlayer().equals(player)) return true;
		return false;
	}

	public ArrayList<Row> rowsForPlayer(int color, ArrayList<Row> possibleRows) {
		ArrayList<Row> rowsForPlayer = new ArrayList<Row>();

		for (int x = 0; x < possibleRows.size(); x++) {
			Row tmp = possibleRows.get(x);
			if (color == tmp.getPlayer().getStoneColor()) rowsForPlayer.add(new Row(tmp.getFromPoint(), tmp.getToPoint(), tmp.getPlayer(), tmp.getLength(), tmp.getWhiteExtensionStones(), tmp.getBlackExtensionStones()));
		}
		return rowsForPlayer;
	}

	public void moveToNextPlayer() {
		if (this.currentPlayer == game.getPlayerOne()) {
			this.currentPlayer = game.getPlayerTwo();
			this.controller.changeEventPerformed(new PlayerChangeEvent(game.getPlayerOne(), game.getPlayerTwo()));
		} else {
			this.currentPlayer = game.getPlayerOne();
			this.controller.changeEventPerformed(new PlayerChangeEvent(game.getPlayerTwo(), game.getPlayerOne()));
		}
	}

	public void handleExtensions(Row row) {
		if (row.getPlayer().getStoneColor() == Board.WHITE_VALUE) row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + row.getWhiteExtensionStones().length);
		else row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + row.getBlackExtensionStones().length);
	}

	public Player checkPlayer(int stoneColor) {
		if (stoneColor == Board.BLACK_VALUE || stoneColor == Board.GIPF_BLACK_VALUE) return game.getPlayerTwo();
		return game.getPlayerOne();
	}

	public boolean checkForWin() {
		if (game.getPlayerOne().getStoneAccount() == 0 || game.getPlayerTwo().getStoneAccount() == 0) return true;

		if (standard) {
			boolean[] containGipfStones = this.game.getBoard().containGipfStones();
			if (!containGipfStones[0]) return true;
			else if (!containGipfStones[1]) return true;
		}
		return false;
	}

	public Player returnWinner() {
		if (game.getPlayerOne().getStoneAccount() == 0) return game.getPlayerTwo();
		if (game.getPlayerTwo().getStoneAccount() == 0) return game.getPlayerOne();

		if (standard) {
			boolean[] containGipfStones = this.game.getBoard().containGipfStones();

			if (!containGipfStones[0]) return this.game.getPlayerTwo();
			else if (!containGipfStones[1]) return this.game.getPlayerOne();
		}

		return null;
	}

	public void emitRowRemovalRequest(RowRemovalRequestEvent e) {
		this.rowRemovalEvent = e;
		this.controller.rowRemoveRequestEventPerformed(e);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getDisabledPlayer() {
		if (this.currentPlayer == this.game.getPlayerOne()) return this.game.getPlayerTwo();
		else return this.game.getPlayerOne();
	}

	public void setGame(Game game) {
		this.game = game;
	}
}