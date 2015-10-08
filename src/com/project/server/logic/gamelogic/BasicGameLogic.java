package com.project.server.logic.gamelogic;

import java.util.ArrayList;

import com.project.client.board.Board;
import com.project.common.player.PlayerEvent;
import com.project.server.logic.Game;
import com.project.server.logic.Row;

public class BasicGameLogic extends GameLogic {

	private boolean inRemoveState = false;
	private ArrayList<RowRemovalRequestListener> rrrListeners = new ArrayList<RowRemovalRequestListener>();
	
	public BasicGameLogic(Game game) {
		super(game);
	}

	public void playerEventPerformed(PlayerEvent e) {
		if (inRemoveState) {

			inRemoveState = false;

		} else {
			if (!this.game.getBoard().isValidMove(e.getFromPoint(), e.getToPoint())) return;
			this.game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
			this.getCurrentPlayer().setStoneAccount(this.getCurrentPlayer().getStoneAccount() - 1); //TODO update for client aswell somehow
			this.server.sendGameUpdate();
			
			if (this.handleRows()) return;

			this.moveToNextPlayer();
			if (this.checkForWin()) System.out.println("someone won");
		}
	}

	private boolean handleRows() {
		ArrayList<Row> rows = this.game.getBoard().checkForLines();
		if (rows.size() == 1) {
			Row row = rows.get(0);
			int stones = row.getLength(); // Need to look out for gipf stones in the row
			this.game.getBoard().removeRowAndExtensions(row);
			handleExtensions(row);
			row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
			
		} else if (rows.size() > 1) {
			ArrayList<Row> activeRows = rowsForPlayer(this.currentPlayer.getStoneColor(), rows);
			if (activeRows.size() > 0) {
				emitRowRemovalRequest(new RowRemovalRequestEvent(activeRows));
				return true;
			} else {
				ArrayList<Row> notActiveRows;
				if (currentPlayer.getStoneColor() == Board.WHITE_VALUE) notActiveRows = rowsForPlayer(Board.BLACK_VALUE, rows);
				else notActiveRows = rowsForPlayer(Board.WHITE_VALUE, rows);

				emitRowRemovalRequest(new RowRemovalRequestEvent(notActiveRows));
				return true;
			}
		}
		return false;
	}

	private ArrayList<Row> rowsForPlayer(int color, ArrayList<Row> possibleRows) {
		ArrayList<Row> rowsForPlayer = new ArrayList<Row>();

		for (int x = 0; x < possibleRows.size(); x++) {
			Row tmp = possibleRows.get(x);
			if (color == tmp.getPlayer().getStoneColor())
				rowsForPlayer.add(new Row(tmp.getFromPoint(), tmp.getToPoint(), tmp.getPlayer(), tmp.getLength(), tmp.getWhiteExtensionStones(), tmp.getBlackExtensionStones()));
		}
		return rowsForPlayer;
	}

	private void handleExtensions(Row row) {
		if (currentPlayer.getStoneColor() == Board.WHITE_VALUE) currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getWhiteExtensionStones());
		else currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getBlackExtensionStones());
	}

	public void setRemoveState(boolean state) {
		this.inRemoveState = state;
	}
	
	public void addRowRemovalRequestListener(RowRemovalRequestListener l){
		rrrListeners.add(l);
	}
	
	private void emitRowRemovalRequest(RowRemovalRequestEvent e){
		for (int i = 0; i < this.rrrListeners.size(); i++) {
			rrrListeners.get(i).rowRemoveRequestEventPerformed(e);
		}
	}
}