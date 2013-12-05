package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Tournament extends Activity {
	
		private Scoreboard sb;
		private TournamentInitializer ti;
		private LinearLayout[] mainPnls;
		private RelativeLayout[] pnls;
        private TextView winner;
        private TextView tName;
        private String win;
        private boolean gameOver = false;
        int mpcount = 1;

        //RadioGroup[] bg;
        private Button[] dbleTeams;
        private Button shfl, back, save, cntin, outPut, mnlBtn;
        //RadioButton[] teams;
        // //////////////////////////////////////////////////////// from tournament builder
        private int teamNum = 16;
        private String[] trnmntTeams;
        private boolean manualState ;
        // /////////////////////////////////////////////////////// from tournament builder
        private LinearLayout mainFrame;
        private String[] label;
        private boolean[] wins;
        int altI = 0;
        int j = 0;
        private String tm1, tm2;
        private Bundle extras;
        private Typeface f1;
        private Typeface f2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                extras = getIntent().getExtras();
                String from = extras.getString("FROM");
                winner= new TextView(this);
                tName = new TextView(this);
                f1 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
        		f2 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
        		tName.setTypeface(f2);
        		tName.setTextSize(25);
        		tName.setTextColor(Color.WHITE);
        		tName.setGravity(Gravity.CENTER);
        		winner.setTypeface(f2);
        		winner.setTextSize(20);
        		winner.setTextColor(Color.GRAY);
        		winner.setBackgroundResource(R.drawable.background5);
        		LinearLayout.LayoutParams tBox = new LinearLayout.LayoutParams(pxtodp(400),pxtodp(150));
        		tBox.gravity = Gravity.CENTER;
        		LinearLayout.LayoutParams tBar = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, pxtodp(180));
        		tBar.gravity = Gravity.CENTER;
        		LinearLayout.LayoutParams tBoxOut = new LinearLayout.LayoutParams(pxtodp(450),pxtodp(200));
        		tBoxOut.gravity = Gravity.CENTER;
        		LinearLayout.LayoutParams winBox = new LinearLayout.LayoutParams(pxtodp(450),pxtodp(250));
        		winBox.gravity = Gravity.CENTER;
                
                try{ 
    	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/tournament.bin")); 
    	            ti = (TournamentInitializer) ois.readObject();  
    	        } 
    			catch(Exception e){
    				Log.v("Serialization Read Error : ",e.getMessage());
    			}
                
                teamNum = ti.teamNumIni;
                trnmntTeams = ti.trnmntTeamsIni;
                manualState = ti.manualStateIni;
                label = ti.label;
                wins = ti.wins;
                sb = ti.sb;
                tName.setText(ti.name + ": " + sb.getName());
                
                shfl = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pxtodp(140), pxtodp(140));
                shfl.setBackgroundResource(R.drawable.shuffles);
                shfl.setLayoutParams(lp);
                shfl.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                        	MediaPlayer mp = MediaPlayer.create(Tournament.this, R.raw.click);
            	    		mp.start();
                            makeShuffle();
                        }
                });

                back = new Button(this);
                back.setBackgroundResource(R.drawable.backs);
                back.setLayoutParams(lp);
                back.setOnClickListener(new OnClickListener() {
                	@Override
                	public void onClick(View v) {
                		MediaPlayer mp = MediaPlayer.create(Tournament.this, R.raw.click);
        	    		mp.start();
                		save();
                		Intent myIntent = new Intent(Tournament.this, com.example.cs356.MainActivity.class);
                		
                		startActivity(myIntent);
                	}
                });

                
                
                tName.setWidth(pxtodp(1500));
                
                winner.setText("WINNER: ");
                winner.setGravity(Gravity.CENTER);
                winner.setLayoutParams(winBox);
                
                if(from.equals("scoreboard")){
                	int input = Integer.parseInt(extras.getString("INPUT"));
                	autoFromScoreBoard(input);
                	shfl.setEnabled(false);
                	shfl.setBackgroundResource(R.drawable.shuffle3);
                }else if(from.equals("continue")){
                	shfl.setEnabled(false);
                	shfl.setBackgroundResource(R.drawable.shuffle3);
                }else{
                	
                }
                
                int tmp = teamNum;// num of teams
                while (tmp >= 1) {// number of panel levels
                        mpcount++;
                        tmp /= 2;
                }

                mainPnls = new LinearLayout[mpcount];
                mainPnls[0] = new LinearLayout(this);
                mainPnls[0].setOrientation(LinearLayout.HORIZONTAL);
                mainPnls[0].addView(back);
                mainPnls[0].addView(tName);
                mainPnls[0].addView(shfl);
                mainPnls[0].setGravity(Gravity.CENTER);
                mainPnls[0].setBackgroundResource(R.drawable.background5);
                mainPnls[0].setLayoutParams(tBar);

                mainFrame = new LinearLayout(this);
                mainFrame.setOrientation(LinearLayout.VERTICAL);
                //mainFrame.setGravity(Gravity.CENTER);

                mainFrame.addView(mainPnls[0]);
               
                
                int tn = teamNum;

                //teams = new RadioButton[2 * teamNum - 1];
                dbleTeams = new Button[teamNum-1];
                for (altI = 0; altI < 2 * (teamNum - 1); altI+=2) {
                	dbleTeams[altI/2] = new Button(this);
                	dbleTeams[altI/2].setBackgroundResource(R.drawable.background1);
                	dbleTeams[altI/2].setEnabled(false);
                	dbleTeams[altI/2].setTypeface(f1);
                	dbleTeams[altI/2].setTextSize(10);
                	dbleTeams[altI/2].setGravity(Gravity.CENTER);
            		dbleTeams[altI/2].setTextColor(Color.WHITE);
                	if(label[altI].equals("") && label[altI+1].equals("")){
                		String in = "N/A" + " vs.\n" + "N/A";
                		dbleTeams[altI/2].setText(in.toUpperCase());
                    }
                    else if (label[altI].equals("") && !label[altI+1].equals("")){
                    	String in = "N/A" + " vs.\n" + label[altI+1];
                    	dbleTeams[altI/2].setText(in.toUpperCase());
                	}else if (!label[altI].equals("") && label[altI+1].equals("")){
                		String in = label[altI] + " vs.\n" + "N/A";
                    	dbleTeams[altI/2].setText(in.toUpperCase());
                    }else{
                    	String in = label[altI] + " vs.\n" + label[altI+1];
                        dbleTeams[altI/2].setText(in.toUpperCase());
                        if(!wins[altI/2]){
                        	dbleTeams[altI/2].setEnabled(true);
                        	dbleTeams[altI / 2].setBackgroundResource(R.drawable.altbacks);
                        }
                    }
                    dbleTeams[altI/2].setTag("" + altI/2);
                    if(!manualState){
                    	dbleTeams[altI / 2].setOnClickListener(scrBrdBtn);
                    }else{
                    	dbleTeams[altI / 2].setOnClickListener(manualClick);
                    }
                    dbleTeams[altI / 2].setLayoutParams(tBox);
                    
                                
                }
                altI = 0;
                for (int mpan = 1; mpan < mpcount-1; mpan++) {
                        pnls = new RelativeLayout[tn];
                        //bg = new RadioGroup[tn];
                        mainPnls[mpan] = new LinearLayout(this);
                        mainPnls[mpan].setOrientation(LinearLayout.HORIZONTAL);
                        mainPnls[mpan].setGravity(Gravity.CENTER);
                        j = 0;
                        for (int i = 0; i < tn; i++) {
                                //teams[altI].setText("t" + (i + 1));
                                //teams[altI].setTextColor(Color.BLACK);
                                if (i % 2 == 0) {
                                        //bg[j] = new RadioGroup(this);
                                        //bg[j].addView(teams[altI]);
                                        if (tn > 1) {
                                               //bg[j].addView(teams[altI + 1]);
                                        }
                                        pnls[j] = new RelativeLayout(this);
                                        //pnls[j].addView(bg[j]);
                                        //if (!manualState)
                                        if (mpan != mpcount - 1)
                                        pnls[j].addView(dbleTeams[altI / 2]);
                                        pnls[j].setBackgroundResource(R.drawable.background5);
                                        pnls[j].setGravity(Gravity.CENTER);
                                        pnls[j].setLayoutParams(tBoxOut);
                                        mainPnls[mpan].addView(pnls[j]);
                                }
                                j++;
                                altI++;
                        }
                        tn /= 2;
                        mainFrame.addView(mainPnls[mpan]);
                        mainFrame.setGravity(Gravity.TOP);
                        mainFrame.setGravity(Gravity.CENTER_HORIZONTAL);
                        mainFrame.setBackgroundColor(Color.BLUE);
                }
                mainFrame.addView(winner);
                mainFrame.setBackgroundResource(R.drawable.background2);
                setContentView(mainFrame);
                
                
                	
        }

        public String[] makeShuffle() {
                shfl.setEnabled(false);
                shfl.setBackgroundResource(R.drawable.shuffle3);
                Collections.shuffle(Arrays.asList(trnmntTeams));
                for (int i = 0; i < teamNum; i+=2) {
                        //teams[i].setText(trnmntTeams[i]);
                        //teams[i+1].setText(trnmntTeams[i+1]);
                        if (manualState){
                        	dbleTeams[i/2].setEnabled(true);
                        	dbleTeams[i / 2].setBackgroundResource(R.drawable.altbacks);
                    		dbleTeams[i/2].setOnClickListener(manualClick);
                    		String in = trnmntTeams[i] + " vs.\n" +trnmntTeams[i+1];
                    		dbleTeams[i/2].setText(in.toUpperCase());
                    		label[i] = trnmntTeams[i];
                    		label[i+1] = trnmntTeams[i+1];
                        }
                        else if (!manualState){
                                dbleTeams[i/2].setEnabled(true);
                        		dbleTeams[i/2].setOnClickListener(scrBrdBtn);
                        		String in = trnmntTeams[i] + " vs.\n" +trnmntTeams[i+1];
                        		dbleTeams[i/2].setText(in.toUpperCase());
                        		dbleTeams[i / 2].setBackgroundResource(R.drawable.altbacks);
                        		label[i] = trnmntTeams[i];
                        		label[i+1] = trnmntTeams[i+1];
                        }
                }
                return trnmntTeams;
        }

        /*
        // for Manual
        private OnClickListener Buttons = new OnClickListener() {
                @Override
                public void onClick(View v) {
                        int input = Integer.parseInt(v.getTag().toString());
                        if (input != altI - 1) {
                                teams[teamNum + input / 2].setEnabled(true);
                                teams[teamNum + input / 2].setText(teams[input].getText()
                                                .toString());
                                //teams[input].setEnabled(false);
                                if (!manualState)
                                        dbleTeams[(teamNum + input / 2) / 2].setEnabled(true);
                                if (input % 2 == 0)
                                        teams[input + 1].setEnabled(false);
                                else
                                        teams[input - 1].setEnabled(false);

                        } else {
                                teams[input].setEnabled(false);
                                shfl.setText("The Final\nWinner is\n"
                                                + teams[input].getText().toString());
                        }

                }
        };
        */
        
        // automated from ScoreBoard
        private void autoFromScoreBoard(int input) {
                if (input < teamNum - 2) {
                        wins[input]= true;
                        int in = (teamNum+input)/2;
                        if(label[in*2].equals("")){
                        	label[in*2] = extras.getString("WIN");
                        }else{
                        	label[in*2+1] = extras.getString("WIN");
                        }
                		
                } else {
                        winner.setText("WINNER: \n" + extras.getString("WIN"));
                        winner.setTextColor(Color.WHITE);
                        gameOver = true;
                }
        }
        
        private OnClickListener manualClick = new OnClickListener() {
        	@Override
        	public void onClick(View v){
        		MediaPlayer mp = MediaPlayer.create(Tournament.this, R.raw.click);
	    		mp.start();
        		final int input = Integer.parseInt(v.getTag().toString());
                tm1 = label[input*2];
                tm2 = label[input*2 + 1];
                final String teams[] = {tm1,tm2};
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Tournament.this, R.style.RefStyle));
				builder.setTitle("CHOOSE ROUND WINNER");
				builder.setItems(teams, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   dbleTeams[input].setEnabled(false);
		            	   dbleTeams[input].setBackgroundResource(R.drawable.background1);
		            	   win = teams[which];
		            	   if (input < teamNum - 2) {
			            	   wins[input] = true;
			            	   int in = (teamNum+input)/2;
		                        if(label[in*2].equals("")){
		                        	label[in*2] = teams[which];
		                        	String i = label[in*2] +  " VS.\n" + "N/A";
		                        	dbleTeams[in].setText(i.toUpperCase());
		                        }else{
		                        	label[in*2+1] = teams[which];
		                        	String i = label[in*2] + " VS.\n" + label[in*2+1];
		                        	dbleTeams[in].setText(i.toUpperCase());
		                        	dbleTeams[in].setEnabled(true);
		                        	dbleTeams[in].setBackgroundResource(R.drawable.altbacks);
		                        }
	                		
		            	   } else {
		            		   winner.setText("WINNER: \n"+ win);
		            		   winner.setTextColor(Color.WHITE);
		            		   gameOver = true;
		            	   }
		               }
		        });
				builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   //Cancel
	                   }
				});
				builder.create();
				builder.show();
        	}
        };

        private OnClickListener scrBrdBtn = new OnClickListener() {
                @Override
                public void onClick(View v) {
                		MediaPlayer mp = MediaPlayer.create(Tournament.this, R.raw.click);
                		mp.start();
                        int input = Integer.parseInt(v.getTag().toString());
                        tm1 = label[input*2];
                        tm2 = label[input*2 + 1];
                        dbleTeams[input].setEnabled(false);
                        dbleTeams[input].setBackgroundResource(R.drawable.background1);
                        //outPut.setText(tm1 + " " + tm2);
                        Intent myIntent = new Intent(Tournament.this, com.example.cs356.ScoreboardUI.class);
						String type = "tournament";
						String ip = Integer.toString(input);
						myIntent.putExtra("INPUT",ip);
						myIntent.putExtra("TYPE",type);
						String file = "/data/data/com.example.cs356/" + sb.getName().toLowerCase() + ".bin";
						String teamNames = tm1 + "," + tm2;
						ti.label = label;
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
						myIntent.putExtra("TEAMS", teamNames);
						myIntent.putExtra("FILE",file);
						startActivity(myIntent);
                        // to score builder
                        // (input*2) and (input*2 +1)  go to score builder, the winner
                        // (input) returns back to enable radiobutton
                        // enabled        teams[teamNum + input / 2].setEnabled(true);
                
                }
        };
        
        public void save(){
        	if(gameOver){
        		ti.cont = false;
        	}else{
        		ti.cont = true;
        	}
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
        }
        
        public int pxtodp(int px){
    		DisplayMetrics displayMetrics = Tournament.this.getResources().getDisplayMetrics();
    	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    	    return dp;
    	}
}