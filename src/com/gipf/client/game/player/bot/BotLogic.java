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

	private Bot bot;

	public BotLogic(Bot bot) {
		this.bot = bot;
	}

	public void performLogic(Player player, Node node) {
		handleRows(player, node);
	}

		public boolean handleRows(Player player, Node node) {
			ArrayList<Row> rows = node.getGame().getBoard().checkForLines();
			Game tmpGame = node.getGame().copy();
			rows = this.rowsForPlayer(player.getStoneColor(), rows);
			Point[][] pointsInRow = new Point[rows.size()][];
			for (int i = 0; i < rows.size(); i++) {
				pointsInRow[i] = this.pointsInRow(rows.get(i));
			}
	
			for (int i = 0; i < pointsInRow.length; i++) {
				for (int j = 0; j < pointsInRow[i].length; j++) {
					if (activeInRows(pointsInRow, pointsInRow[i][j].getX(), pointsInRow[i][j].getY()).length == 1) {
						Point[] gipfStonesInRow = this.returnAllGipfPointsInRow(rows.get(i), player, tmpGame);
	
						if (gipfStonesInRow.length == 0) {
							Game gameCopy = tmpGame.copy();
							gameCopy.getBoard().removeRowAndExtensions(rows.get(i));
	
							if (player.equals(gameCopy.getPlayerOne())) {
								gameCopy.getPlayerOne().addStones(rows.get(i).getLength());
								gameCopy.getPlayerOne().addStones(rows.get(i).getWhiteExtensionStones().length);
							} else {
								gameCopy.getPlayerTwo().addStones(rows.get(i).getLength());
								gameCopy.getPlayerTwo().addStones(rows.get(i).getBlackExtensionStones().length);
							}
	
							Node newNode = new Node(node, gameCopy, new Action(pointsInRow[i][j]), false);
							node.addChild(newNode);
							if (!handleRows(player, newNode)) {
								newNode.setEndState(true);
								newNode.setValue(this.bot.getEvaluator().evaluate(newNode.getGame(), player));
							}
						} else {
							Game gameCopy = tmpGame.copy();
							gameCopy.getBoard().removeRowAndExtensions(rows.get(i));
	
							ArrayList<Point> toRemove = new ArrayList<Point>();
							Point[] allRowPoints = this.returnAllPointFromRow(rows.get(i));
							Point[] basicStonesToRemove = this.substract(allRowPoints, gipfStonesInRow);
							for (Point p : basicStonesToRemove)
								toRemove.add(p);
	
							Point[] points = new Point[toRemove.size()];
							points = toRemove.toArray(points);
							this.removePoints(player, gameCopy, points);
							Node newNode = new Node(node, gameCopy, new Action(pointsInRow[i][j]), false);
							node.addChild(newNode);
	
							int[] cntr = new int[gipfStonesInRow.length];
							boolean[] values = new boolean[gipfStonesInRow.length];
	
							for (int k = 0; k < Math.pow(2, gipfStonesInRow.length); k++) {
								for (int l = 0; l < cntr.length; l++) {
									if (cntr[l] == Math.pow(2, l)) {
										values[l] = !values[l];
										cntr[l] = 0;
									}
								}
								toRemove = new ArrayList<Point>();
								for (int m = 0; m < gipfStonesInRow.length; m++) {
									if (values[m]) toRemove.add(gipfStonesInRow[m]);
									ArrayList<Point> actionPoints = new ArrayList<Point>();
									for (int z = 0; z < gipfStonesInRow.length; z++) {
										if (values[z]) actionPoints.add(gipfStonesInRow[z]);
									}
									Point[] tmpPoints = new Point[toRemove.size()];
									tmpPoints = toRemove.toArray(tmpPoints);
									Point[] actionTmpPoints = new Point[actionPoints.size()];
									actionTmpPoints = actionPoints.toArray(actionTmpPoints);
									this.removePoints(player, newNode, tmpPoints, new Action(actionTmpPoints), true);
									toRemove.clear();
								}
							}
						}
						break;
					}
				}
			}
	
			if (rows.size() == 0) {
				node.setEndState(true);
				return false;
			} else return true;
		}

