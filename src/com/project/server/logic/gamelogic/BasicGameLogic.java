package com.project.server.logic.gamelogic;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.project.client.board.Board;
import com.project.client.sound.SoundManager;
import com.project.common.player.PlayerEvent;
import com.project.server.logic.Game;
import com.project.server.logic.Row;

public class BasicGameLogic extends GameLogic {

	private boolean inRemoveState = false;
	private SoundManager sound = new SoundManager();

	public BasicGameLogic(Game game) {
		super(game);
		//sound.backgroundPlay();
	}

	public void playerEventPerformed(PlayerEvent e) {
		if (inRemoveState) {

			inRemoveState = false;
			

		} else {
			if (!this.game.getBoard().isValidMove(e.getFromPoint(), e.getToPoint())) {
				sound.movePlay(false);
				System.out.println("Hellooo");
				return;
			}
			sound.movePlay(true);
			this.game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
			this.getCurrentPlayer().setStoneAccount(this.getCurrentPlayer().getStoneAccount() - 1); //TODO update for client aswell somehow

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
			System.out.println(row.getPlayer() + " length " + row.getLength() + " black extension " + row.getBlackExtensionStones() + " white extension " + row.getWhiteExtensionStones());
			row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
			
		} else if (rows.size() > 1) {

			ArrayList<Row> activeRows = rowsForPlayer(this.currentPlayer.getStoneColor(), rows);
			if (activeRows.size() > 0) {
				// MAKE ACTIVEPLAYER CHOOSE
				return true;
			} else {
				ArrayList<Row> notActiveRows;
				if (currentPlayer.getStoneColor() == Board.WHITE_VALUE) notActiveRows = rowsForPlayer(Board.BLACK_VALUE, rows);
				else notActiveRows = rowsForPlayer(Board.WHITE_VALUE, rows);

				//MAKE OTHER PLAYER CHOOSE
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
}
