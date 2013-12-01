package com.example.cs356;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardUI extends Activity {
	
	static int SCORE_DIGIT = 140;
	static int SCORE_H = 250;
	static int BUTTON_H = 180;
	static int BUTTON_W = 180;
	static int TIMER_W = 720;
	
	private boolean endgame = false;
	private Button end;
	private Button home;
	private Button rules;
	private Button options;
	private Button round;
	private Button bet;
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
	private String teamNames[];
	private int timerCount = 0;
	private Typeface f1;
	private Typeface f2;
	private int height;
	private int width;
	private int wins[] = new int[]{0,0,0,0};
	private boolean contin;
	private RoundData rounds;
	private int betNum = 0;
	private EditText et;
	private ArrayList<String> roundData = new ArrayList<String>();
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard_ui);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		end = (Button) this.findViewById(R.id.end);
		home = (Button) this.findViewById(R.id.home);
		rules = (Button) this.findViewById(R.id.rules);
		round = (Button) this.findViewById(R.id.rounds);
		bet = (Button) this.findViewById(R.id.bet);
		options = (Button) this.findViewById(R.id.options2);
		name = (TextView) this.findViewById(R.id.textView1);
		teams = (LinearLayout) this.findViewById(R.id.Teams);
		scores = (LinearLayout) this.findViewById(R.id.Scores);
		tbuttons = (LinearLayout) this.findViewById(R.id.Tbuttons);
		bbuttons = (LinearLayout) this.findViewById(R.id.Bbuttons);
		Bundle extras = getIntent().getExtras();
		String file = extras.getString("FILE");
		type = extras.getString("TYPE");
		contin = false;
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
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));  
				sb = (Scoreboard) ois.readObject();
			}
			
			catch(Exception e){
				Log.v("Serialization Read Error : ",e.getMessage());
			}
		}
		
		else if (type.equals("tournament")) {
					
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));  
				sb = (Scoreboard) ois.readObject();
			}
					
			catch(Exception e){
				Log.v("Serialization Read Error : ",e.getMessage());
			}
			String teams = extras.getString("TEAMS");
			String tTeams[] = teams.split(",");
			sb.setTeamNames(tTeams);
			round.setEnabled(false);
			bet.setEnabled(false);
		}	
		
		
		
		else{
			initializeScoreboard();//load new scoreboard
		}
		rounds = new RoundData();
		build();
		
		round.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CharSequence teams[] = teamNames;
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
				builder.setTitle("CHOSE ROUND WINNER");
				builder.setItems(teams, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		                   String winner = rounds.getRoundCount() + ". " + teamNames[which] + " - $" + betNum;
		                   saveScore();
		                   rounds.nextRound(winner);
		                   wins[which]++;
		                   round.setText("ROUND: " + rounds.getRoundCount());
		                   reset();
		               }
		        });
				builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
			}
		});
		
		round.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				CharSequence data[] = new CharSequence[rounds.getRoundCount()-1];
				data = rounds.getData().toArray(data);
				if(data.length == 0){
					data[0] = "None";
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
				builder.setTitle("ROUND WINNERS");
				builder.setItems(data, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		               }
		        });
				builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
				return true;
			}
		});
		
		bet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
				builder.setTitle("SET BET AMOUNT");
				builder.setMessage("Enter a number less than 4 digits.");
				et = new EditText(ScoreboardUI.this);
				et.setInputType(InputType.TYPE_CLASS_NUMBER);
				builder.setView(et);
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   int check = Integer.parseInt(et.getText().toString());
	                	   if(check <= 999){
	                		   betNum = check;
	                		   bet.setText("BET: $" + check);
	                	   }else{
	                		   Toast.makeText(ScoreboardUI.this, "Invalid Entry!", Toast.LENGTH_LONG).show();
	                	   }  
		               }
					});
				builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
			}
		});
		
		end.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardUI.this, R.raw.click);
				mp.start();
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
				builder.setTitle("END GAME AND SAVE SCORES?");
				builder.setMessage("You can no longer continue the " +
						"game afterwards.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   if(type.equals("tournament")){
	                		    CharSequence teams[] = teamNames;
	                		   	final Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.Tournament.class);
	                		   	AlertDialog.Builder builder2 = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
		       					builder2.setTitle("CHOSE ROUND WINNER");
		       					builder2.setItems(teams, new DialogInterface.OnClickListener() {
		       			               public void onClick(DialogInterface dialog, int which) {
		       			                   String winner = rounds.getRoundCount() + ". " + teamNames[which] + " - $" + betNum;
		       			                   myIntent.putExtra("WIN",winner);
		    			                   startActivity(myIntent);
		       			               }
		       			        });
		       					builder2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		       		                   public void onClick(DialogInterface dialog, int id) {
		       		                      //dialog dismissed
		       		                   }
		       					});
		       					builder2.create();
		       					builder2.show();
	                	   }else {
		       					resetContinue();
		       					endgame = true;
		       					CharSequence teams[] = teamNames;
		       					AlertDialog.Builder builder2 = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
		       					builder2.setTitle("CHOSE ROUND WINNER");
		       					builder2.setItems(teams, new DialogInterface.OnClickListener() {
		       			               public void onClick(DialogInterface dialog, int which) {
		       			                   String winner = rounds.getRoundCount() + ". " + teamNames[which] + " - $" + betNum;
		       			                   wins[which]++;
		       			                   saveGame();
		       			                   Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.MainActivity.class);
		       			                   startActivity(myIntent);
		       			               }
		       			        });
		       					builder2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		       		                   public void onClick(DialogInterface dialog, int id) {
		       		                      //dialog dismissed
		       		                   }
		       					});
		       					builder2.create();
		       					builder2.show();
	                	   }
		               }
					});
				builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
			}
		});
		
		home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardUI.this, R.raw.click);
				mp.start();
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ScoreboardUI.this, R.style.RefStyle));
				builder.setTitle("EXIT TO MAIN MENU?");
				builder.setMessage("This score board will save for later.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.MainActivity.class);
	       					saveContinue();
	       					startActivity(myIntent);
		               }
					});
				builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      //dialog dismissed
	                   }
				});
				builder.create();
				builder.show();
			}
		});
		
		rules.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardUI.this, R.raw.click);
				mp.start();
				Intent myIntent = new Intent(ScoreboardUI.this, com.example.cs356.RuleSheet.class);
				saveContinue();
				startActivity(myIntent);
			}
		});
		
		options.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(ScoreboardUI.this, R.raw.click);
				mp.start();
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
	
	public void saveScore(){
		String data = rounds.getRoundCount() + ". ";
		for(int i = 0; i < sb.getTeams(); i++){
			ScoreCounter sc = (ScoreCounter) this.findViewById(scoreid[i]);
			int score = sc.getScore();
			data += teamNames[i] + " - " + score + "  ";
		}
		roundData.add(data);
	}
	
	public void saveGame(){
		String teams[] = sb.getTeamNames();
		String name = sb.getName();
		String details[] = new String[rounds.getRoundCount()];
		saveScore();
		details = roundData.toArray(details);
		ScoreData newScore = new ScoreData(name, teams, wins, details);
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
	
	public void reset(){
		int bcount = 0;
		int tcount = 0;
		for(int i = 0; i < sb.getTeams(); i++){
			ScoreCounter sc = (ScoreCounter) this.findViewById(scoreid[i]);
			sc.setScore(0);
			for(int j = 0; j < sb.getTCount(); j++){
				char check[] = sb.getTopButtons();
				switch(check[j]){
				case 'c':
					SpecialCounter c = (SpecialCounter) this.findViewById(tbutid[tcount]);
					c.setCount(0);
					break;
				case 't':
					RToggle t = (RToggle) this.findViewById(tbutid[tcount]);
					t.setIsOn(false);
					break;
				case 'm':
					RefereeTimer m = (RefereeTimer) this.findViewById(tbutid[tcount]);
					m.reset();
					break;
				}
				tcount++;
			}
			if(!sb.isHasNeutral()){
				for(int j = 0; j < sb.getBCount(); j++){
					char check[] = sb.getBottomButtons();
					switch(check[j]){
					case 'c':
						SpecialCounter c = (SpecialCounter) this.findViewById(bbutid[bcount]);
						c.setCount(0);
						break;
					case 't':
						RToggle t = (RToggle) this.findViewById(bbutid[bcount]);
						t.setIsOn(false);
						break;
					case 'm':
						RefereeTimer m = (RefereeTimer) this.findViewById(bbutid[bcount]);
						m.reset();
						break;
					}
					bcount++;
				}
			}
		}
		if(sb.isHasNeutral()){
			for(int j = 0; j < sb.getBCount(); j++){
				char check[] = sb.getBottomButtons();
				switch(check[j]){
				case 'c':
					SpecialCounter c = (SpecialCounter) this.findViewById(bbutid[j]);
					c.setCount(0);
					break;
				case 't':
					RToggle t = (RToggle) this.findViewById(bbutid[j]);
					t.setIsOn(false);
					break;
				case 'm':
					RefereeTimer m = (RefereeTimer) this.findViewById(bbutid[j]);
					m.reset();
					break;
				}
			}
		}
	}
	
	public void build(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		params.weight = 1;
		LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		bparams.gravity = Gravity.CENTER;
		if(contin){
			betNum = cd.getBet();
			rounds = cd.getRounds();
		}
		f1 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		f2 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
		round.setText("ROUND: " + rounds.getRoundCount());
		round.setTypeface(f2);
		round.setTextSize(15);
		round.setTextColor(Color.WHITE);
		round.setGravity(Gravity.CENTER);
		bet.setText("BET: $" + betNum);
		bet.setTypeface(f2);
		bet.setTextSize(15);
		bet.setTextColor(Color.WHITE);
		bet.setGravity(Gravity.CENTER);
		name.setText(sb.getName());
		name.setTypeface(f2);
		name.setTextSize(26);
		name.setTextColor(Color.WHITE);
		name.setGravity(Gravity.CENTER);
		
		final float scale = this.getResources().getDisplayMetrics().density;
		//int pixels = (int) (dps * scale + 0.5f);
		LayoutParams counter = new LayoutParams(pxtodp(BUTTON_W),pxtodp(BUTTON_H));
		LayoutParams toggle = new LayoutParams(pxtodp(BUTTON_W),pxtodp(BUTTON_H));
		LayoutParams timerP = new LayoutParams(pxtodp(TIMER_W), pxtodp(BUTTON_H));
		LayoutParams diceP = new LayoutParams(pxtodp(BUTTON_W),pxtodp(BUTTON_H));
		LayoutParams coinP = new LayoutParams(pxtodp(BUTTON_W),pxtodp(BUTTON_H));
		//Build Scoreboard Sequence
		int teamCount = sb.getTeams();
		teamNames = sb.getTeamNames();
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
            int dcount = pxtodp(SCORE_DIGIT) * sb.getDigits();
            LinearLayout.LayoutParams scoreCount = new LinearLayout.LayoutParams(dcount, pxtodp(SCORE_H));
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
				case 'd':
					DiceRoll dice = new DiceRoll(this, "");
					tbutid[i]=id;
					dice.setId(id++);
					dice.setLayoutParams(diceP);
					tbutton.addView(dice);
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
					case 'd':
						DiceRoll dice = new DiceRoll(this, "");
						bbutid[i]=id;
						dice.setId(id++);
						dice.setLayoutParams(diceP);
						bbutton.addView(dice);
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
				case 'w':
					Whistle w = new Whistle(this);
					bbutid[i] = id;
					w.setId(id++);
					w.setLayoutParams(coinP);
					nbutton.addView(w);
					break;
				}
			
		}
	}
	}

	public void initializeScoreboard(){
		String names[] = {"Joe", "team2", "team3", "team4"};
		String tnames[] = {"tc1", "tc2"};
		String bnames[] = {"tc3", "tc4","","","",""};
		String rules[] = {"Dont cheat", "Follow these Rules", "Blah", "Blah, blah"};
		char trow[] = {'c','t'};
		char brow[] = {'c','t','m','d','f','w'};
		long times[] = {50000};
		boolean types[] = {true};
		sb.setTopButtons(trow);
		sb.setBottomButtons(brow);
		sb.setTCount(2);
		sb.setBCount(6);
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
	
	public int pxtodp(int px){
		DisplayMetrics displayMetrics = ScoreboardUI.this.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoreboard_ui, menu);
		return true;
	}
	
	
	

}
