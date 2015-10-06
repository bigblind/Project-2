package com.project.server.logic.gamelogic;

import java.util.ArrayList;

import com.project.server.logic.Row;

public class RowRemovalRequestEvent {

	private ArrayList<Row> rows;
	
	public RowRemovalRequestEvent(ArrayList<Row> rows) {
		this.rows = rows;
	}
	
	public ArrayList<Row> getRows() {
		return this.rows;
	}
}
