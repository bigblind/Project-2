package com.project.client.connection;

import java.util.ArrayList;

import com.project.client.board.Board;
import com.project.client.sound.SoundManager;
import com.project.client.visuals.BoardPanel;
import com.project.client.visuals.state.MoveStateA;
import com.project.client.visuals.state.OpponentTurnState;
import com.project.client.visuals.state.RemoveState;
import com.project.common.player.Player;
import com.project.common.player.PlayerEvent;
import com.project.common.player.PlayerListener;
import com.project.common.utils.Point;

public class ClientInterface implements PlayerListener {

	private ArrayList<PlayerListener> listeners;
	private SoundManager sound = new SoundManager();

	private int opponentAccount;
	private Board board;
	private BoardPanel boardPanel;

	private Player thisPlayer;

	public ClientInterface() {
		this.board = new Board();
		this.thisPlayer = new Player();
		this.listeners = new ArrayList<PlayerListener>();
		this.thisPlayer.addPlayerListener(this);
		sound.backgroundPlay();
	}

	public void initCall(byte stoneColor, byte numberOfStones, byte opponentStoneAccount, String boardString) {
		this.thisPlayer.setStoneAccount(numberOfStones);
		this.thisPlayer.setStoneColor(stoneColor);
		this.opponentAccount = opponentStoneAccount;
		this.readBoard(boardString);

		if (stoneColor == Board.BLACK_VALUE) this.boardPanel.setState(new OpponentTurnState(this.boardPanel, this));
		else if (stoneColor == Board.WHITE_VALUE) this.boardPanel.setState(new MoveStateA(this.boardPanel, this));
		else System.err.println("Invalid client input: Initialise StoneColor invalid");
	}

	public void receive(byte[] bytes) {
		String received = new String(bytes);
		System.out.println(received);
		if (received.startsWith("/i")) {
			// "/i Board.BLACK_VALUE thisStonesAccount opponentStonesAccount boardString";
			String info = received.split("/i ")[1];
			String boardString = received.substring(3 + info.split(" ")[0].length() + 1 + info.split(" ")[1].length() + 1 + info.split(" ")[2].length() + 1);
			this.initCall(Byte.parseByte(info.split(" ")[0]), Byte.parseByte(info.split(" ")[1]), Byte.parseByte(info.split(" ")[2]), boardString);
		} else if (received.startsWith("/u")) {
			sound.movePlay(true);
			// "/u 18 18 boardString";
			String[] subParts = received.split(" ");
			this.thisPlayer.setStoneAccount(Integer.parseInt(subParts[1]));
			this.opponentAccount = Integer.parseInt(subParts[2]);
			String boardString = received.substring(subParts[0].length() + subParts[1].length() + 3 + subParts[2].length());
			this.readBoard(boardString);
		} else if (received.startsWith("/s")) {
			System.out.println("in /s");
			// for updating state
			if (received.equals("/s move")) {
				this.boardPanel.setState(new MoveStateA(this.boardPanel, this));
			} else if (received.startsWith("/s remove")) {
				System.out.println("in remove");
				String[] subPartsX = received.split("Point: x = ");
				String[] subPartsY = received.split("y = ");
				int nRows = subPartsX.length / 2;
				Point[] rowPoints = new Point[nRows*2];
				for (int i = 0; i < nRows; i++) {
					int x1 = Integer.parseInt(subPartsX[1 + i*2].substring(0, 1));
					int y1 = Integer.parseInt(subPartsY[1 + i*2].substring(0, 1));

					int x2 = Integer.parseInt(subPartsX[1 + i*2 + 1].substring(0, 1));
					int y2 = Integer.parseInt(subPartsY[1 + i*2 + 1].substring(0, 1));

					rowPoints[i * 2] = new Point(x1, y1);
					rowPoints[i * 2 + 1] = new Point(x2, y2);
				}
				for (Point point : rowPoints) System.out.println(point);
				this.boardPanel.setState(new RemoveState(this.boardPanel, this, rowPoints)); 
			} else if (received.startsWith("/s opponent")) {
				this.boardPanel.setState(new OpponentTurnState(this.boardPanel, this));
			} else {
				System.err.println("Invalid client input: Input not recognised");
			}
		}
	}

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

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}
}
