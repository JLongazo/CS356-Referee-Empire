package com.example.cs356;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import java.io.*;

public class ScoreboardUI extends Activity {
	
	Button whistle;
	private Button save;
	private Scoreboard sb;
	private ContinueData cd;
	private TextView name;
	private LinearLayout teams;
	private LinearLayout scores;
	private LinearLayout tbuttons;
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
		save = (Button) this.findViewById(R.id.button1);
		name = (TextView) this.findViewById(R.id.textView1);
		teams = (LinearLayout) this.findViewById(R.id.Teams);
		scores = (LinearLayout) this.findViewById(R.id.Scores);
		tbuttons = (LinearLayout) this.findViewById(R.id.Tbuttons);
		bbuttons = (LinearLayout) this.findViewById(R.id.Bbuttons);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		params.weight = 1;
		LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		bparams.gravity = Gravity.CENTER;
		//get scoreboard file and continue data
		Bundle extras = getIntent().getExtras();
		String cont = "l";
		//String cont = extras.getString("CONT");
		//String file = extras.getString("FILE");
		boolean contin = false;
		if(cont.equals("true")){
			//load continue data
			cd = new ContinueData();
			contin = true;
		}
		sb = new Scoreboard(); //load scoreboard
		initializeScoreboard();
		name.setText(sb.getName());
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		String teamNames[] = sb.getTeamNames();
		for(int j = 0; j < teamCount; j++){
			LinearLayout team = new LinearLayout(this);
			team.setId(id++);
			LinearLayout score = new LinearLayout(this);
			score.setId(id++);
			LinearLayout trow = new LinearLayout(this);
			trow.setId(id++);
			LinearLayout brow = new LinearLayout(this);
			brow.setId(id++);
			team.setOrientation(LinearLayout.VERTICAL);
			score.setOrientation(LinearLayout.VERTICAL);
			teams.addView(team,params);
			scores.addView(score,params);
			tbuttons.addView(trow,params);
			if(!sb.isHasNeutral()){
				bbuttons.addView(brow,params);
			}
			trow.setGravity(Gravity.CENTER);
			brow.setGravity(Gravity.CENTER);
			ScoreCounter scoreC = new ScoreCounter(0,1,sb.getDigits(),this);
			if(contin){
				scoreC.setInitial(cd.getScore(j));
			}
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
				LinearLayout tbutton = new LinearLayout(this);
				tbutton.setId(id++);
				tbutton.setOrientation(LinearLayout.VERTICAL);
				tbutton.setGravity(Gravity.CENTER);
				trow.addView(tbutton,params);
				switch(tbuttons[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,tnames[i]);
					if(contin){
						sp.setCount(Integer.parseInt(cd.getTButton(i+j)));
					}
					TextView name2 = new TextView(this);
					name2.setText(sp.getName());
				//	name2.setTextColor(Color.BLACK);
					tbutid[i+j]=id;
					sp.setId(id++);
					name2.setId(id++);
					tbutton.addView(name2,bparams);
					tbutton.addView(sp,bparams);
		//			break;
				case 't':
					RToggle rt = new RToggle(this,tnames[i]);
					TextView name3 = new TextView(this);
					name3.setText(rt.getName());
			//		name3.setTextColor(Color.BLACK);
					tbutid[i+j]=id;
					rt.setId(id++);
					name3.setId(id++);
					trow.addView(rt,bparams);
					tbutton.addView(name3,bparams);
					rt.setIsOn(false);
					rt.changeToggle();
					break;
				}
			}
			if(!sb.isHasNeutral()){
				char bbuttons[] = sb.getBottomButtons();
				String bnames[] = sb.getbNames();
				for(int i = 0; i < sb.getBCount(); i++){
					LinearLayout bbutton = new LinearLayout(this);
					bbutton.setId(id++);
					bbutton.setOrientation(LinearLayout.VERTICAL);
					bbutton.setGravity(Gravity.CENTER);
					brow.addView(bbutton,params);
					switch(bbuttons[i]){
					case 'c':
						SpecialCounter sp = new SpecialCounter(this,bnames[i]);
						if(contin){
							sp.setCount(Integer.parseInt(cd.getTButton(i+j)));
						}
						TextView name2 = new TextView(this);
						name2.setText(sp.getName());
						bbutid[i+j]=id;
						sp.setId(id++);
						name2.setId(id++);
						bbutton.addView(name2,bparams);
						bbutton.addView(sp,bparams);
						break;
					}
				}
			}
		}
		if(sb.isHasNeutral()){
			char nbuttons[] = sb.getBottomButtons();
			String nnames[] = sb.getbNames();
			for(int i = 0; i < sb.getBCount(); i++){
				LinearLayout nbutton = new LinearLayout(this);//neutral button
				nbutton.setId(id++);
				nbutton.setOrientation(LinearLayout.HORIZONTAL);
				nbutton.setGravity(Gravity.CENTER);
				bbuttons.addView(nbutton,params);
				switch(nbuttons[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,nnames[i]);
					if(contin){
						sp.setCount(Integer.parseInt(cd.getTButton(i)));
					}
					TextView name2 = new TextView(this);
					name2.setText(sp.getName());
					bbutid[i]=id;
					sp.setId(id++);
					name2.setId(id++);
					nbutton.addView(name2,bparams);
					nbutton.addView(sp,bparams);
			//		break;
				case 'd':
					DiceRoll dr = new DiceRoll(this, nnames[i]);
		/*			if(contin){
						sp.setCount(Integer.parseInt(cd.getTButton(i)));
					}
					*/
					TextView name3 = new TextView(this);
					name3.setText(dr.getName());
					bbutid[i]=id;
					dr.setId(id++);
					name3.setId(id++);
					nbutton.addView(name3,bparams);
					nbutton.addView(dr,bparams);
					dr.setWidth(50);
				case 'e':
					CoinToss ct = new CoinToss(this, nnames[i]);
		/*			if(contin){
						sp.setCount(Integer.parseInt(cd.getTButton(i)));
					}
					*/
					TextView name4 = new TextView(this);
					name4.setText(ct.getName());
					bbutid[i]=id;
					ct.setId(id++);
					name4.setId(id++);
					nbutton.addView(name4,bparams);
					nbutton.addView(ct,bparams);
					ct.setWidth(70);
					break;
				}
			
		}
	}
		
		
		/**
		whistle = (Button) this.findViewById(R.id.Whistle);

		
		whistle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//whistle.setText("You Clicked Me");
				//setContentView(R.layout.tototo);
					
				}});
		*/
		
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					FileOutputStream fos = new FileOutputStream("fafae");
					ObjectOutputStream os = new ObjectOutputStream(fos);
					os.writeObject(sb);
					os.flush();
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("nope");
					e.printStackTrace();
				}
				
			}
		});
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
		sb.setHasNeutral(true);
		sb.setTeamNames(names);
		sb.setTeams(4);
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
