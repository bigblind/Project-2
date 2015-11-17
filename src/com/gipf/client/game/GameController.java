package com.gipf.client.game;

import com.gipf.client.connector.Connector;
import com.gipf.client.game.player.Player;
import com.gipf.client.utils.Point;
import com.project.client.base.Controller;
import com.project.client.board.Board;
import com.project.client.board.Row;
import com.project.client.visuals.state.MoveStateA;
import com.project.client.visuals.state.RemoveState;
import com.project.client.visuals.state.WaitState;

public class GameController {

	private Controller controller;

	private Player thisPlayer;

	public GameController(Controller controller) {
		this.thisPlayer = new Player();
		this.controller = controller;
	}

	public void input(String received) {
		if (received.startsWith("/i")) {
			// "/i Board.BLACK_VALUE thisStonesAccount opponentStonesAccount boardString";
			String info = received.split("/i ")[1];
			String boardString = received.substring(3 + info.split(" ")[0].length() + 1 + info.split(" ")[1].length() + 1 + info.split(" ")[2].length() + 1);
			this.initCall(Integer.parseInt(info.split(" ")[0]), Integer.parseInt(info.split(" ")[1]), Integer.parseInt(info.split(" ")[2]), boardString);
		} else if (received.startsWith("/u")) {
			// "/u 18 18 boardString";
			String[] subParts = received.split(" ");
			this.thisPlayer.setStoneAccount(Integer.parseInt(subParts[1]));
			returnOpponent(this.thisPlayer).setStoneAccount(Integer.parseInt(subParts[2]));
			String boardString = received.substring(subParts[0].length() + subParts[1].length() + 3 + subParts[2].length());
			this.readBoard(boardString);
		} else if (received.startsWith("/s")) {
			// for updating state
			if (received.equals("/s move")) {
				this.controller.getGamePanel().setState(new MoveStateA(this.controller.getGamePanel(), this));
			} else if (received.startsWith("/s remove")) {
				System.out.println(received);
				String[] rowStrings = received.split("endRow ");
				Row[] rows = new Row[rowStrings.length];
				for (int i = 0; i < rows.length; i++)
					rows[i] = this.readRow(rowStrings[i]);

				//				System.out.println(received);
				//				String[] subPartsX = received.split("Point: x = ");
				//				String[] subPartsY = received.split("y = ");
				//				int nRows = subPartsX.length / 2;
				//				Point[] rowPoints = new Point[nRows * 2];
				//				for (int i = 0; i < nRows; i++) {
				//					int x1 = Integer.parseInt(subPartsX[1 + i * 2].substring(0, 1));
				//					int y1 = Integer.parseInt(subPartsY[1 + i * 2].substring(0, 1));
				//
				//					int x2 = Integer.parseInt(subPartsX[1 + i * 2 + 1].substring(0, 1));
				//					int y2 = Integer.parseInt(subPartsY[1 + i * 2 + 1].substring(0, 1));
				//
				//					rowPoints[i * 2] = new Point(x1, y1);
				//					rowPoints[i * 2 + 1] = new Point(x2, y2);
				//				}
				this.controller.getGamePanel().setState(new RemoveState(this.controller.getGamePanel(), this, rows));
			} else if (received.startsWith("/s wait")) {
				this.controller.getGamePanel().setState(new WaitState(this.controller.getGamePanel(), this));
			} else {
				System.err.println("Invalid client input: Input not recognised");
			}
		} else if (received.startsWith("/m")) {
			if (received.equals("/m valid")) {
				// this.soundManager.movePlay(true);
			} else if (received.equals("/m invalid")) {
				// this.soundManager.movePlay(false);
				this.controller.getGamePanel().setState(new MoveStateA(this.controller.getGamePanel(), this));
			} else {
				System.err.println("Invalid client input: Input not recognised");
			}
		} else if (received.startsWith("/g")) {
			if (received.equals("/g win")) {
				// this.soundManager.stopPlayBackground();
				// this.soundManager.winPlay();
			} else if (received.startsWith("/g lose")) {
				// this.soundManager.stopPlayBackground();
				// this.soundManager.losePlay();
			} else {
				System.err.println("Invalid client input: Input not recognised");
			}
		} else {
			System.err.println("Invalid client input: Input not recognised");
		}
		this.controller.getGamePanel().revalidate();
		this.controller.getGamePanel().repaint();
	}

