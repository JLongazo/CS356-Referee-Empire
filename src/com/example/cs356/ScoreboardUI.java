package com.example.cs356;

import java.io.*;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class ScoreboardUI extends Activity {
	
	Button whistle;
	Button b1;
	private Scoreboard sb;
	private TextView name;
	private LinearLayout teams;
	private LinearLayout scores;
	private LinearLayout tNames;
	private LinearLayout tbuttons;
	private LinearLayout bNames;
	private LinearLayout bbuttons;
	private int id = 1;
	private int teamid[] = new int[4];
	private int scoreid[] = new int[4];
	private int tbutid[] = new int[8];
	private int bbutid[] = new int[8];
	

	public void whistlesound(View view) 
	{
		
        MediaPlayer mp = MediaPlayer.create(this, R.raw.whistle);

		mp.start();
		//Intent whistle = new Intent(ScoreboardUI.this, com.example.cs356.DummyTester.class);
		//MainActivity.this.startActivity(myIntent);
		//startActivity(whistle);
		//setContentView(R.layout.scoreboard);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard_ui);
		name = (TextView) this.findViewById(R.id.textView1);
		teams = (LinearLayout) this.findViewById(R.id.Teams);
		scores = (LinearLayout) this.findViewById(R.id.Scores);
		tNames = (LinearLayout) this.findViewById(R.id.tNames);
		tbuttons = (LinearLayout) this.findViewById(R.id.Tbuttons);
		bNames = (LinearLayout) this.findViewById(R.id.bNames);
		bbuttons = (LinearLayout) this.findViewById(R.id.Bbuttons);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		params.weight = 1;
		LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		bparams.gravity = Gravity.CENTER;
		
		Scoreboard sb = (Scoreboard)loadSerializedObject(new File("/data/data/com.example.cs356/test1.bin"));
		
		//Not needed if using statement above
		//sb = new Scoreboard(); //load scoreboard
		//initializeScoreboard();
		
		
		name.setText(sb.getName());
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		String teamNames[] = sb.getTeamNames();
		for(int j = 0; j < teamCount; j++){
			LinearLayout team = new LinearLayout(this);
			team.setId(id++);
			LinearLayout score = new LinearLayout(this);
			score.setId(id++);
			LinearLayout tnrow = new LinearLayout(this);
			tnrow.setId(id++);
			LinearLayout trow = new LinearLayout(this);
			trow.setId(id++);
			LinearLayout bnrow = new LinearLayout(this);
			bnrow.setId(id++);
			LinearLayout brow = new LinearLayout(this);
			brow.setId(id++);
			team.setOrientation(LinearLayout.VERTICAL);
			score.setOrientation(LinearLayout.VERTICAL);
			teams.addView(team,params);
			scores.addView(score,params);
			tNames.addView(tnrow,params);
			tbuttons.addView(trow,params);
			bNames.addView(bnrow,params);
			bbuttons.addView(brow,params);
			trow.setGravity(Gravity.CENTER);
			tnrow.setGravity(Gravity.CENTER);
			brow.setGravity(Gravity.CENTER);
			bnrow.setGravity(Gravity.CENTER);
			ScoreCounter scoreC = new ScoreCounter(0,1,sb.getDigits(),this);
			scoreid[j]=id;
			scoreC.setId(id++);
			TextView name1 = new TextView(this);
			teamid[j]=id;
			name1.setId(id++);
			name1.setText(teamNames[j]);
			name1.setTextColor(Color.RED);
            name1.setTextSize(25);
            scoreC.setBackgroundColor(Color.YELLOW);
            scoreC.setWidth(150);
            scoreC.setHeight(150);
            scoreC.setTextSize(30);
			team.addView(name1,params);
			score.addView(scoreC,params);
			char tbuttons[] = sb.getTopButtons();
			String tnames[] = sb.gettNames();
			for(int i = 0; i < sb.getTCount(); i++){
				switch(tbuttons[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames[i]);
					TextView name2 = new TextView(this);
					name2.setText(sp.getName());
					tbutid[i+j]=id;
					sp.setId(id++);
					name2.setId(id++);
					trow.addView(sp,bparams);
					tnrow.addView(name2,bparams);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons[] = sb.getBottomButtons();
				String bnames[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					switch(bbuttons[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames[i]);
						TextView name2 = new TextView(this);
						name2.setText(sp.getName());
						bbutid[i+j]=id;
						sp.setId(id++);
						name2.setId(id++);
						brow.addView(sp,bparams);
						bnrow.addView(name2,bparams);
						break;
					}
				}
			}
		}
		if(sb.isHasNeutral()){
			
		}
			
			
		//saves scoreboard based on current spec
		//	saveObject(sb);


		
		
		/**
		whistle = (Button) this.findViewById(R.id.Whistle);

		
		whistle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//whistle.setText("You Clicked Me");
				//setContentView(R.layout.tototo);
					
				}});
		*/
		
			
			b1 = (Button) this.findViewById(R.id.button1);

			
			b1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//b1.setText("Test");
					//setContentView(R.layout.tototo);
					
					Scoreboard sbimport = (Scoreboard)loadSerializedObject(new File("/data/data/com.example.cs356/test.bin"));

					//sb = new Scoreboard(); //load scoreboard
					initializeScoreboard();
					
					//loadSerializedObject();
					
					}});
		

	}
	
	
    public void saveObject(Scoreboard sb){
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/test1.bin"))); //Select where you wish to save the file...
           oos.writeObject(sb); // write the class as an 'object'
           oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
           oos.close();// close the stream
        }
        catch(Exception ex)
        {
           Log.v("Serialization Save Error : ",ex.getMessage());
           ex.printStackTrace();
        }
   }
    
    
    public Object loadSerializedObject(File f)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            return o;
        }
        catch(Exception ex)
        {
        Log.v("Serialization Read Error : ",ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
	
	
	

	public void initializeScoreboard(){
		String names[] = {"team1", "team2", "team3", "team4"};
		String tnames[] = {"tc1", "tc2"};
		String bnames[] = {"tc3", "tc4"};
		char trow[] = {'c','c'};
		char brow[] = {'c','c'};
		sb.setTopButtons(trow);
		sb.setBottomButtons(brow);
		sb.setTCount(2);
		sb.setBCount(2);
		sb.settNames(tnames);
		sb.setbNames(bnames);
		sb.setHasNeutral(false);
		sb.setTeamNames(names);
		sb.setTeams(1);
		sb.setDigits(2);
		sb.setName("test");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoreboard_ui, menu);
		return true;
	}
	
	
	

}
