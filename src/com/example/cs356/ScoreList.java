package com.example.cs356;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
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
		String scores[] = new String[10];
		scores[0] = "No Scores Saved!";
		for(int i = 1; i < 10; i++){
			scores[i] = " ";
		}
		for(int i = s.size() - 1; i >= 0; i--){
			scores[s.size() - i - 1] = (s.size() - i) + ". " + s.getScore(i).getData();
		} 
		Typeface f = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		RAdapter adapter = new RAdapter(this,android.R.layout.simple_list_item_1, scores,f);
		Typeface f2 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
        header.setTypeface(f2);
        header.setText("SCORES");
        header.setTextColor(Color.WHITE);
        header.setTextSize(30);
        scoreList.setAdapter(adapter); 
        
        
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