	private Player returnOpponent(Player player) {
		if (player.equals(this.controller.getGame().getPlayerOne())) return this.controller.getGame().getPlayerTwo();
		else return this.controller.getGame().getPlayerOne();
	}

	private void initCall(int stoneColor, int numberOfStones, int opponentStones, String boardString) {
		this.thisPlayer.setStoneAccount(numberOfStones);
		this.thisPlayer.setStoneColor(stoneColor);
		if (stoneColor == Board.BLACK_VALUE) {
			this.controller.setGame(new Game(new Player(Board.WHITE_VALUE), this.thisPlayer));
			this.controller.getGame().getPlayerOne().setStoneAccount(opponentStones);
			this.controller.getGamePanel().setGame(this.controller.getGame());
			this.controller.getGamePanel().setState(new WaitState(this.controller.getGamePanel(), this));
		} else if (stoneColor == Board.WHITE_VALUE) {
			this.controller.setGame(new Game(this.thisPlayer, new Player(Board.BLACK_VALUE)));
			this.controller.getGame().getPlayerTwo().setStoneAccount(opponentStones);
			this.controller.getGamePanel().setGame(this.controller.getGame());
			this.controller.getGamePanel().setState(new MoveStateA(this.controller.getGamePanel(), this));
		} else {
			System.err.println("Invalid client input: Initialise invalid.");
		}
		this.readBoard(boardString);
		this.controller.getGamePanel().setVisible(true);
		System.out.println(this.controller.getGamePanel().getS() + this.thisPlayer);
	}

	private void readBoard(String boardString) {
		String[] subParts = boardString.split(" ");

		int[][] newBoardGrid = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				newBoardGrid[i][j] = Integer.parseInt(subParts[i * 9 + j]);
			}
		}
		this.controller.getGame().getBoard().setGrid(newBoardGrid);
	}

	private Row readRow(String rowString) {
		System.out.println("Row string input: " + rowString);
		System.out.println();

		Point from = this.readPoint(rowString.split("from: ")[1].split("\\]")[0]);
		Point end = this.readPoint(rowString.split("to: ")[1].split("\\]")[0]);

		Player player;
		if (rowString.split("player: ")[1].startsWith("Player 1")) player = controller.getGame().getPlayerOne();
		else player = controller.getGame().getPlayerTwo();

		int length = Integer.parseInt(rowString.split("length: ")[1].substring(0, 1));

		String whiteExt = rowString.split("\\{")[1].split("\\}")[0];
		String blackExt = rowString.split("\\{")[2].split("\\}")[0];

		String[] whiteExt2;
		if (!whiteExt.equals("")) whiteExt2 = whiteExt.split("\\]");
		else whiteExt2 = new String[0];

		String[] blackExt2;
		if (!blackExt.equals("")) blackExt2 = blackExt.split("\\]");
		else blackExt2 = new String[0];

		Point[] whiteExtensions = new Point[whiteExt2.length];
		Point[] blackExtensions = new Point[blackExt2.length];

		for (int i = 0; i < whiteExtensions.length; i++) {
			whiteExtensions[i] = this.readPoint(whiteExt2[i]);
		}

		for (int i = 0; i < blackExtensions.length; i++) {
			blackExtensions[i] = this.readPoint(blackExt2[i]);
		}

		return new Row(from, end, player, length, whiteExtensions, blackExtensions);
	}

	private Point readPoint(String pointString) {
		int x = Integer.parseInt(pointString.split("x = ")[1].substring(0, 1));
		int y = Integer.parseInt(pointString.split("y = ")[1].substring(0, 1));

		return new Point(x, y);
	}

	public void setGame(Game game) {
		this.controller.setGame(game);
	}

	public Player getThisPlayer() {
		return this.thisPlayer;
	}

	public Connector getConnector() {
		return this.controller.getConnector();
	}
}
