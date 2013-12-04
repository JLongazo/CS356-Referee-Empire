package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class CreateTournament extends Activity {
	private Button numTeams;
	private Button teamNamers;
	private Button nameGame;
	private Button slctMnAuto;
	private Button namet;
	private Button trnmnt;
	private Scoreboard sb;
	//to pass
	private int nmbrTeams; // and names
	private int mnlStt;
	private Typeface f1;
	private ScrollView scroll;
	private LinearLayout teamNames;
	private String names[];
	private String sbs[];
	private String games[];
	private TournamentInitializer ti;
	private int buttonC = 0;	

	private EditText ed[] ;

//	static Tournament sb2 = new Tournament();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_tournament);
		f1 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		numTeams = (Button) this.findViewById(R.id.numTeams);
		setUp(numTeams);
		numTeams.setBackgroundResource(R.drawable.back5s);
		teamNamers = (Button) this.findViewById(R.id.teamNames);
		setUp(teamNamers);
		nameGame = (Button) this.findViewById(R.id.nameGame);
		setUp(nameGame);
		slctMnAuto = (Button) this.findViewById(R.id.mnlAoutState);
		setUp(slctMnAuto);
		trnmnt = (Button) this.findViewById(R.id.strtTrnmnt);
		setUp(trnmnt);
		namet = (Button) this.findViewById(R.id.namet);
		setUp(namet);
		
		scroll=new ScrollView(CreateTournament.this);
		scroll.setVerticalScrollBarEnabled(true);
	    scroll.setHorizontalScrollBarEnabled(true);
	    
	    ti = new TournamentInitializer();
//		csb = (Button) this.findViewById(R.id.csb);
		
		teamNames = new LinearLayout(CreateTournament.this);
		teamNames.setOrientation(LinearLayout.VERTICAL);
		
		numTeams.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 0){
					final String[] items = { "4", "8", "16", "32"};
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
					listBuilder.setTitle("Select the Number of Teams");
					listBuilder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									numTeams.setText("NUMBER OF TEAMS: " + items[item]);
									nmbrTeams = Integer.parseInt(items[item]);
									numTeams.setBackgroundResource(R.drawable.background3);
									buttonC++;
									teamNamers.setBackgroundResource(R.drawable.back5s);
								}	
							});
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else{
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		teamNamers.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 1){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
					listBuilder.setTitle("Enter Team Names");
					ed = new EditText[nmbrTeams];  
					for (int i = 0; i < nmbrTeams; i++) {
						TextView spacer = new TextView(CreateTournament.this);
						teamNames.addView(spacer);
					    ed[i] = new EditText(CreateTournament.this);
					    
					    teamNames.addView(ed[i]);
					    
					}
	
					listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {	
							names = new String[nmbrTeams];
							for(int i = 0; i < nmbrTeams; i++){
								 names[i] = ed[i].getText().toString();
							}
					//		sb2.setTeamNames(names);
							teamNamers.setText("TEAM NAMES: SET");
							teamNamers.setBackgroundResource(R.drawable.background3);
							buttonC++;
							nameGame.setBackgroundResource(R.drawable.back5s);
						  }
						});
	
						listBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int whichButton) {
						    // Canceled.
						  }
						});
						
					scroll.addView(teamNames);
					listBuilder.setView(scroll);
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else{
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		nameGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 2){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
					listBuilder.setTitle("Choose Game");
					ScoreboardData sd;
					games = new String[nmbrTeams];
					try{
			        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scoreboards.bin")); 
			            sd = (ScoreboardData) ois.readObject();
			        }
			        catch(Exception e){
						Log.v("Serialization Read Error : ",e.getMessage());
						sd = new ScoreboardData(games);
			        }
					games = sd.getSbs();
					listBuilder.setItems(games,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									String file = "/data/data/com.example.cs356/" + games[item].toLowerCase() + ".bin";
									try {
										ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));  
										sb = (Scoreboard) ois.readObject();
									}
									
									catch(Exception e){
										Log.v("Serialization Read Error : ",e.getMessage());
									}
									nameGame.setText("GAME: " + games[item].toUpperCase());
									nameGame.setBackgroundResource(R.drawable.background3);
									buttonC++;
									slctMnAuto.setBackgroundResource(R.drawable.back5s);
								}
								
							});
					listBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int whichButton) {
						    // Canceled.
						  }
						});
	
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else{
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});

		slctMnAuto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 3){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
					listBuilder.setTitle("Automatic or Manual Tournament?");
					listBuilder.setPositiveButton("AUTOMATIC", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							ti.manualStateIni = false;
							slctMnAuto.setBackgroundResource(R.drawable.background3);
							buttonC++;
							namet.setBackgroundResource(R.drawable.back5s);
						  }
						});
	
						listBuilder.setNeutralButton("MANUAL", new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int whichButton) {
							  ti.manualStateIni = true;
							  slctMnAuto.setBackgroundResource(R.drawable.background3);
							  buttonC++;
							  namet.setBackgroundResource(R.drawable.back5s);
						  }
						});
						listBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
							  public void onClick(DialogInterface dialog, int whichButton) {
								  //cancel
							  }
							});
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else{
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
	
		namet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 4){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
					listBuilder.setTitle("Enter the Name of the Tournament");
					LinearLayout nameGames = new LinearLayout(CreateTournament.this);
					nameGames.setOrientation(1);
	
						TextView spacer = new TextView(CreateTournament.this);
						nameGames.addView(spacer);
					    ed[0] = new EditText(CreateTournament.this);
					    nameGames.addView(ed[0]);
					
					listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							
							String gameName = ed[0].getText().toString();
							ti.name = gameName;
							namet.setText("TOURNAMENT NAME: " + gameName.toUpperCase());
							namet.setBackgroundResource(R.drawable.background3);
							trnmnt.setBackgroundResource(R.drawable.back5s);
							buttonC++;
						  }
						});
	
						listBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int whichButton) {
						    // Canceled.
						  }
						});
						
					listBuilder.setView(nameGames);
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else {
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		trnmnt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonC == 5){
					ti.sb = sb;
					ti.teamNumIni = nmbrTeams;
					ti.trnmntTeamsIni = names;
					int matches = nmbrTeams/2;
					int count = 0;
					while(matches > 1){
						count += matches;
						matches-=matches/2;
					}
					count++;
					String[] label = new String[count*2];
					boolean[] wins = new boolean[count];
					for(int i = 0; i < label.length; i++){
						label[i] = "";
					}
					for(int i = 0; i < wins.length; i++){
						wins[i] = false;
					}
					ti.label = label;
					ti.wins = wins;
					try 
	                { 
	                   ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/tournament.bin"))); 
	                   oos.writeObject(ti); // write the class as an 'object' 
	                   oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin' 
	                   oos.close();// close the stream 
	                } 
	                catch(Exception ex) 
	                { 
	                   Log.v("Serialization Save Error : ",ex.getMessage()); 
	                   ex.printStackTrace(); 
	                }
					Intent myIntent = new Intent(CreateTournament.this, com.example.cs356.Tournament.class);
					myIntent.putExtra("FROM","");
	/*				String type = "null";
					myIntent.putExtra("TYPE",type);
					String file = "null";
					myIntent.putExtra("FILE",file);*/
					startActivity(myIntent);	
				}else {
					Toast.makeText(CreateTournament.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	public void saveOptions(){
		//ScoreboardData sd;
		
		try 
        { 
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/create.bin"))); 
     //      oos.writeObject(sb2); 
           oos.flush(); 
           oos.close();
           //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scoreboards.bin")); 
           //sd = (ScoreboardData) ois.readObject();
           //sd.addSb(sb2.getName());
           //oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/scoreboards.bin")));
           //oos.writeObject(sd);
	       //oos.flush();  
	       //oos.close();
        } 
        catch(Exception ex) 
        { 
        	try{
      //  		String start[] = {sb2.getName()};
	        	//sd = new ScoreboardData(start);
	            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/scoreboards.bin")));
	            //oos.writeObject(sd);
	 	       	oos.flush();  
	 	      	oos.close();	
        	}
        	catch(Exception e2){
        		Log.v("Serialization Save Error : ",ex.getMessage()); 
	 	      	ex.printStackTrace(); 
        	}
        }
	}
	
	public void setUp(Button b){
		b.setTypeface(f1);
		b.setTextColor(Color.WHITE);
		b.setGravity(Gravity.CENTER);
		b.setBackgroundResource(R.drawable.background1);
	}
	
}