package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreboardList extends Activity {

	private Scoreboard [] savedBoards = new Scoreboard[10];
	private int current = 0;
	private static int fname;
	private ScoreboardData sd;
	private ListView gameList;
	private Button back;
	private TextView header;
	private String[] games = {"nothing!"};
	//= { "Generic", "Chess", "Scrabble", "Pictionary", "Catch Phrase", "Rummy", "Continental", "Go-Fish", "Racquetball", "Ping Pong", "Football", "Golf", "Frisbee",  "Basketball"};

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scoreboard_list);
        back = (Button) this.findViewById(R.id.buttonB);
        header = (TextView) this.findViewById(R.id.textViewG);
        gameList = (ListView) this.findViewById(R.id.games);
        try{
        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scoreboards.bin")); 
            sd = (ScoreboardData) ois.readObject();
            games = sd.getSbs();
        }
        catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
			sd = new ScoreboardData(games);
        }
        String[] newGames = new String[20];
        for(int i = 0; i < 20; i++){
        	if(i < games.length){
        		newGames[i] = games[i];
        	}else{
        		newGames[i] = "";
        	}
        }
        Typeface f = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
        RAdapter adapter = new RAdapter(this,android.R.layout.simple_list_item_1, newGames,f);
        header.setTypeface(f);
        header.setText("GAMES");
        header.setTextColor(Color.WHITE);
        header.setTextSize(30);
        gameList.setAdapter(adapter); 
        gameList.setOnItemClickListener(new OnItemClickListener(){
        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardList.this, R.raw.click);
	    		mp.start();
	    		ListView l = (ListView) arg0;
	    		String selection = l.getItemAtPosition(arg2).toString();
	    		selection = selection.toLowerCase();
	    		//Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	    	
	    		Scoreboard sb;
	    		String type;
	    		try 
	            { 
	    			fname = getResources().getIdentifier(selection, "raw", ScoreboardList.this.getPackageName());
	    			
	    			InputStream is = getResources().openRawResource(fname);
	                ObjectInputStream ois = new ObjectInputStream(is); 
	                sb = (Scoreboard) ois.readObject();
	                
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
			
			});
        
        back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardList.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(ScoreboardList.this, com.example.cs356.MainActivity.class);
				startActivity(myIntent);
			}
		});
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

