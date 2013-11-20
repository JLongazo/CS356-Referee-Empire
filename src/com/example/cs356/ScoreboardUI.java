package com.example.cs356;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
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
	private Button rules;
	private Button options;
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
	private int timerCount = 0;
	private Typeface f1;
	private Typeface f2;
	

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
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		end = (Button) this.findViewById(R.id.end);
		home = (Button) this.findViewById(R.id.home);
		rules = (Button) this.findViewById(R.id.rules);
		options = (Button) this.findViewById(R.id.options2);
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
		f1 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		f2 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
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
		}

		else if (type.equals("savedboard")) {
			
			try {
			InputStream is = getResources().openRawResource(ScoreboardList.getFileInt());
            ObjectInputStream ois = new ObjectInputStream(is); 
            sb = (Scoreboard) ois.readObject();
			}
			
			catch(Exception e){
				Log.v("Serialization Read Error : ",e.getMessage());
			}
		}
		
		
		
		else{
			initializeScoreboard();//load new scoreboard
		}
		name.setText(sb.getName());
		name.setTypeface(f2);
		name.setTextSize(30);
		name.setTextColor(Color.WHITE);
		name.setGravity(Gravity.CENTER);
		LayoutParams counter = new LayoutParams(100,100);
		LayoutParams toggle = new LayoutParams(100,100);
		LayoutParams timerP = new LayoutParams(350,100);
		LayoutParams diceP = new LayoutParams(100,100);
		LayoutParams coinP = new LayoutParams(100,100);
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		String teamNames[] = sb.getTeamNames();
		int tcount = 0;
		int bcount = 0;
		for(int j = 0; j < teamCount; j++){
			int background = 0;
			switch(j+1){
			case 1:
				background = R.drawable.background1;
				break;
			case 2:
				background = R.drawable.background2;
				break;
			case 3:
				background = R.drawable.background3;
				break;
			case 4:
				background = R.drawable.background4;
				break;
			}
			LinearLayout team = new LinearLayout(this);
			team.setId(id++);
			team.setBackgroundResource(background);
			LinearLayout score = new LinearLayout(this);
			score.setId(id++);
			score.setBackgroundResource(background);
			LinearLayout trow = new LinearLayout(this);
			trow.setId(id++);
			trow.setBackgroundResource(background);
			LinearLayout brow = new LinearLayout(this);
			brow.setId(id++);
			if(sb.isHasNeutral()){
				brow.setBackgroundResource(background);
			}
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
			name1.setTypeface(f2);
			team.setGravity(Gravity.CENTER);
			name1.setWidth(width/teamCount);
			name1.setGravity(Gravity.CENTER);
			teamid[j]=id;
			name1.setId(id++);
			String name = teamNames[j];
			name1.setText(name);
			name1.setTextColor(Color.WHITE);
            name1.setTextSize(25);
            int dcount = 70 * sb.getDigits();
            LinearLayout.LayoutParams scoreCount = new LinearLayout.LayoutParams(dcount, 125);
            scoreCount.setMargins(1,1,1,1);
            scoreCount.gravity = Gravity.CENTER;
            scoreC.setLayoutParams(scoreCount);
            switch(sb.getDigits()){
            case 2:
            	scoreC.setBackgroundResource(R.drawable.counter2);
            	break;
            case 3:
            	scoreC.setBackgroundResource(R.drawable.counter3);
            	break;
            case 4:
            	scoreC.setBackgroundResource(R.drawable.counter4);
            	break;
            }
			team.addView(name1,params);
			score.addView(scoreC);
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
					sp.setBackgroundResource(R.drawable.counter2);
					sp.setLayoutParams(counter);
					//sp.setTypeface(f1);
					TextView name2 = new TextView(this);
					name2.setText(sp.getName().toUpperCase());
					name2.setTypeface(f1);
					tbutid[tcount++]=id;
					sp.setId(id++);
					name2.setId(id++);
					name2.setTextColor(Color.WHITE);
					tbutton.addView(name2,bparams);
					tbutton.addView(sp);
					break;
				
				case 't':
					RToggle tog = new RToggle(this,tnames[i]);
					if(contin){
						tog.setIsOn(Boolean.parseBoolean(cd.getTButton(tcount)));
					}
					tog.setLayoutParams(toggle);
					TextView name3 = new TextView(this);
					name3.setText(tog.getName().toUpperCase());
					name3.setTypeface(f1);
					tbutid[tcount++]=id;
					tog.setId(id++);
					name3.setId(id++);
					name3.setTextColor(Color.WHITE);
					tbutton.addView(name3,bparams);
					tbutton.addView(tog);
					break;
				case 'm':
					long start = sb.getTimerTime(timerCount);
					boolean tType = sb.getTimerType(timerCount++);
					RefereeTimer time = new RefereeTimer(this,start,tType);
					if(contin){
						time.setMillis(Long.parseLong(cd.getTButton(tcount)));
					}
					time.setLayoutParams(timerP);
					//time.setTypeface(f1);
					tbutid[tcount++]=id;
					time.setId(id++);
					time.setBackgroundResource(R.drawable.timer);
					tbutton.addView(time);
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
						sp.setBackgroundResource(R.drawable.counter2);
						sp.setLayoutParams(counter);
						//sp.setTypeface(f1);
						TextView name2 = new TextView(this);
						name2.setText(sp.getName().toUpperCase());
						name2.setTypeface(f1);
						bbutid[bcount++]=id;
						sp.setId(id++);
						name2.setId(id++);
						name2.setTextColor(Color.WHITE);
						bbutton.addView(name2,bparams);
						bbutton.addView(sp);
						break;
					case 't':
						RToggle tog = new RToggle(this,bnames[i]);
						if(contin){
							tog.setIsOn(Boolean.parseBoolean(cd.getBButton(bcount)));
						}
						tog.setLayoutParams(toggle);
						TextView name3 = new TextView(this);
						name3.setText(tog.getName().toUpperCase());
						name3.setTypeface(f1);
						bbutid[bcount++]=id;
						tog.setId(id++);
						name3.setId(id++);
						name3.setTextColor(Color.WHITE);
						bbutton.addView(name3,bparams);
						bbutton.addView(tog);
						break;
					case 'm':
						long start = sb.getTimerTime(timerCount);
						boolean tType = sb.getTimerType(timerCount++);
						RefereeTimer time = new RefereeTimer(this,start,tType);
						if(contin){
							time.setMillis(Long.parseLong(cd.getBButton(bcount)));
						}
						time.setLayoutParams(timerP);
						time.setBackgroundResource(R.drawable.timer);
						bbutid[bcount++]=id;
						time.setId(id++);
						bbutton.addView(time);
						break;
					}
				}
			}
		}
		if(sb.isHasNeutral()){
			char nbuttons[] = sb.getBottomButtons();
			String nnames[] = sb.getbNames();
			bbuttons.setBackgroundResource(R.drawable.background5);
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
					sp.setBackgroundResource(R.drawable.counter2);
					sp.setLayoutParams(counter);
					//sp.setTypeface(f1);
					TextView name2 = new TextView(this);
					name2.setText(sp.getName().toUpperCase());
					name2.setTypeface(f1);
					bbutid[i]=id;
					sp.setId(id++);
					name2.setId(id++);
					name2.setTextColor(Color.WHITE);
					nbutton.addView(name2,bparams);
					nbutton.addView(sp);
					break;
				case 't':
					RToggle tog = new RToggle(this,nnames[i]);
					if(contin){
						tog.setIsOn(Boolean.parseBoolean(cd.getBButton(i)));
					}
					tog.setLayoutParams(toggle);
					TextView name3 = new TextView(this);
					name3.setText(tog.getName().toUpperCase());
					name3.setTypeface(f1);
					bbutid[i]=id;
					tog.setId(id++);
					name3.setId(id++);
					name3.setTextColor(Color.WHITE);
					nbutton.addView(name3,bparams);
					nbutton.addView(tog);
					break;
				case 'm':
					long start = sb.getTimerTime(timerCount);
					boolean tType = sb.getTimerType(timerCount++);
					RefereeTimer time = new RefereeTimer(this,start,tType);
					if(contin){
						time.setMillis(Long.parseLong(cd.getBButton(i)));
					}
					time.setLayoutParams(timerP);
					time.setBackgroundResource(R.drawable.timer);
					bbutid[i]=id;
					time.setId(id++);
					nbutton.addView(time);
					break;
				case 'd':
					DiceRoll dice = new DiceRoll(this, "");
					bbutid[i]=id;
					dice.setId(id++);
					dice.setLayoutParams(diceP);
					nbutton.addView(dice);
					break;
				case 'f':
					CoinToss coin = new CoinToss(this, "");
					bbutid[i]=id;
					coin.setId(id++);
					coin.setLayoutParams(coinP);
					nbutton.addView(coin);
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
		
		rules.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.RuleSheet.class);
				saveContinue();
				startActivity(myIntent);
			}
		});
		
		options.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//savePremade();
				Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.GameOptions.class);
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
		String teams[] = sb.getTeamNames();
		String name = sb.getName();
		int scores[] = new int[sb.getTeams()];
		for(int i = 0; i < sb.getTeams(); i++){
			ScoreCounter sc = (ScoreCounter) this.findViewById(scoreid[i]);
			scores[i] = sc.getScore();
		}
		ScoreData newScore = new ScoreData(name, teams, scores);
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
		s.add(newScore);
		try 
        { 
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/scores.bin"))); 
           //Select where you wish to save the file... 
           oos.writeObject(s); // write the class as an 'object' 
           oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin' 
           oos.close();// close the stream 
        } 
        catch(Exception ex) 
        { 
           Log.v("Serialization Save Error : ",ex.getMessage()); 
           ex.printStackTrace(); 
        }
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
				case 'm':
					RefereeTimer m = (RefereeTimer) this.findViewById(tbutid[tcount]);
					String type = Boolean.toString(m.getCountUp());
					String time = Long.toString(m.getMillis());
					String save = type + "/" + time;
					cd.setTButton(save,tcount++);
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
					case 'm':
						RefereeTimer m = (RefereeTimer) this.findViewById(bbutid[bcount]);
						String type = Boolean.toString(m.getCountUp());
						String time = Long.toString(m.getMillis());
						String save = type + "/" + time;
						cd.setBButton(save,bcount++);
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
				case 'm':
					RefereeTimer m = (RefereeTimer) this.findViewById(bbutid[j]);
					String time = Long.toString(m.getMillis());
					cd.setBButton(time,j);
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
	
	public void savePremade(){
		ScoreboardData sd;
		String name = sb.getName().toLowerCase();
		try 
        { 
           ObjectOutputStream oos = new ObjectOutputStream(
        		   new FileOutputStream(new File("/data/data/com.example.cs356/" + name + ".bin"))); 
           oos.writeObject(sb); 
           oos.flush(); 
           oos.close();
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scoreboards.bin")); 
           sd = (ScoreboardData) ois.readObject();
           sd.addSb(sb.getName());
           oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/scoreboards.bin")));
           oos.writeObject(sd);
	       oos.flush();  
	       oos.close();
        } 
        catch(Exception ex) 
        { 
        	try{
        		String start[] = {sb.getName()};
	        	sd = new ScoreboardData(start);
	            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/scoreboards.bin")));
	            oos.writeObject(sd);
	 	       	oos.flush();  
	 	      	oos.close();	
        	}
        	catch(Exception e2){
        		Log.v("Serialization Save Error : ",ex.getMessage()); 
	 	      	ex.printStackTrace(); 
        	}
        }
	}

	public void initializeScoreboard(){
		String names[] = {"Joe", "team2", "team3", "team4"};
		String tnames[] = {"tc1", "tc2"};
		String bnames[] = {"tc3", "tc4",""};
		String rules[] = {"Dont cheat", "Follow these Rules", "Blah", "Blah, blah"};
		char trow[] = {'c','t'};
		char brow[] = {'c','t','m'};
		long times[] = {50000};
		boolean types[] = {true};
		sb.setTopButtons(trow);
		sb.setBottomButtons(brow);
		sb.setTCount(2);
		sb.setBCount(3);
		sb.settNames(tnames);
		sb.setbNames(bnames);
		sb.setHasNeutral(true);
		sb.setTeamNames(names);
		sb.setTimerTimes(times);
		sb.setTimerTypes(types);
		sb.setRules(rules);
		sb.setTeams(4);
		sb.setDigits(3);
		sb.setName("Generic");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoreboard_ui, menu);
		return true;
	}
	
	
	

}
