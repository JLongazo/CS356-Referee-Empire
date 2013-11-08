package com.example.cs356;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
import java.io.*;

public class ScoreboardUI extends Activity {
	
	Button whistle;
	private boolean endgame = false;
	private Button end;
	private Button home;
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
		end = (Button) this.findViewById(R.id.resume);
		home = (Button) this.findViewById(R.id.select);
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
		String file = extras.getString("FILE");
		String type = extras.getString("TYPE");
		boolean contin = false;
		sb = new Scoreboard();
		if(type.equals("continue")){
			//load continue data
			try 
	        { 
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)); 
	            cd = (ContinueData) ois.readObject();  
	        } 
			catch(Exception e){
				Log.v("Serialization Read Error : ",e.getMessage());
			}
			sb = cd.getSb();
			contin = true;
		}else{
			initializeScoreboard();//load new scoreboard
		}
		name.setText(sb.getName());
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		String teamNames[] = sb.getTeamNames();
		int tcount = 0;
		int bcount = 0;
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
				scoreC.setScore(cd.getScore(j));
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
						sp.setCount(Integer.parseInt(cd.getTButton(tcount)));
					}
					TextView name2 = new TextView(this);
					name2.setText(sp.getName());
					tbutid[tcount++]=id;
					sp.setId(id++);
					name2.setId(id++);
					tbutton.addView(name2,bparams);
					tbutton.addView(sp,bparams);
					break;
				
				case 't':
					RToggle tog = new RToggle(this,tnames[i]);
					if(contin){
						tog.setIsOn(Boolean.parseBoolean(cd.getTButton(tcount)));
					}
					TextView name3 = new TextView(this);
					name3.setText(tog.getName());
					tbutid[tcount++]=id;
					tog.setId(id++);
					name3.setId(id++);
					tbutton.addView(name3,bparams);
					tbutton.addView(tog,bparams);
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
							sp.setCount(Integer.parseInt(cd.getTButton(bcount)));
						}
						TextView name2 = new TextView(this);
						name2.setText(sp.getName());
						bbutid[bcount++]=id;
						sp.setId(id++);
						name2.setId(id++);
						bbutton.addView(name2,bparams);
						bbutton.addView(sp,bparams);
						break;
					case 't':
						RToggle tog = new RToggle(this,bnames[i]);
						if(contin){
							tog.setIsOn(Boolean.parseBoolean(cd.getBButton(bcount)));
						}
						TextView name3 = new TextView(this);
						name3.setText(tog.getName());
						bbutid[bcount++]=id;
						tog.setId(id++);
						name3.setId(id++);
						bbutton.addView(name3,bparams);
						bbutton.addView(tog,bparams);
						break;
					}
				}
			}
		}
		if(sb.isHasNeutral()){
			char nbuttons[] = sb.getBottomButtons();
			String nnames[] = sb.getbNames();
			for(int i = 0; i < sb.getBCount(); i++){
				LinearLayout nbutton = new LinearLayout(this);
				nbutton.setId(id++);
				nbutton.setOrientation(LinearLayout.VERTICAL);
				nbutton.setGravity(Gravity.CENTER);
				bbuttons.addView(nbutton,params);
				switch(nbuttons[i]){
				case 'c':
					SpecialCounter sp = new SpecialCounter(this,nnames[i]);
					if(contin){
						sp.setCount(Integer.parseInt(cd.getBButton(i)));
					}
					TextView name2 = new TextView(this);
					name2.setText(sp.getName());
					bbutid[i]=id;
					sp.setId(id++);
					name2.setId(id++);
					nbutton.addView(name2,bparams);
					nbutton.addView(sp,bparams);
					break;
				case 't':
					RToggle tog = new RToggle(this,nnames[i]);
					if(contin){
						tog.setIsOn(Boolean.parseBoolean(cd.getBButton(i)));
					}
					TextView name3 = new TextView(this);
					name3.setText(tog.getName());
					bbutid[i]=id;
					tog.setId(id++);
					name3.setId(id++);
					nbutton.addView(name3,bparams);
					nbutton.addView(tog,bparams);
					break;
				case 'd':
					DiceRoll dice = new DiceRoll(this, "");
					bbutid[i]=id;
					dice.setId(id++);
					nbutton.addView(dice,bparams);
					break;
				case 'f':
					CoinToss coin = new CoinToss(this, "");
					bbutid[i]=id;
					coin.setId(id++);
					nbutton.addView(coin,bparams);
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
		
		end.setOnClickListener(new OnClickListener() {
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
					e.printStackTrace();
				}
				saveGame();
				resetContinue();
				endgame = true;
				Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.MainActivity.class);
				startActivity(myIntent);
			}
		});
		
		home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.MainActivity.class);
				saveContinue();
				startActivity(myIntent);
			}
		});
		
		
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(!endgame){
			saveContinue();
		}
	}
	
	public void saveGame(){
		
	}
	
	public void resetContinue(){
		ContinueData cd = new ContinueData();
		cd.setCheck(false);
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
	}
	
	public void saveContinue(){
		ContinueData cd = new ContinueData();
		cd.setSb(sb);
		cd.setCheck(true);
		int tcount = 0;
		int bcount = 0;
		for(int i = 0; i < sb.getTeams(); i++){
			ScoreCounter sc = (ScoreCounter) this.findViewById(scoreid[i]);
			cd.setScore(sc.getScore(),i);
			for(int j = 0; j < sb.getTCount(); j++){
				char check[] = sb.getTopButtons();
				switch(check[j]){
				case 'c':
					SpecialCounter c = (SpecialCounter) this.findViewById(tbutid[tcount]);
					cd.setTButton(Integer.toString(c.getSpScore()), tcount++);
					break;
				case 't':
					RToggle t = (RToggle) this.findViewById(tbutid[tcount]);
					cd.setTButton(Boolean.toString(t.getIsOn()), tcount++);
					break;
				}
			}
			if(!sb.isHasNeutral()){
				for(int j = 0; j < sb.getBCount(); j++){
					char check[] = sb.getBottomButtons();
					switch(check[j]){
					case 'c':
						SpecialCounter c = (SpecialCounter) this.findViewById(bbutid[bcount]);
						cd.setBButton(Integer.toString(c.getSpScore()), bcount++);
						break;
					case 't':
						RToggle t = (RToggle) this.findViewById(bbutid[bcount]);
						cd.setBButton(Boolean.toString(t.getIsOn()), bcount++);
						break;
					}
				}
			
			}
		}
		if(sb.isHasNeutral()){
			for(int j = 0; j < sb.getBCount(); j++){
				char check[] = sb.getBottomButtons();
				switch(check[j]){
				case 'c':
					SpecialCounter c = (SpecialCounter) this.findViewById(bbutid[j]);
					cd.setBButton(Integer.toString(c.getSpScore()), j);
					break;
				case 't':
					RToggle t = (RToggle) this.findViewById(bbutid[j]);
					cd.setBButton(Boolean.toString(t.getIsOn()), j);
					break;
				}
			}
		}
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
	}

	public void initializeScoreboard(){
		String names[] = {"team1", "team2", "team3", "team4"};
		String tnames[] = {"tc1", "tc2"};
		String bnames[] = {"tc3", "tc4"};
		char trow[] = {'c','t'};
		char brow[] = {'c','t','f','d'};
		sb.setTopButtons(trow);
		sb.setBottomButtons(brow);
		sb.setTCount(2);
		sb.setBCount(4);
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
