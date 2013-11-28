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

public class InputMenu extends Activity {
	Button numTeams;
	Button mainsc;
	Button teamNamers;
	Button nameGame;
	Button neutral;
	Button toprow;
	Button bottomrow;
	Button ruleinput;
	Button csb;
	
	String names[] = new String[4];
	String buttonTopNames[] = new String [8];
	String buttonNeutralNames[] = new String [8];

	
	String[] ButtonAssigning;
	String[] ButtonAmount;

	char topRow[] = new char[9];
	char neutralRow[] = new char [9];
	int numberOfButtonsTop;
	int numberOfButtonsNeutral;
	EditText ed[] = new EditText[4];  
	EditText buttonTNames[] = new EditText[8];  
	EditText buttonNNames[] = new EditText[8]; 
	EditText ruleList[] = new EditText[10];


	static Scoreboard sb2 = new Scoreboard();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_menu);
		numTeams = (Button) this.findViewById(R.id.numTeams);
		mainsc = (Button) this.findViewById(R.id.mainsc);
		teamNamers = (Button) this.findViewById(R.id.teamNames);
		nameGame = (Button) this.findViewById(R.id.nameGame);
		neutral = (Button) this.findViewById(R.id.neutral);
		toprow = (Button) this.findViewById(R.id.toprow);
		bottomrow = (Button) this.findViewById(R.id.botrow);
		ruleinput = (Button) this.findViewById(R.id.ruleinput);
		csb = (Button) this.findViewById(R.id.csb);


		numTeams.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] items = { "1", "2", "3","4"};
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Select the Number of Teams");
				listBuilder.setItems(items,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								numTeams.setText("Number of Teams: " + items[item]);
								int numTeams = Integer.parseInt(items[item]);
								sb2.setTeams(numTeams);
								//sb.setTeams(Integer.parseInt(items[item]));
								//Toast.makeText(getApplicationContext(),Integer.toString(numTeams), Toast.LENGTH_SHORT).show();
							}
							
						});
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		mainsc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] items = { "1", "2", "3","4"};
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(
						InputMenu.this);
				listBuilder.setTitle("Number of Digits for Score Counter");
				listBuilder.setItems(items,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								mainsc.setText("Main Score Counter Digits: " + items[item]);
								sb2.setDigits(Integer.parseInt(items[item]));
								//numberOfDigits = Integer.parseInt(items[item]);
								//Toast.makeText(getApplicationContext(),Integer.toString(numberOfTeams), Toast.LENGTH_SHORT).show();
								//Toast.makeText(getApplicationContext(),items[item], Toast.LENGTH_SHORT).show();
							}
						});
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		teamNamers.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Enter Team Names");
				LinearLayout teamNames = new LinearLayout(InputMenu.this);
				teamNames.setOrientation(1);

				
				for (int i = 0; i < sb2.getTeams(); i++) {

					TextView spacer = new TextView(InputMenu.this);
					teamNames.addView(spacer);
				    ed[i] = new EditText(InputMenu.this);
				    teamNames.addView(ed[i]);
				}

				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						for(int i = 0; i < sb2.getTeams(); i++){
							 names[i] = ed[i].getText().toString();

						}
						sb2.setTeamNames(names);
						teamNamers.setText("Team Names: Set");

					  }
					});

					listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
					
				listBuilder.setView(teamNames);
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		
		nameGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Enter the Name of the Game");
				LinearLayout nameGames = new LinearLayout(InputMenu.this);
				nameGames.setOrientation(1);

					TextView spacer = new TextView(InputMenu.this);
					nameGames.addView(spacer);
				    ed[0] = new EditText(InputMenu.this);
				    nameGames.addView(ed[0]);
				
				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						String gameName = ed[0].getText().toString();
						sb2.setName(gameName);
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
		
		
		neutral.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Would you like a neutral bar?");
				
				
				listBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						sb2.setHasNeutral(true);
						neutral.setText("Neutral Bar: Yes");
					  }
					});

					listBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    sb2.setHasNeutral(false);	
						neutral.setText("Neutral Bar: No");

					  }
					});
					
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		
		
		toprow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int iter = 0;
				if (sb2.getTeams() == 1)
					iter = 8;
				if (sb2.getTeams() == 2)
					iter = 4;
				if (sb2.getTeams() == 3)
					iter = 3;
				if (sb2.getTeams() == 4)
					iter = 2;
				
			
				ArrayList<String> AmountOfButtons = new ArrayList<String>();
				
				for (int i = 1; i <= iter; i++) {
					String adder = Integer.toString(i);
					AmountOfButtons.add(adder);
				}
					
				ButtonAmount = new String[AmountOfButtons.size()];
				ButtonAmount = AmountOfButtons.toArray(ButtonAmount);
				
				//final String[] ButtonAmount = { "1", "2", "3","4", "5", "6", "7", "8"};
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Number of Buttons Per Player");
				listBuilder.setItems(ButtonAmount,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								

								
								numberOfButtonsTop = Integer.parseInt(ButtonAmount[item]);
								ArrayList<String> ButtonList = new ArrayList<String>();
								
								for (int i = 0; i < numberOfButtonsTop; i++) {
									
									ButtonList.add("Button " + (i+1));
								}
				
								
								ButtonAssigning = new String[ButtonList.size()];
								ButtonAssigning = ButtonList.toArray(ButtonAssigning);
	

								//final String[] items = { "Button 1", "Button 2", "Button 3","Button 4", "Button 5", "Button 6", "Button 7", "Button 8"};
								final AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
								listBuilder.setTitle("Type Of Button");
								listBuilder.setItems(ButtonAssigning, new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, final int selection) {
												
												
												final String[] items = { "Special Counter", "Toggle","Dice", "Coin"};
												AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
												listBuilder2.setTitle("Select the type of Button");
												listBuilder2.setItems(items,
														new DialogInterface.OnClickListener() {
															public void onClick(DialogInterface dialog, int item) {
																
																if (items[item] == "Special Counter")
																	topRow[selection] = 'c';
																if (items[item] == "Toggle")
																	topRow[selection] = 't';
																if (items[item] == "Timer")
																	topRow[selection] = 'm';
																if (items[item] == "Dice")
																	topRow[selection] = 'd';
																if (items[item] == "Coin")
																	topRow[selection] = 'f';
																if (items[item] == "Whistle")
																	topRow[selection] = 'w';
																
																ButtonAssigning[selection] = "Button Set: " + items[item];
				
																AlertDialog alertList = listBuilder.show();
																alertList.show();
																
																
															}
															
														});
												
												listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int whichButton) {
														
														sb2.setTopButtons(topRow);
														sb2.setTCount(numberOfButtonsTop);
														
														
								
														
														
														
															AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
															listBuilder2.setTitle("Name Your Buttons");
															LinearLayout nameButton = new LinearLayout(InputMenu.this);
															nameButton.setOrientation(1);

															for (int i = 0; i < sb2.getTCount(); i++) {

																TextView spacer = new TextView(InputMenu.this);
																if (topRow[i] == 'c') {
																	spacer.setText("Special Counter");
																	nameButton.addView(spacer);
																    buttonTNames[i] = new EditText(InputMenu.this);
																    nameButton.addView(buttonTNames[i]);
																}
																if (topRow[i] == 't') {
																	spacer.setText("Toggle");
																	nameButton.addView(spacer);
																    buttonTNames[i] = new EditText(InputMenu.this);
																    nameButton.addView(buttonTNames[i]);
																}
											
															
															}
															
															listBuilder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int whichButton) {
																	
																	
																	for(int i = 0; i < sb2.getTCount(); i++){
																		if (topRow[i] == 't' || topRow[i] == 'c')
																		 buttonTopNames[i] = buttonTNames[i].getText().toString();
																		else
																			buttonTopNames[i] = "";
																	}
																	sb2.settNames(buttonTopNames);
																	toprow.setText("Top Row Options Set");
																  }
																});
																
																	listBuilder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																  public void onClick(DialogInterface dialog, int whichButton) {
																    // Canceled.
																  }
																});
																
															
															listBuilder2.setView(nameButton);
															AlertDialog alertList = listBuilder2.show();
															alertList.show();

																listBuilder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																  public void onClick(DialogInterface dialog, int whichButton) {
																    // Canceled.
																  }
																});														
													  }
													});

													listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
													  public void onClick(DialogInterface dialog, int whichButton) {
													    // Canceled.
													  }
													});
												
												AlertDialog alertList = listBuilder2.create();
												alertList.show();
											
											}
											
										});
								AlertDialog alertList = listBuilder.create();
								alertList.show();
							}
							
						});
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		
		
		
		
		
		bottomrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				


				
				final String[] NButtonAmount = { "1", "2", "3","4", "5", "6", "7", "8"};
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Number of Buttons on Neutral Bar");
				listBuilder.setItems(NButtonAmount,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								

								
								numberOfButtonsNeutral = Integer.parseInt(NButtonAmount[item]);
								ArrayList<String> ButtonListNeutral = new ArrayList<String>();
								
								for (int i = 0; i < numberOfButtonsNeutral; i++) {
									
									ButtonListNeutral.add("Button " + (i+1));
								}
				
								
								ButtonAssigning = new String[ButtonListNeutral.size()];
								ButtonAssigning = ButtonListNeutral.toArray(ButtonAssigning);
	

								//final String[] items = { "Button 1", "Button 2", "Button 3","Button 4", "Button 5", "Button 6", "Button 7", "Button 8"};
								final AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
								listBuilder.setTitle("Type Of Button");
								listBuilder.setItems(ButtonAssigning, new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, final int selection) {
												
												
												final String[] items = { "Special Counter", "Toggle","Dice", "Coin","Timer","Whistle"};
												AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
												listBuilder2.setTitle("Select the type of Button");
												listBuilder2.setItems(items,
														new DialogInterface.OnClickListener() {
															public void onClick(DialogInterface dialog, int item) {
																
																if (items[item] == "Special Counter")
																	neutralRow[selection] = 'c';
																if (items[item] == "Toggle")
																	neutralRow[selection] = 't';
																if (items[item] == "Timer")	
																	neutralRow[selection] = 'm';
																if (items[item] == "Dice")
																	neutralRow[selection] = 'd';
																if (items[item] == "Coin")
																	neutralRow[selection] = 'f';
																if (items[item] == "Whistle")
																	neutralRow[selection] = 'w';
																
																ButtonAssigning[selection] = "Button Set: " + items[item];
				
																AlertDialog alertList = listBuilder.show();
																alertList.show();
																
																
															}
															
														});
												
												listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int whichButton) {
														
														sb2.setBottomButtons(neutralRow);
														sb2.setBCount(numberOfButtonsNeutral);
														
														
								
														
														
														
															AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
															listBuilder2.setTitle("Name Your Buttons");
															LinearLayout nameButton = new LinearLayout(InputMenu.this);
															nameButton.setOrientation(1);

															for (int i = 0; i < sb2.getBCount(); i++) {

																TextView spacer = new TextView(InputMenu.this);
																if (neutralRow[i] == 'c') {
																	spacer.setText("Special Counter");
																	nameButton.addView(spacer);
																    buttonNNames[i] = new EditText(InputMenu.this);
																    nameButton.addView(buttonNNames[i]);
																}
																if (neutralRow[i] == 't') {
																	spacer.setText("Toggle");
																	nameButton.addView(spacer);
																    buttonNNames[i] = new EditText(InputMenu.this);
																    nameButton.addView(buttonNNames[i]);
																}
											
															
															}
															
															listBuilder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int whichButton) {
																	
																	
																	for(int i = 0; i < sb2.getBCount(); i++){
																		if (neutralRow[i] == 't' || neutralRow[i] == 'c')
																		 buttonNeutralNames[i] = buttonNNames[i].getText().toString();
																		else
																			buttonNeutralNames[i] = "";
																	}
																	sb2.setbNames(buttonNeutralNames);
																	bottomrow.setText("Bottom Row Options Set");
																  }
																});
																
																	listBuilder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																  public void onClick(DialogInterface dialog, int whichButton) {
																    // Canceled.
																  }
																});
																
															
															listBuilder2.setView(nameButton);
															AlertDialog alertList = listBuilder2.show();
															alertList.show();

																listBuilder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																  public void onClick(DialogInterface dialog, int whichButton) {
																    // Canceled.
																  }
																});														
													  }
													});

													listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
													  public void onClick(DialogInterface dialog, int whichButton) {
													    // Canceled.
													  }
													});
												
												AlertDialog alertList = listBuilder2.create();
												alertList.show();
											
											}
											
										});
								AlertDialog alertList = listBuilder.create();
								alertList.show();
							}
							
						});
				AlertDialog alertList = listBuilder.create();
				alertList.show();
			}
		});
		
		
		
		
		
		ruleinput.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
				listBuilder.setTitle("Enter Game Rules");
				LinearLayout teamNames = new LinearLayout(InputMenu.this);
				teamNames.setOrientation(1);
				final int numberRules = 10;
				
				for (int i = 0; i < numberRules; i++) {
					
					TextView spacer = new TextView(InputMenu.this);
					spacer.setText("Rule " + (i+1));
					teamNames.addView(spacer);
				    ruleList[i] = new EditText(InputMenu.this);
				    teamNames.addView(ruleList[i]);
				}

				listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						String rules[] = new String[10];

						for(int i = 0; i < numberRules; i++){
							 rules[i] = ruleList[i].getText().toString();

						}
						//sb2.setTeamNames(names);
						ruleinput.setText("Game Rules: Set");
						sb2.setRules(rules);

					  }
					});

					listBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
					

				
					
			    ScrollView scrollView = new ScrollView(InputMenu.this);
			    scrollView.addView(teamNames);
			    listBuilder.setView(scrollView);
			    listBuilder.show();
					
				//listBuilder.setView(teamNames);
				//AlertDialog alertList = listBuilder.create();
				//alertList.show();
			}
		});
		
		csb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				long times[] = {50000};
				boolean types[] = {true};
				
				sb2.setTimerTimes(times);
				sb2.setTimerTypes(types);
				saveOptions();
				
				Intent myIntent = new Intent(InputMenu.this, com.example.cs356.ScoreboardUI.class);
				String type = "null";
				myIntent.putExtra("TYPE",type);
				String file = "null";
				myIntent.putExtra("FILE",file);
				startActivity(myIntent);	

			}
		});
		
		
		
		
		
		
	}
	
	public void saveOptions(){
		//ScoreboardData sd;
		
		try 
        { 
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/create.bin"))); 
           oos.writeObject(sb2); 
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
        		String start[] = {sb2.getName()};
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