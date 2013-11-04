package com.example.cs356;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
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
	Scoreboard sb;
	LinearLayout teams;
	LinearLayout scores;
	LinearLayout tNames;
	LinearLayout tbuttons;
	LinearLayout bNames;
	LinearLayout bbuttons;

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
		sb = new Scoreboard(); //load scoreboard
		initializeScoreboard();
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		String teamNames[] = sb.getTeamNames();
		switch(teamCount){
		case 4:
			LinearLayout team4 = new LinearLayout(this);
			LinearLayout scores4 = new LinearLayout(this);
			LinearLayout tnrow4 = new LinearLayout(this);
			LinearLayout trow4 = new LinearLayout(this);
			LinearLayout bnrow4 = new LinearLayout(this);
			LinearLayout brow4 = new LinearLayout(this);
			team4.setOrientation(LinearLayout.VERTICAL);
			scores4.setOrientation(LinearLayout.VERTICAL);
			teams.addView(team4,params);
			scores.addView(scores4,params);

			tNames.addView(tnrow4,params);
			tbuttons.addView(trow4,params);
			bNames.addView(bnrow4,params);
			bbuttons.addView(brow4,params);
			trow4.setGravity(Gravity.CENTER);
			tnrow4.setGravity(Gravity.CENTER);
			brow4.setGravity(Gravity.CENTER);
			bnrow4.setGravity(Gravity.CENTER);
			ScoreCounter score4 = new ScoreCounter(0,1,sb.getDigits(),this);
			TextView name4 = new TextView(this);
			name4.setTextColor(Color.RED);
			name4.setTextSize(25);
			score4.setBackgroundColor(Color.YELLOW);
			score4.setWidth(150);
			score4.setHeight(150);
			score4.setTextSize(50);
			name4.setText(teamNames[3]);
			team4.addView(name4,params);
			scores4.addView(score4,params);
			char tbuttons4[] = sb.getTopButtons();
			String tnames4[] = sb.gettNames();
			for(int i = 0; i < sb.getTCount(); i++){
				switch(tbuttons4[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames4[i]);
					TextView name = new TextView(this);
					name.setTextColor(Color.RED);
					name.setText(sp.getName());
					sp.setId(i + 40);
					name.setId(i + 44);
					trow4.addView(sp,bparams);
					tnrow4.addView(name,bparams);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons4[] = sb.getBottomButtons();
				String bnames4[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					switch(bbuttons4[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames4[i]);
						TextView name = new TextView(this);
						name.setTextColor(Color.RED);
						name.setText(sp.getName());
						sp.setId(i + 400);
						name.setId(i + 404);
						brow4.addView(sp,bparams);
						bnrow4.addView(name,bparams);
						break;
					}
				}
			}
		case 3:
			LinearLayout team3 = new LinearLayout(this);
			LinearLayout scores3 = new LinearLayout(this);
			LinearLayout tnrow3 = new LinearLayout(this);
			LinearLayout trow3 = new LinearLayout(this);
			LinearLayout bnrow3 = new LinearLayout(this);
			LinearLayout brow3 = new LinearLayout(this);
			team3.setOrientation(LinearLayout.VERTICAL);
			scores3.setOrientation(LinearLayout.VERTICAL);
			teams.addView(team3,params);
			scores.addView(scores3,params);
			tNames.addView(tnrow3,params);
			tbuttons.addView(trow3,params);
			bNames.addView(bnrow3,params);
			bbuttons.addView(brow3,params);
			trow3.setGravity(Gravity.CENTER);
			tnrow3.setGravity(Gravity.CENTER);
			brow3.setGravity(Gravity.CENTER);
			bnrow3.setGravity(Gravity.CENTER);
			ScoreCounter score3 = new ScoreCounter(0,1,sb.getDigits(),this);
			TextView name3 = new TextView(this);
			name3.setTextColor(Color.RED);
			name3.setTextSize(25);
			score3.setBackgroundColor(Color.YELLOW);
			score3.setWidth(150);
			score3.setHeight(150);
			score3.setTextSize(50);
			name3.setText(teamNames[2]);
			team3.addView(name3,params);
			scores3.addView(score3,params);
			char tbuttons3[] = sb.getTopButtons();
			String tnames3[] = sb.gettNames();
			for(int i = 0; i < sb.getTCount(); i++){
				switch(tbuttons3[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames3[i]);
					TextView name = new TextView(this);
					name.setText(sp.getName());
					name.setTextColor(Color.RED);
					sp.setId(i + 30);
					name.setId(i + 34);
					trow3.addView(sp,bparams);
					tnrow3.addView(name,bparams);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons3[] = sb.getBottomButtons();
				String bnames3[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					switch(bbuttons3[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames3[i]);
						TextView name = new TextView(this);
						name.setTextColor(Color.RED);
						name.setText(sp.getName());
						sp.setId(i + 300);
						name.setId(i + 304);
						brow3.addView(sp,bparams);
						bnrow3.addView(name,bparams);
						break;
					}
				}
			}
		case 2:
			LinearLayout team2 = new LinearLayout(this);
			LinearLayout scores2 = new LinearLayout(this);
			LinearLayout tnrow2 = new LinearLayout(this);
			LinearLayout trow2 = new LinearLayout(this);
			LinearLayout bnrow2 = new LinearLayout(this);
			LinearLayout brow2 = new LinearLayout(this);
			team2.setOrientation(LinearLayout.VERTICAL);
			scores2.setOrientation(LinearLayout.VERTICAL);
			teams.addView(team2,params);
			scores.addView(scores2,params);
			tNames.addView(tnrow2,params);
			tbuttons.addView(trow2,params);
			bNames.addView(bnrow2,params);
			bbuttons.addView(brow2,params);
			trow2.setGravity(Gravity.CENTER);
			tnrow2.setGravity(Gravity.CENTER);
			brow2.setGravity(Gravity.CENTER);
			bnrow2.setGravity(Gravity.CENTER);
			ScoreCounter score2 = new ScoreCounter(0,1,sb.getDigits(),this);
			TextView name2 = new TextView(this);
			name2.setTextColor(Color.RED);
			name2.setTextSize(25);
			score2.setBackgroundColor(Color.YELLOW);
			score2.setWidth(150);
			score2.setHeight(150);
			score2.setTextSize(50);
			name2.setText(teamNames[1]);
			team2.addView(name2,params);
			scores2.addView(score2,params);
			char tbuttons2[] = sb.getTopButtons();
			String tnames2[] = sb.gettNames();
			for(int i = 0; i < sb.getTCount(); i++){
				switch(tbuttons2[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames2[i]);
					TextView name = new TextView(this);
					name.setTextColor(Color.RED);
					name.setText(sp.getName());
					sp.setId(i + 20);
					name.setId(i + 24);
					trow2.addView(sp,bparams);
					tnrow2.addView(name,bparams);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons2[] = sb.getBottomButtons();
				String bnames2[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					switch(bbuttons2[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames2[i]);
						TextView name = new TextView(this);
						name.setTextColor(Color.RED);
						name.setText(sp.getName());
						sp.setId(i + 200);
						name.setId(i + 204);
						brow2.addView(sp,bparams);
						bnrow2.addView(name,bparams);
						break;
					}
				}
			}
		case 1:
			LinearLayout team1 = new LinearLayout(this);
			LinearLayout scores1 = new LinearLayout(this);
			LinearLayout tnrow1 = new LinearLayout(this);
			LinearLayout trow1 = new LinearLayout(this);
			LinearLayout bnrow1 = new LinearLayout(this);
			LinearLayout brow1 = new LinearLayout(this);
			team1.setOrientation(LinearLayout.VERTICAL);
			scores1.setOrientation(LinearLayout.VERTICAL);
			trow1.setOrientation(LinearLayout.HORIZONTAL);
			brow1.setOrientation(LinearLayout.HORIZONTAL);
			teams.addView(team1,params);
			scores.addView(scores1,params);
			tNames.addView(tnrow1,params);
			tbuttons.addView(trow1,params);
			bNames.addView(bnrow1,params);
			bbuttons.addView(brow1,params);
			trow1.setGravity(Gravity.CENTER);
			tnrow1.setGravity(Gravity.CENTER);
			brow1.setGravity(Gravity.CENTER);
			bnrow1.setGravity(Gravity.CENTER);
			ScoreCounter score = new ScoreCounter(0,1,sb.getDigits(),this);
			TextView name1 = new TextView(this);
			name1.setTextColor(Color.RED);
			name1.setTextSize(25);
			score.setBackgroundColor(Color.YELLOW);
			score.setWidth(150);
			score.setHeight(150);
			score.setTextSize(50);
			name1.setText(teamNames[0]);
			team1.addView(name1,params);
			scores1.addView(score,params);
			char tbuttons1[] = sb.getTopButtons();
			String tnames1[] = sb.gettNames();
			for(int i = 0; i < sb.getTCount(); i++){
				switch(tbuttons1[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames1[i]);
					TextView name = new TextView(this);
					name.setTextColor(Color.RED);
					name.setText(sp.getName());
					sp.setId(i + 10);
					name.setId(i + 14);
					trow1.addView(sp,bparams);
					tnrow1.addView(name,bparams);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons1[] = sb.getBottomButtons();
				String bnames1[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					switch(bbuttons1[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames1[i]);
						TextView name = new TextView(this);
						name.setTextColor(Color.RED);
						name.setText(sp.getName());
						sp.setId(i + 100);
						name.setId(i + 104);
						brow1.addView(sp,bparams);
						bnrow1.addView(name,bparams);
						break;
					}
				}
			}
		}
		if(sb.isHasNeutral()){
			
		}
		
		
		/**
		whistle = (Button) this.findViewById(R.id.Whistle);

		
		whistle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//whistle.setText("You Clicked Me");
				//setContentView(R.layout.tototo);
					
				}});
		*/

		
	}
	

	public void initializeScoreboard(){
		String names[] = {"team1", "team2", "team3", "team4"};
		String tnames[] = {"tc1", "tc2"};
		String bnames[] = {"tc3", "tc3"};
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
		sb.setTeams(4);
		sb.setDigits(2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoreboard_ui, menu);
		return true;
	}
	
	
	

}
