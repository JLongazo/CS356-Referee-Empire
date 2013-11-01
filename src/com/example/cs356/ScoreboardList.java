package com.example.cs356;

<<<<<<< HEAD
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScoreboardList extends ListActivity{
	
	public ScoreboardList(){
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(ScoreboardList.this, android.R.layout.simple_list_item_1, games));
	}

	private Scoreboard [] savedBoards = new Scoreboard[10];
	
	private String[] games = new String[10];
	
	private int current = 0;
	
	public void addScoreBoard(Scoreboard add) {
		savedBoards[current] = add;
		games[current] = add.getName();
		current++;
=======
public class ScoreboardList {
	
	Scoreboard [] savedBoards = new Scoreboard[10];
	
	public void addScoreBoard() {
		
>>>>>>> Empire_Rafik
	}
	
	public void removeScoreBoard() {
		
	}
	
	public void selectScoreBoard() {
		
	}
	
	public void mainMenu() {
		
	}


}
