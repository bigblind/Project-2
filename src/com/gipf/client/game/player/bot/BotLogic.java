package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.offline.logic.Row;
import com.gipf.client.utils.Point;

public class BotLogic {

	private boolean standard;

	public BotLogic(boolean standard) {
		this.standard = standard;
	}

	public void performLogic(Player player, Node node) { // TODO for all children of this node
		player.setStoneAccount(player.getStoneAccount() - 1);
		if (handleRows(player, node)) return;
	}

	public boolean handleRows(Player player, Node node) {
		ArrayList<Row> rows = node.getGame().getBoard().checkForLines();
		if (rows.size() == 1 && !containsGipfStone(player, node.getGame(), rows.get(0).getFromPoint(), rows.get(0).getToPoint())) {
			if (extCurrentPlayerContainGipf(player, node.getGame(), rows.get(0).getWhiteExtensionStones(), rows.get(0).getBlackExtensionStones())) {
				ArrayList<Row> activeRows = rowsForPlayer(player.getStoneColor(), rows);
				if (activeRows.size() > 0) {

					Row row = activeRows.get(0);
					Point[] gipfExt = this.gipfStonesOfRowExtension(node.getGame(), this.extensionStonesOfPlayer(player, row));
					if (gipfExt.length > 0) {
						ArrayList<Point> toRemove = new ArrayList<Point>();
						for (Point p : this.pointsInRow(row))
							toRemove.add(p);
						if (player.getStoneColor() == Board.WHITE_VALUE) {
							for (Point p : row.getBlackExtensionStones()) {
								toRemove.add(p);
							}
							for (Point p : row.getWhiteExtensionStones()) {
								boolean isGipf = false;
								for (Point extP : gipfExt) {
									if (p.equals(extP)) isGipf = true;
								}
								if (!isGipf) toRemove.add(p);
							}
						} else {
							for (Point p : row.getWhiteExtensionStones()) {
								toRemove.add(p);
							}
							for (Point p : row.getBlackExtensionStones()) {
								boolean isGipf = false;
								for (Point extP : gipfExt) {
									if (p.equals(extP)) isGipf = true;
								}
								if (!isGipf) toRemove.add(p);
							}
						}

						int[] counterArray = new int[gipfExt.length];
						boolean[] values = new boolean[gipfExt.length];

						for (int i = 0; i < Math.pow(2, gipfExt.length); i++) {
							for (int j = 0; j < counterArray.length; j++) {
								if (counterArray[j] == Math.pow(2, j)) {
									values[j] = !values[j];
									counterArray[j] = 0;
								}
							}
							int counter = 0;
							if (values[i]) {
								toRemove.add(gipfExt[i]);
								counter++;
							}
							Point[] actionPoints = new Point[counter];
							int cnt = 0;
							for (int k = 0; k < values.length; k++) {
								if (values[k]) {
									actionPoints[cnt] = gipfExt[k];
									cnt++;
								}
							}
							this.removePoints(player, node, (Point[])(toRemove.toArray()), new Action(actionPoints),  false);
						}
					} else {
						Game tmpGame = node.getGame().copy();

						int stones = row.getLength();
						tmpGame.getBoard().removeRowAndExtensions(row);
						handleExtensions(row);
						row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
						node.addChild(new Node(node, tmpGame, new Action(row.getFromPoint()), true));
					}

				} else {
					// TODO
					//					emitRowRemovalRequest(new RowRemovalRequestEvent(rowsForPlayer(getDisabledPlayer(player, gameState).getStoneColor(), rows)));
					//					return true;
				}
			} else {
				Row row = rows.get(0);
				int stones = row.getLength();
				node.getGame().getBoard().removeRowAndExtensions(row);
				handleExtensions(row);
				row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
			}
		} else if (rows.size() > 0) {
			ArrayList<Row> activeRows = rowsForPlayer(player.getStoneColor(), rows);
			if (activeRows.size() == 1) {
				Row row = activeRows.get(0);
				int stones = row.getLength();
				node.getGame().getBoard().removeRowAndExtensions(row);
				handleExtensions(row);
				row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + stones);
			} else {
				if (activeRows.size() > 0) {
					//					emitRowRemovalRequest(new RowRemovalRequestEvent(activeRows));
					//					return true;
				} else {
					//					emitRowRemovalRequest(new RowRemovalRequestEvent(rowsForPlayer(getDisabledPlayer(player, gameState).getStoneColor(), rows)));
					//					return true;
				}
			}
		}
		return false;
	}

	public boolean checkForWin(Game game) {
		if (game.getPlayerOne().getStoneAccount() == 0 || game.getPlayerTwo().getStoneAccount() == 0) return true;

		if (this.standard) {
			boolean[] containGipfStones = game.getBoard().containGipfStones();
			if (!containGipfStones[0]) return true;
			else if (!containGipfStones[1]) return true;
		}
		return false;
	}

	public void removePoints(Player player, Node node, Point[] points, Action action, boolean checkRows) {
		Game game = node.getGame().copy();
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		} else {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		}
		if (checkRows) {
			if (handleRows(player, node)) {
				node.addChild(new Node(node, game, action, false));
			} else {
				node.addChild(new Node(node, game, action, true));
			}
		} else {
			node.addChild(new Node(node, game, action, true));
		}
	}

	public Point[] pointsInRow(Row row) {
		Point[] points = new Point[row.getLength()];

		int xx = row.getToPoint().getX() - row.getFromPoint().getX();
		int yy = row.getToPoint().getY() - row.getFromPoint().getY();

		int dx, dy;
		if (xx == 0) dx = 0;
		else dx = 1;
		if (yy == 0) dy = 0;
		else dy = 1;

		for (int j = 0; j < row.getLength(); j++) {
			int x = row.getFromPoint().getX() + (j * dx);
			int y = row.getFromPoint().getY() + (j * dy);
			points[j] = new Point(x, y);
		}
		return points;
	}

	public Player returnWinner(Game game) {
		if (game.getPlayerOne().getStoneAccount() == 0) return game.getPlayerTwo();
		if (game.getPlayerTwo().getStoneAccount() == 0) return game.getPlayerOne();

		if (this.standard) {
			boolean[] containGipfStones = game.getBoard().containGipfStones();

			if (!containGipfStones[0]) return game.getPlayerTwo();
			else if (!containGipfStones[1]) return game.getPlayerOne();
		}

		return null;
	}

	public void handleExtensions(Row row) {
		if (row.getPlayer().getStoneColor() == Board.WHITE_VALUE) row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + row.getWhiteExtensionStones().length);
		else row.getPlayer().setStoneAccount(row.getPlayer().getStoneAccount() + row.getBlackExtensionStones().length);
	}

	private boolean containsGipfStone(Player player, Game game, Point start, Point end) {
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
				if (game.getBoard().getGrid()[x][y] == Board.GIPF_BLACK_VALUE) return true;
			} else {
				if (game.getBoard().getGrid()[x][y] == Board.GIPF_WHITE_VALUE) return true;
			}
		}
		return false;
	}

	private Point[] extensionStonesOfPlayer(Player player, Row row) {
		if (player.getStoneColor() == Board.WHITE_VALUE) return row.getWhiteExtensionStones();
		else return row.getBlackExtensionStones();
	}

	private boolean extCurrentPlayerContainGipf(Player player, Game game, Point[] whiteExt, Point[] blackExt) {
		if (player.getStoneColor() == Board.BLACK_VALUE) {
			for (Point p : blackExt)
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) return true;
		} else {
			for (Point p : whiteExt)
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) return true;
		}
		return false;
	}

	private Point[] gipfStonesOfRowExtension(Game game, Point[] extensions) {
		int counter = 0;
		Point[] tmp = new Point[extensions.length];

		for (int i = 0; i < extensions.length; i++) {
			if (game.getBoard().getGrid()[extensions[i].getX()][extensions[i].getY()] == Board.GIPF_BLACK_VALUE || game.getBoard().getGrid()[extensions[i].getX()][extensions[i].getY()] == Board.GIPF_WHITE_VALUE) {
				tmp[counter] = extensions[i];
				counter++;
			}
		}

		Point[] result = new Point[counter];
		for (int i = 0; i < counter; i++) {
			result[i] = tmp[i];
		}

		return result;
	}

	public void removePoints(Player player, Game game, Point[] points) {
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		} else {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) {
					player.setStoneAccount(player.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		}

	}

	public ArrayList<Row> rowsForPlayer(int color, ArrayList<Row> possibleRows) {
		ArrayList<Row> rowsForPlayer = new ArrayList<Row>();

		for (int x = 0; x < possibleRows.size(); x++) {
			Row tmp = possibleRows.get(x);
			if (color == tmp.getPlayer().getStoneColor()) rowsForPlayer.add(new Row(tmp.getFromPoint(), tmp.getToPoint(), tmp.getPlayer(), tmp.getLength(), tmp.getWhiteExtensionStones(), tmp.getBlackExtensionStones()));
		}
		return rowsForPlayer;
	}

	public Point[][] generateClickablePoints(Row[] rows) {
		Point[][] rowPoints = new Point[rows.length][];

		for (int i = 0; i < rows.length; i++) {
			Point start = rows[i].getFromPoint();
			Point end = rows[i].getToPoint();

			int xx = end.getX() - start.getX();
			int yy = end.getY() - start.getY();

			int dx, dy;
			if (xx == 0) dx = 0;
			else dx = 1;
			if (yy == 0) dy = 0;
			else dy = 1;

			int length = rows[i].getLength();

			rowPoints[i] = new Point[length];
			for (int j = 0; j < length; j++) {
				int x = start.getX() + (j * dx);
				int y = start.getY() + (j * dy);
				rowPoints[i][j] = new Point(x, y);
			}
		}
		return rowPoints;
	}

	public int[] activeInRows(Point[][] rowPoints, int x, int y) { // return the row indices that it's in
		boolean[] activeInRow = new boolean[rowPoints.length];
		int[] indices;
		int[] tmp = new int[rowPoints.length];
		int counter = 0;
		for (int i = 0; i < rowPoints.length; i++) {
			for (int j = 0; j < rowPoints[i].length; j++) {
				if (rowPoints[i][j].getX() == x && rowPoints[i][j].getY() == y) {
					activeInRow[i] = true;
					tmp[counter] = i;
					counter++;
					break;
				}
			}
		}
		indices = new int[counter];
		for (int i = 0; i < counter; i++) {
			indices[i] = tmp[i];
		}
		return indices;
	}

	//	private Player getDisabledPlayer(Player player, Game gameState) {
	//		if (player.equals(game.getPlayerOne())) return game.getPlayerTwo();
	//		else return game.getPlayerOne();
	//	}
}
