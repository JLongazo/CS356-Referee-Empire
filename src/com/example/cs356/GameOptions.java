package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOptions extends Activity {
	
	private LinearLayout teamNames;
	private Button back;
	private Button save;
	private ContinueData cd;
	private String file = "/data/data/com.example.cs356/continue.bin";
	private Scoreboard sb;
	private String rule[];
	private int fieldIds[] = new int[4];
	private int id = 0;
	private int teamCount;
	private String[] names;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_options);
		teamNames = (LinearLayout) this.findViewById(R.id.linearLayout1);
		back = (Button) this.findViewById(R.id.button1);
		save = (Button) this.findViewById(R.id.button2);
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
		for(int i = 0; i < teamCount; i++){
			TextView team = new TextView(this);
			team.setId(id++);
			team.setText(names[i]);
			teamNames.addView(team);
			EditText newName = new EditText(this);
			fieldIds[i] = id;
			newName.setId(id++);
			teamNames.addView(newName);
		}
		
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
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
				String newNames[] = new String[4];
				for(int i = 0; i < teamCount; i++){
					EditText et = (EditText) GameOptions.this.findViewById(fieldIds[i]);
					String text = et.getText().toString();
					if(text.length() > 0 && text.length() < 8){
						newNames[i] = text;
					} else {
						newNames[i] = names[i];
					}
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
