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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
	private String[] newGames = new String[20];
	private String[] premades = {"Generic"};
	private Typeface f;
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
        }
        catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
			sd = new ScoreboardData(games);
        }
        int check = 0;
        games = sd.getSbs();
        /*
        for(int i = 0; i < premades.length; i++){
        	newGames[i] = premades[i];
        	check++;
        }
        */
        for(int i = check; i < 20; i++){
        	if(i-check < games.length){
        		newGames[i] = games[i-check];
        	}else{
        		newGames[i] = "";
        	}
        }
        f = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
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
	    		String selection = newGames[arg2].toLowerCase();
	    		//Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	    		
	    		String type;
	    		if (selection != "") {
	    		try 
	            { 
	    			//fname = getResources().getIdentifier(selection, "raw", ScoreboardList.this.getPackageName());
	    			
	    			//InputStream is = getResources().openRawResource(fname);
	                //ObjectInputStream ois = new ObjectInputStream(is); 
	                //sb = (Scoreboard) ois.readObject();
	                
	            	Intent myIntent = new Intent(ScoreboardList.this, com.example.cs356.ScoreboardUI.class);
	    			type = "savedboard";
	    			myIntent.putExtra("TYPE",type);
	    			String file = "/data/data/com.example.cs356/" + selection + ".bin";
	    			myIntent.putExtra("FILE",file);
	    			startActivity(myIntent); 
	            } 
	    			catch(Exception e){
	    				Log.v("Serialization Read Error : ",e.getMessage());
	    			}	
	    		}
	    		
	    		else 
	    			Toast.makeText(ScoreboardList.this, "Not a valid Selection" , Toast.LENGTH_LONG).show();
	    		
	        	}
			
			});
        gameList.setOnItemLongClickListener(new OnItemLongClickListener(){
        	@Override
			public boolean onItemLongClick(final AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardList.this, R.raw.click);
	    		mp.start();
	    		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardList.this, R.style.RefStyle));
				builder.setTitle("DELETE SCORE BOARD?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
		                      sd.remove(arg2);
		                      games = sd.getSbs();
		                      newGames = new String[20];
		                      for(int i = 0; i < 20; i++){
		                      	if(i < games.length){
		                      		newGames[i] = games[i];
		                      	}else{
		                      		newGames[i] = "";
		                      	}
		                      }
		                      RAdapter adapter = new RAdapter(ScoreboardList.this,android.R.layout.simple_list_item_1, newGames,f);
		                      gameList.setAdapter(adapter); 
		                      try{
		                      	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.cs356/scoreboards.bin")); 
		                        oos.writeObject(sd);
		                        oos.flush();
		                        oos.close();
		                      }
		                      catch(Exception e){
		              			Log.v("Serialization Read Error : ",e.getMessage());
		              			sd = new ScoreboardData(games);
		                      }
		                      	ListView l = (ListView) arg0;
		                      	String selection = l.getItemAtPosition(arg2).toString();
		      	    			selection = selection.toLowerCase();
		      	    			File file = new File("/data/data/com.example.cs356/" + selection + ".bin");
		      	    			file.delete();
		                   }
					});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
				return true;
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

