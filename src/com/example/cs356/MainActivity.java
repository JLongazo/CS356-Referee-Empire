package com.example.cs356;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button create;
	Button scores;
	Button resume;
	Button select;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		resume = (Button) this.findViewById(R.id.resume);
		create = (Button) this.findViewById(R.id.create);
		scores = (Button) this.findViewById(R.id.scores);
		select = (Button) this.findViewById(R.id.select);



		create.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreboardUI.class);
				String type = "null";
				myIntent.putExtra("TYPE",type);
				String file = "null";
				myIntent.putExtra("FILE",file);
				startActivity(myIntent);					
				}});
		
		resume.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.click);
				mp.start();
				ContinueData cd;
				String type;
				try 
		        { 
		            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/continue.bin")); 
		            cd = (ContinueData) ois.readObject();
		            if(cd.isCheck()){
		            	Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreboardUI.class);
						type = "continue";
						myIntent.putExtra("TYPE",type);
						String file = "/data/data/com.example.cs356/continue.bin";
						myIntent.putExtra("FILE",file);
						startActivity(myIntent);
		            }
		            else{
		            	Toast.makeText(MainActivity.this, "No Saved Game!", Toast.LENGTH_LONG).show();
		            }
		            
		        } 
				catch(Exception e){
					Log.v("Serialization Read Error : ",e.getMessage());
					Toast.makeText(MainActivity.this, "No Saved Game!", Toast.LENGTH_LONG).show();
				}
					
				}});

		
		
		scores.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreList.class);
				startActivity(myIntent);
				}});
		
		select.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreboardList.class);
				startActivity(myIntent);
				//select.setText("Not Yet Implemented");					
				}});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
