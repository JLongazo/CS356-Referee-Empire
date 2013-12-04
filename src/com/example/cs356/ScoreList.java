package com.example.cs356;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

public class ScoreList extends Activity {

	private ListView scoreList;
	private Button back;
	private TextView header;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_score_list);
        back = (Button) this.findViewById(R.id.buttonB2);
        header = (TextView) this.findViewById(R.id.textViewG2);
        scoreList = (ListView) this.findViewById(R.id.score);
		Scores s;
		try 
        { 
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scores.bin")); 
            s = (Scores) ois.readObject();  
        } 
		catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
			s = new Scores();
		}
		final String scores[] = new String[10];
		final ArrayList<String[]> details = new ArrayList<String[]>();
		scores[0] = "No Scores Saved!";
		for(int i = 1; i < 10; i++){
			scores[i] = " ";
		}
		for(int i = s.size() - 1; i >= 0; i--){
			scores[s.size() - i - 1] = (s.size() - i) + ". " + s.getScore(i).getData();
			details.add(s.getScore(i).getDetails());
		} 
		Typeface f = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		RAdapter adapter = new RAdapter(this,android.R.layout.simple_list_item_1, scores,f);
		Typeface f2 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
        header.setTypeface(f2);
        header.setText("SCORES");
        header.setTextColor(Color.WHITE);
        header.setTextSize(30);
        scoreList.setAdapter(adapter); 
        scoreList.setOnItemClickListener(new OnItemClickListener(){
        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
        		
        		if (scores[arg2]  != " " && scores[arg2] != "No Scores Saved!") {
        		
				MediaPlayer mp = MediaPlayer.create(ScoreList.this, R.raw.click);
	    		mp.start();
	    		CharSequence[] ind = details.get(arg2);
	    		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreList.this, R.style.RefStyle));
				builder.setTitle("ROUND BREAKDOWN");
				builder.setItems(ind, null);
				builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
        		}
        		
        		
	    		else 
	    			Toast.makeText(ScoreList.this, "Not a valid Selection" , Toast.LENGTH_LONG).show();
        	}
			
			});

        
        
        back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreList.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(ScoreList.this, com.example.cs356.MainActivity.class);
				startActivity(myIntent);
			}
		});
	}
	

}
