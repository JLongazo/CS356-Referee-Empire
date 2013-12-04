package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class GameOptions extends Activity {
	
	private LinearLayout teamNames;
	private Button back;
	private Button save;
	private TextView options;
	private TextView op1;
	private TextView op12;
	private ContinueData cd;
	private String file = "/data/data/com.example.cs356/continue.bin";
	private Scoreboard sb;
	private String rule[];
	private int fieldIds[] = new int[4];
	private int nameIds[] = new int[4];
	private int id = 0;
	private int teamCount;
	private String[] names;
	private Typeface f1;
	private Typeface f2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_options);
		options = (TextView) this.findViewById(R.id.textView1);
		op1 = (TextView) this.findViewById(R.id.textView2);
		op12 = (TextView) this.findViewById(R.id.textView3);
		teamNames = (LinearLayout) this.findViewById(R.id.nameChange);
		back = (Button) this.findViewById(R.id.button1);
		save = (Button) this.findViewById(R.id.button2);
		f1 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
		f2 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		options.setTypeface(f1);
		options.setTextSize(30);
		options.setTextColor(Color.WHITE);
		CharSequence o1 = "CHANGE TEAM NAMES";
		op1.setText(o1);
		op1.setTypeface(f1);
		op1.setTextColor(Color.WHITE);
		CharSequence o2 = "7 CHARACTERS MAX";
		op12.setText(o2);
		op12.setTypeface(f2);
		op12.setTextColor(Color.WHITE);
		save.setTypeface(f2);
		save.setTextColor(Color.WHITE);
		save.setText("SAVE NAMES");
		save.setTextSize(20);
		try 
        { 
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)); 
            cd = (ContinueData) ois.readObject();  
        } 
		catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
		}
		sb = cd.getSb();
		teamCount = sb.getTeams();
		names = sb.getTeamNames();
		int height = teamCount * 120 + 150;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height);
		teamNames.setLayoutParams(params);
		LinearLayout.LayoutParams oparams = new LinearLayout.LayoutParams(550,60);
		for(int i = 0; i < teamCount; i++){
			TextView team = new TextView(this);
			team.setLayoutParams(oparams);
			team.setGravity(Gravity.CENTER_VERTICAL);
			nameIds[i] = id;
			team.setId(id++);
			team.setText(names[i].toUpperCase());
			team.setTypeface(f2);
			team.setTextColor(Color.WHITE);
			teamNames.addView(team);
			EditText newName = new EditText(this);
			newName.setTextSize(12);
			newName.setLayoutParams(oparams);
			fieldIds[i] = id;
			newName.setId(id++);
			teamNames.addView(newName);
		}
		
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(GameOptions.this, R.raw.click);
				mp.start();
				cd.setSb(sb);
				try 
			    { 
			       ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/continue.bin"))); 
			       //Select where you wish to save the file... 
			       oos.writeObject(cd); // write the class as an 'object' 
			       oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin' 
			       oos.close();// close the stream 
			    } 
			    catch(Exception ex) 
			    { 
			       Log.v("Serialization Save Error : ",ex.getMessage()); 
			       ex.printStackTrace(); 
			    }
				Intent myIntent = new Intent(GameOptions.this, com.example.cs356.ScoreboardUI.class);
				myIntent.putExtra("FILE", file);
				myIntent.putExtra("TYPE", "continue");
				startActivity(myIntent);
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(GameOptions.this, R.raw.click);
				mp.start();
				String newNames[] = new String[4];
				for(int i = 0; i < teamCount; i++){
					EditText et = (EditText) GameOptions.this.findViewById(fieldIds[i]);
					String text = et.getText().toString();
					if(text.length() > 0 && text.length() < 8){
						newNames[i] = text;
					} else {
						newNames[i] = names[i];
					}
					TextView tv = (TextView) GameOptions.this.findViewById(nameIds[i]);
					tv.setText(newNames[i].toUpperCase());
					et.setText("");
				}
				
				sb.setTeamNames(newNames);
				Toast.makeText(GameOptions.this, "Names Changed", Toast.LENGTH_LONG).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_options, menu);
		return true;
	}

}
