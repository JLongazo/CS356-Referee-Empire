package com.example.cs356;

import java.io.InputStream;
import java.io.ObjectInputStream;
import android.os.Bundle;
import android.util.Log;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ScoreboardList extends ListActivity {

	private Scoreboard [] savedBoards = new Scoreboard[10];
	private int current = 0;
	private static int fname;
	
	String[] games = { "Generic", "Chess", "Scrabble", "Pictionary", "Catch Phrase", "Rummy", "Continental", "Go-Fish", "Racquetball", "Ping Pong", "Football", "Golf", "Frisbee",  "Basketball"};

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, games);
        setListAdapter(adapter);    
    }

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String selection = l.getItemAtPosition(position).toString();
		selection = selection.toLowerCase();
		//Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	
		ContinueData cd;
		String type;
		try 
        { 
			fname = this.getResources().getIdentifier(selection, "raw", this.getPackageName());
			
			InputStream is = getResources().openRawResource(fname);
            ObjectInputStream ois = new ObjectInputStream(is); 
            cd = (ContinueData) ois.readObject();
            
        	Intent myIntent = new Intent(ScoreboardList.this, com.example.cs356.ScoreboardUI.class);
			type = "savedboard";
			myIntent.putExtra("TYPE",type);
			String file = "android.resource://" + getPackageName() + "/raw/" + selection;
			myIntent.putExtra("FILE",file);
			startActivity(myIntent); 
        } 
			catch(Exception e){
				Log.v("Serialization Read Error : ",e.getMessage());
			}		
	}
	
	public void addScoreBoard(Scoreboard add) {
		savedBoards[current] = add;
		games[current] = add.getName();
		current++;
	}
	
	public void removeScoreBoard() {
		
	}
	
	public void selectScoreBoard() {
		
	}
	
	public void mainMenu() {
		
	}
	
	public static int getFileInt(){
		return fname;
	}
}

