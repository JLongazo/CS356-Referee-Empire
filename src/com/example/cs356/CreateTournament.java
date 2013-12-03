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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class CreateTournament extends Activity {
	Button numTeams;
	Button teamNamers;
	Button nameGame;
	Button slctMnAuto;
	Button trnmnt;
	//to pass
	int nmbrTeams; // and names
	int mnlStt;
	
	ScrollView scroll;
	LinearLayout teamNames;
	String names[];
		

	EditText ed[] ;

//	static Tournament sb2 = new Tournament();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_tournament);
		numTeams = (Button) this.findViewById(R.id.numTeams);
		teamNamers = (Button) this.findViewById(R.id.teamNames);
		nameGame = (Button) this.findViewById(R.id.nameGame);
		slctMnAuto = (Button) this.findViewById(R.id.mnlAoutState);
		trnmnt = (Button) this.findViewById(R.id.strtTrnmnt);

		scroll=new ScrollView(CreateTournament.this);
		scroll.setVerticalScrollBarEnabled(true);
	    scroll.setHorizontalScrollBarEnabled(true);
	    
//		csb = (Button) this.findViewById(R.id.csb);
		
		teamNames = new LinearLayout(CreateTournament.this);
		teamNames.setOrientation(LinearLayout.VERTICAL);
		
		numTeams.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] items = { "4", "8", "16", "32"};
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
				listBuilder.setTitle("Select the Number of Teams");
				listBuilder.setItems(items,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								numTeams.setText("Number of Teams: " + items[item]);
								nmbrTeams = Integer.parseInt(items[item]);
							}	
						});
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		teamNamers.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
				listBuilder.setTitle("Enter Team Names");
				ed = new EditText[nmbrTeams];  
				for (int i = 0; i < nmbrTeams; i++) {
					TextView spacer = new TextView(CreateTournament.this);
					teamNames.addView(spacer);
				    ed[i] = new EditText(CreateTournament.this);
				    
				    teamNames.addView(ed[i]);
				    
				}

				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {	
						names = new String[nmbrTeams];
						for(int i = 0; i < nmbrTeams; i++){
							 names[i] = ed[i].getText().toString();
						}
				//		sb2.setTeamNames(names);
						teamNamers.setText("Team Names: Set");
					  }
					});

					listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
					
				scroll.addView(teamNames);
				listBuilder.setView(scroll);
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		nameGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
				listBuilder.setTitle("Enter the Name of the Game");
				LinearLayout nameGames = new LinearLayout(CreateTournament.this);
				nameGames.setOrientation(1);

					TextView spacer = new TextView(CreateTournament.this);
					nameGames.addView(spacer);
				    ed[0] = new EditText(CreateTournament.this);
				    nameGames.addView(ed[0]);
				
				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						String gameName = ed[0].getText().toString();
			//			sb2.setName(gameName);
						nameGame.setText("Game Name: " + gameName);
						
					  }
					});

					listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});

				listBuilder.setView(nameGames);
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});

		slctMnAuto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(CreateTournament.this);
				listBuilder.setTitle("Enter the State of the Tournament 1 for Manual 0 for Automatic");
				LinearLayout stLyt = new LinearLayout(CreateTournament.this);
				stLyt.setOrientation(1);

					TextView spacer = new TextView(CreateTournament.this);
					stLyt.addView(spacer);
				    ed[0] = new EditText(CreateTournament.this);
				    stLyt.addView(ed[0]);
				
				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mnlStt = Integer.parseInt(ed[0].getText().toString());	
			//			sb2.setManualState(numTeams);
					  }
					});

					listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});

				listBuilder.setView(stLyt);
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
	
		trnmnt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		/*		
				long times[] = {50000};
				boolean types[] = {true};
				
/*				sb2.setTimerTimes(times);
				sb2.setTimerTypes(types);
				saveOptions();
				*/
				
				TournamentInitializer sb2 = new TournamentInitializer(nmbrTeams, names, mnlStt);
				Intent myIntent = new Intent(CreateTournament.this, com.example.cs356.Tournament.class);
/*				String type = "null";
				myIntent.putExtra("TYPE",type);
				String file = "null";
				myIntent.putExtra("FILE",file);*/
				startActivity(myIntent);	

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
	
}