//	public boolean handleRows(Player player, Node node) {
//		Game game = node.getGame().copy();
//		ArrayList<Row> rows = game.getBoard().checkForLines();
//		rows = this.rowsForPlayer(player.getStoneColor(), rows);
//
//		if (rows.size() == 0) {
//			node.setEndState(true);
//			return false;
//		}
//		//		if (rows.size() == 1 && !this.containsGipfStone(player, game, rows.get(0).getFromPoint(), rows.get(0).getToPoint()) && !this.extPlayerContainGipf(player, game, rows.get(0).getWhiteExtensionStones(), rows.get(0).getBlackExtensionStones())) {
//		//			// it's simple
//		//			
//		//		} else {
//		// ... it's not you it's me, it's complicated
//		for (Row row : rows) {
////			System.out.println(row);
//			Point[] allStones = this.stonesInRowWithExtensions(game, row);
////			System.out.print("Points in selected row: ") ;
////			for (Point p : allStones)
////				System.out.print(p + " ");
////			System.out.println();
//			ArrayList<Point> toRemove = new ArrayList<Point>();
//			ArrayList<Point> gipfStones = new ArrayList<Point>();
//			for (Point p : allStones) {
//				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.WHITE_VALUE) toRemove.add(p);
//				else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.BLACK_VALUE) toRemove.add(p);
//
//				if (player.getStoneColor() == Board.WHITE_VALUE) {
//					if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) gipfStones.add(p);
//					else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) toRemove.add(p);
//				} else {
//					if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) toRemove.add(p);
//					else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) gipfStones.add(p);
//				}
//			}
//
//			int n = gipfStones.size();
//
//			if (n == 0) {
//				Point[] pointToRemove = new Point[toRemove.size()];
//				toRemove.toArray(pointToRemove);
//				this.removePoints(player, node, pointToRemove, new Action(), true);
//				continue;
//			}
//			boolean[] stoneStays = new boolean[n];
//			for (int i = 0; i < Math.pow(2, n); i++) {
//				for (int j = n - 1; j >= 0; j--) {
//					if ((i / (int) Math.pow(2, j)) % 2 == 1) stoneStays[j] = true;
//					else stoneStays[j] = false;
//				}
//
//				int falses = 0;
//				for (int k = 0; k < stoneStays.length; k++) {
//					if (!stoneStays[k]) {
//						toRemove.add(gipfStones.get(k));
//						falses++;
//					}
//				}
//
//				Point[] actionPoints = new Point[falses + 1];
//				actionPoints[0] = allStones[0];
//				int lastIndex = 1;
//				for (int k = 0; k < stoneStays.length; k++) {
//					if (!stoneStays[k]) {
//						actionPoints[lastIndex] = gipfStones.get(k);
//						lastIndex++;
//					}
//				}
//
//				Point[] pointToRemove = new Point[toRemove.size()];
//				toRemove.toArray(pointToRemove);
//				this.removePoints(player, node, pointToRemove, new Action(actionPoints), true);
//
//				int toRemoveSize = toRemove.size();
//				for (int k = toRemoveSize - 1; k > toRemoveSize - 1 - falses; k--) {
//					toRemove.remove(k);
//				}
//			}
//			//			}
//		}
////		System.out.println();
//		if (rows.size() != 0) return true;
//		else return false;
//	}

	public int[] allRowPointsWithExtensions(Game game, Row row) { // 1st spot: white stones; 2nd: black stones; 3rd: gipf white stones; 4th: gipf black	stones 
		Point start = row.getFromPoint();
		Point end = row.getToPoint();
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();

		if (deltaX > 0) deltaX = 1;
		else if (deltaX < 0) deltaX = -1;
		else deltaX = 0;
		if (deltaY > 0) deltaY = 1;
		else if (deltaY < 0) deltaY = -1;
		else deltaY = 0;

		Point connectedStart = game.getBoard().findConnectionEnd(start, -deltaX, -deltaY);
		Point connectedEnd = game.getBoard().findConnectionEnd(end, deltaX, deltaY);

		int xx = connectedEnd.getX() - connectedStart.getX();
		int yy = connectedEnd.getY() - connectedStart.getY();

		int dx, dy;
		if (xx == 0) dx = 0;
		else dx = 1;
		if (yy == 0) dy = 0;
		else dy = 1;

		int length;
		if (xx == 0) length = yy;
		else if (yy == 0) length = xx;
		else length = xx;

		int ws = 0;
		int bs = 0;
		int wgs = 0;
		int bgs = 0;
		for (int i = 0; i <= length; i++) {
			if (game.getBoard().getGrid()[start.getX() + (i * dx)][start.getY() + (i * dy)] == Board.WHITE_VALUE) ws++;
			else if (game.getBoard().getGrid()[start.getX() + (i * dx)][start.getY() + (i * dy)] == Board.BLACK_VALUE) bs++;
			else if (game.getBoard().getGrid()[start.getX() + (i * dx)][start.getY() + (i * dy)] == Board.GIPF_WHITE_VALUE) wgs++;
			else if (game.getBoard().getGrid()[start.getX() + (i * dx)][start.getY() + (i * dy)] == Board.GIPF_BLACK_VALUE) bgs++;
		}
		return new int[] { ws, bs, wgs, bgs };
	}

	public Point[] stonesInRowWithExtensions(Game game, Row row) {
		Point[] allPoints = new Point[row.getWhiteExtensionStones().length + row.getBlackExtensionStones().length + row.getLength()];

		Point start = row.getFromPoint();
		Point end = row.getToPoint();
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();

		if (deltaX > 0) deltaX = 1;
		else if (deltaX < 0) deltaX = -1;
		else deltaX = 0;
		if (deltaY > 0) deltaY = 1;
		else if (deltaY < 0) deltaY = -1;
		else deltaY = 0;

		ArrayList<Point> rowPoints = new ArrayList<Point>();
		for (int i = 0; i < row.getLength(); i++)
			rowPoints.add(new Point(start.getX() + (i * deltaX), start.getY() + (i * deltaY)));

		for (Point p : row.getBlackExtensionStones())
			rowPoints.add(p);
		for (Point p : row.getWhiteExtensionStones())
			rowPoints.add(p);

		rowPoints.toArray(allPoints);
		return allPoints;
	}

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
	private boolean extPlayerContainGipf(Player player, Game game, Point[] whiteExt, Point[] blackExt) {
		if (player.getStoneColor() == Board.BLACK_VALUE) {
			for (Point p : blackExt)
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) return true;
		} else {
			for (Point p : whiteExt)
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) return true;
		}
		return false;
	}

	public void removePoints(Player player, Node node, Point[] points, Action action, boolean checkRows) {
		Game game = node.getGame().copy();
		Player tmp = game.getPlayerOne();
		if (game.getPlayerOne().getStoneColor() == Board.WHITE_VALUE) tmp = game.getPlayerTwo();

		if (player.getStoneColor() == Board.WHITE_VALUE) {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.WHITE_VALUE) {
					tmp.setStoneAccount(tmp.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
					tmp.setStoneAccount(tmp.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		} else {
			for (Point p : points) {
				if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.BLACK_VALUE) {
					tmp.setStoneAccount(tmp.getStoneAccount() + 1);
				} else if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE) {
					tmp.setStoneAccount(tmp.getStoneAccount() + 2);
				}
				game.getBoard().getGrid()[p.getX()][p.getY()] = Board.EMPTY_TILE;
			}
		}
		if (checkRows) {
			Node newNode = new Node(node, game, action, false);
			node.addChild(newNode);

			if (!handleRows(player, newNode)) {
				newNode.setValue(this.bot.getEvaluator().evaluate(newNode.getGame(), player));
				newNode.setEndState(true);
			}
		} else {
			Node newNode = new Node(node, game, action, true);
			newNode.setValue(this.bot.getEvaluator().evaluate(newNode.getGame(), player));
			node.addChild(newNode);
		}
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

	public Point[] gipfPointsInRow(Row row, Game game) {
		Point[] points = pointsInRow(row);

		ArrayList<Point> gipfPoints = new ArrayList<Point>();
		for (Point p : points) {
			if (game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_BLACK_VALUE || game.getBoard().getGrid()[p.getX()][p.getY()] == Board.GIPF_WHITE_VALUE) {
				gipfPoints.add(p);
			}
		}

		Point[] result = new Point[gipfPoints.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = gipfPoints.get(i);
		}

		return result;
	}

	public Point[] combine(Point[] a, Point[] b) {
		Point[] result = new Point[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int i = a.length; i < a.length + b.length; i++) {
			result[i] = b[i - a.length];
		}
		return result;
	}

	public Point[] substract(Point[] a, Point[] b) {
		ArrayList<Point> tmp = new ArrayList<Point>();

		for (Point pa : a) {
			boolean inB = false;
			for (Point pb : b) {
				if (pa.equals(pb)) {
					inB = true;
					break;
				}
			}
			if (!inB) tmp.add(pa);
		}

		Point[] result = new Point[tmp.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = tmp.get(i);
		}
		return result;
	}

	public Point[] returnAllPointFromRow(Row row) {
		Point[] rowPoints = this.pointsInRow(row);
		Point[] tmp = this.combine(rowPoints, row.getWhiteExtensionStones());
		return this.combine(tmp, row.getBlackExtensionStones());
	}

	public Point[] returnAllGipfPointsInRow(Row row, Player player, Game game) {
		Point[] one = this.gipfPointsInRow(row, game);
		Point[] two;
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			two = this.gipfStonesOfRowExtension(game, row.getWhiteExtensionStones());
		} else {
			two = this.gipfStonesOfRowExtension(game, row.getBlackExtensionStones());
		}

		Point[] result = new Point[one.length + two.length];
		for (int i = 0; i < one.length; i++) {
			result[i] = one[i];
		}
		for (int i = one.length; i < two.length + one.length; i++) {
			result[i] = two[i - one.length];
		}
		return result;
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

	public int[] activeInRows(Point[][] rowPoints, int x, int y) {
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
}
