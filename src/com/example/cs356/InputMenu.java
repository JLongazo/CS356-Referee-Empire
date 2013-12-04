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
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class InputMenu extends Activity {
	private Button numTeams;
	private Button mainsc;
	private Button teamNamers;
	private Button nameGame;
	private Button neutral;
	private Button toprow;
	private Button bottomrow;
	private Button ruleinput;
	private Button csb;
	
	private int buttonCount = 0;
	
	private Typeface f1;
	
	private String names[] = new String[4];
	private String buttonTopNames[] = new String [8];
	private String buttonNeutralNames[] = new String [8];

	
	private String[] ButtonAssigning;
	private String[] ButtonAmount;
	private String[] items;
	private String[] NButtonAmount;

	private char topRow[] = new char[9];
	private char neutralRow[] = new char [9];
	private int numberOfButtonsTop;
	private int numberOfButtonsNeutral;
	private EditText ed[] = new EditText[4];  
	private EditText buttonTNames[] = new EditText[8];  
	private EditText buttonNNames[] = new EditText[8]; 
	private EditText ruleList[] = new EditText[10];
	
	private ArrayList<Long> times = new ArrayList<Long>();
	private ArrayList<Boolean> types = new ArrayList<Boolean>();


	static Scoreboard sb2 = new Scoreboard();
	private ScoreboardData sd = new ScoreboardData(new String[0]);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_menu);
		f1 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		numTeams = (Button) this.findViewById(R.id.numTeams);
		setUp(numTeams);
		mainsc = (Button) this.findViewById(R.id.mainsc);
		setUp(mainsc);
		teamNamers = (Button) this.findViewById(R.id.teamNames);
		setUp(teamNamers);
		nameGame = (Button) this.findViewById(R.id.nameGame);
		setUp(nameGame);
		nameGame.setBackgroundResource(R.drawable.back5s);
		neutral = (Button) this.findViewById(R.id.neutral);
		setUp(neutral);
		toprow = (Button) this.findViewById(R.id.toprow);
		setUp(toprow);
		bottomrow = (Button) this.findViewById(R.id.botrow);
		setUp(bottomrow);
		ruleinput = (Button) this.findViewById(R.id.ruleinput);
		setUp(ruleinput);
		csb = (Button) this.findViewById(R.id.csb);
		setUp(csb);
		
		try{
        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scoreboards.bin")); 
            sd = (ScoreboardData) ois.readObject();
        }
        catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());

        }
		


		numTeams.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 1){
					final String[] items = { "1", "2", "3","4"};
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
					listBuilder.setTitle("Select the Number of Teams");
					listBuilder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									numTeams.setText("NUMBER OF TEAMS: " + items[item].toUpperCase());
									int numTeams2 = Integer.parseInt(items[item]);
									sb2.setTeams(numTeams2);
									numTeams.setBackgroundResource(R.drawable.background3);
									buttonCount++;
									mainsc.setBackgroundResource(R.drawable.back5s);
									//sb.setTeams(Integer.parseInt(items[item]));
									//Toast.makeText(getApplicationContext(),Integer.toString(numTeams), Toast.LENGTH_SHORT).show();
								}
								
							});
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}
				else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		mainsc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 2){
					final String[] items = { "1", "2", "3","4"};
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(
							InputMenu.this);
					listBuilder.setTitle("Number of Digits for Score Counter");
					listBuilder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									mainsc.setText("SCORE COUNTER DIGITS: " + items[item].toUpperCase());
									sb2.setDigits(Integer.parseInt(items[item]));
									//numberOfDigits = Integer.parseInt(items[item]);
									//Toast.makeText(getApplicationContext(),Integer.toString(numberOfTeams), Toast.LENGTH_SHORT).show();
									//Toast.makeText(getApplicationContext(),items[item], Toast.LENGTH_SHORT).show();
									mainsc.setBackgroundResource(R.drawable.background3);
									buttonCount++;
									teamNamers.setBackgroundResource(R.drawable.back5s);
								}
							});
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}
				else{
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			} 
		});
		
		teamNamers.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 3){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
					listBuilder.setTitle("Enter Team Names");
					listBuilder.setMessage("7 characters maximum.");
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
								String name = ed[i].getText().toString();
								if(name.length() <= 7){
									names[i] = name;
								}else{
									names[i] = name.substring(0,7);
								}
	
							}
							sb2.setTeamNames(names);
							teamNamers.setText("TEAM NAMES: SET");
							teamNamers.setBackgroundResource(R.drawable.background3);
							buttonCount++;
							neutral.setBackgroundResource(R.drawable.back5s);
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
				} else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		nameGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 0){
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
							nameGame.setText("GAME NAME: " + gameName.toUpperCase());
							nameGame.setBackgroundResource(R.drawable.background3);
							numTeams.setBackgroundResource(R.drawable.back5s);
							buttonCount++;
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
				}else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		neutral.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 4){
					AlertDialog.Builder listBuilder = new AlertDialog.Builder(InputMenu.this);
					listBuilder.setTitle("Would you like a neutral bar?");
					
					
					listBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							
							sb2.setHasNeutral(true);
							neutral.setText("NEUTRAL BAR: YES");
							buttonCount++;
							neutral.setBackgroundResource(R.drawable.background3);
							toprow.setBackgroundResource(R.drawable.back5s);
						  }
						});
	
						listBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						  public void onClick(DialogInterface dialog, int whichButton) {
						    sb2.setHasNeutral(false);	
							neutral.setText("NEUTRAL BAR: NO");
							buttonCount++;
							neutral.setBackgroundResource(R.drawable.background3);
							toprow.setBackgroundResource(R.drawable.back5s);
						  }
						});
						
					AlertDialog alertList = listBuilder.create();
					alertList.show();
				}else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		
		toprow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 5){
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
													
													
													final String[] items = { "Special Counter", "Toggle","Dice"};
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
																listBuilder2.setMessage("4 characters maximum.");
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
																			if (topRow[i] == 't' || topRow[i] == 'c'){
																			 	String name = buttonTNames[i].getText().toString();
																				if(name.length() <= 4){
																					buttonTopNames[i] = name;
																				}else{
																					buttonTopNames[i] = name.substring(0,4);
																				}
																			
																			}else
																				buttonTopNames[i] = "";
																		}
																		sb2.settNames(buttonTopNames);
																		toprow.setText("ROW 1 OPTIONS SET");
																		toprow.setBackgroundResource(R.drawable.background3);
																		buttonCount++;
																		bottomrow.setBackgroundResource(R.drawable.back5s);
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
			}else {
				Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
			}
			}
		});
		
		
		
		
		
		
		bottomrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 6){


					String n[] = { "1", "2", "3","4", "5", "6", "7", "8"};
					if(sb2.isHasNeutral()){
						NButtonAmount = n;
					} else {
						NButtonAmount = ButtonAmount;
					}
					 
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
													String items1[] = { "Special Counter", "Toggle","Dice", "Coin","Timer","Whistle"};
													String items2[] = { "Special Counter", "Toggle","Dice"};
													if(sb2.isHasNeutral()){
														items = items1;
													}
													else {
														items = items2;
													}
													AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
													listBuilder2.setTitle("Select the type of Button");
													listBuilder2.setItems(items,
															new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int item) {
																	
																	if (items[item] == "Special Counter")
																		neutralRow[selection] = 'c';
																	if (items[item] == "Toggle")
																		neutralRow[selection] = 't';
																	if (items[item] == "Timer")	{
																		neutralRow[selection] = 'm';
																		AlertDialog.Builder listBuilder3 = new AlertDialog.Builder(InputMenu.this);
																		listBuilder3.setTitle("Timer Settings");
																		listBuilder3.setMessage("Enter number of minutes. Leave field blank for a Count-Up timer.");
																		final EditText et = new EditText(InputMenu.this);
																		et.setInputType(InputType.TYPE_CLASS_NUMBER);
																		listBuilder3.setView(et);
																		listBuilder3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																			public void onClick(DialogInterface dialog, int whichButton) {
																				String check = et.getText().toString();
																				if(check.equals("")){
																					times.add(0L);
																					types.add(true);
																				}else{
																					long time = (Integer.parseInt(et.getText().toString()) * 60000);
																					times.add(time);
																					types.add(false);
																				}
																				AlertDialog alertList = listBuilder.show();
																				alertList.show();
																			}});
																		AlertDialog alertList2 = listBuilder3.show();
																		alertList2.show();
																	}
																	if (items[item] == "Dice")
																		neutralRow[selection] = 'd';
																	if (items[item] == "Coin")
																		neutralRow[selection] = 'f';
																	if (items[item] == "Whistle")
																		neutralRow[selection] = 'w';
																	
																	ButtonAssigning[selection] = "Button Set: " + items[item];
																	if (items[item] != "Timer"){
																		AlertDialog alertList = listBuilder.show();
																		alertList.show();
																	}
																	
																	
																}
																
															});
													
													listBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int whichButton) {
															
															sb2.setBottomButtons(neutralRow);
															sb2.setBCount(numberOfButtonsNeutral);
															
															
									
															
															
															
																AlertDialog.Builder listBuilder2 = new AlertDialog.Builder(InputMenu.this);
																listBuilder2.setTitle("Name Your Buttons");
																listBuilder2.setTitle("4 characters maximum.");
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
																			if (neutralRow[i] == 't' || neutralRow[i] == 'c'){
																				String name = buttonNNames[i].getText().toString();
																				if(name.length() <= 4){
																					buttonNeutralNames[i] = name;
																				}else{
																					buttonNeutralNames[i] = name.substring(0,4);
																				}
																			}
																			else
																				buttonNeutralNames[i] = "";
																		}
																		sb2.setbNames(buttonNeutralNames);
																		bottomrow.setText("ROW 2 OPTIONS SET");
																		bottomrow.setBackgroundResource(R.drawable.background3);
																		buttonCount++;
																		ruleinput.setBackgroundResource(R.drawable.back5s);
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
				}else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		
		
		
		ruleinput.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 7){
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
							ruleinput.setText("GAME RULES: SET");
							sb2.setRules(rules);
							buttonCount++;
							ruleinput.setBackgroundResource(R.drawable.background3);
							csb.setBackgroundResource(R.drawable.back5s);
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
				}else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		csb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonCount == 8){
					long times2[] = new long[times.size()];
					boolean types2[] = new boolean[types.size()];
					
					for(int i = 0; i < times.size(); i++){
						times2[i] = times.get(i);
						types2[i] = types.get(i);
					}
					
					sb2.setTimerTimes(times2);
					sb2.setTimerTypes(types2);
					saveOptions();
					if(sd.getSbs().length < 20){
						sd.addSb(sb2.getName());
						try{
				        	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.cs356/scoreboards.bin")); 
				            oos.writeObject(sd);
				            oos.flush();
				            oos.close();
				        }
				        catch(Exception e){
							Log.v("Serialization Read Error : ",e.getMessage());
		
				        }
							
					}else{
						Toast.makeText(InputMenu.this, "Too many score boards! Delete some to make room.", Toast.LENGTH_LONG).show();
					}
					Intent myIntent = new Intent(InputMenu.this, com.example.cs356.MainActivity.class);
					startActivity(myIntent);
				}else {
					Toast.makeText(InputMenu.this, "Can't do that yet!", Toast.LENGTH_LONG).show();
				}

			}
		});
		
		
		
		
		
		
	}
	
	public void saveOptions(){
		//ScoreboardData sd;
		
		try 
        { 
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/" + sb2.getName().toLowerCase() +".bin"))); 
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
	
	public void setUp(Button b){
		b.setTypeface(f1);
		b.setTextColor(Color.WHITE);
		b.setGravity(Gravity.CENTER);
		b.setBackgroundResource(R.drawable.background1);
	}
	
}