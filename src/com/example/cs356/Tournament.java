package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
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
        LinearLayout[] mainPnls;
        RelativeLayout[] pnls;
        private TextView winner;
        private TextView tName;

        int mpcount = 1;

        //RadioGroup[] bg;
        Button[] dbleTeams;
        Button shfl, back, save, cntin, outPut, mnlBtn;
        //RadioButton[] teams;
        // //////////////////////////////////////////////////////// from tournament builder
        private int teamNum = 16;
        String[] trnmntTeams;
        private boolean manualState ;
        // /////////////////////////////////////////////////////// from tournament builder
        private LinearLayout mainFrame;
        private String[] label;
        private boolean[] wins;
        int altI = 0;
        int j = 0;
        String tm1, tm2;
        private Bundle extras;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                extras = getIntent().getExtras();
                String from = extras.getString("FROM");
                winner= new TextView(this);
                tName = new TextView(this);
                
                try{ 
    	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/tournament.bin")); 
    	            ti = (TournamentInitializer) ois.readObject();  
    	        } 
    			catch(Exception e){
    				Log.v("Serialization Read Error : ",e.getMessage());
    			}
                tName.setText(ti.name);
                teamNum = ti.teamNumIni;
                trnmntTeams = ti.trnmntTeamsIni;
                manualState = ti.manualStateIni;
                label = ti.label;
                wins = ti.wins;
                sb = ti.sb;
                
                shfl = new Button(this);
                shfl.setText("Shuffle");
                shfl.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                                makeShuffle();
                        }
                });

                back = new Button(this);
                back.setBackgroundResource(R.drawable.backs);
                back.setWidth(pxtodp(140));
                back.setHeight(pxtodp(140));
                back.setOnClickListener(new OnClickListener() {
                	@Override
                	public void onClick(View v) {
                		save();
                		Intent myIntent = new Intent(Tournament.this, com.example.cs356.MainActivity.class);
                		startActivity(myIntent);
                	}
                });

                outPut = new Button(this);
                outPut.setText("outPut");

                cntin = new Button(this);
                cntin.setText("Continue");

                save = new Button(this);
                save.setText("Save");
                
                tName.setWidth(pxtodp(800));
                
                winner.setText("WINNER: ");
                
                if(from.equals("scoreboard")){
                	int input = Integer.parseInt(extras.getString("INPUT"));
                	autoFromScoreBoard(input);
                	shfl.setEnabled(false);
                }else if(from.equals("continue")){
                	shfl.setEnabled(false);
                	
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

                mainFrame = new LinearLayout(this);
                mainFrame.setOrientation(LinearLayout.VERTICAL);
                mainFrame.setGravity(Gravity.CENTER);

                mainFrame.addView(mainPnls[0]);
                
                int tn = teamNum;

                //teams = new RadioButton[2 * teamNum - 1];
                dbleTeams = new Button[teamNum-1];
                for (altI = 0; altI < 2 * (teamNum - 1); altI+=2) {
                	dbleTeams[altI/2] = new Button(this);
                	dbleTeams[altI/2].setEnabled(false);
                	if(label[altI].equals("") && label[altI+1].equals("")){
                		dbleTeams[altI/2].setText("N/A" + " vs. " + "N/A");
                    }
                    else if (label[altI].equals("") && !label[altI+1].equals("")){
                    	dbleTeams[altI/2].setText("N/A" + " vs. " + label[altI+1]);
                	}else if (!label[altI].equals("") && label[altI+1].equals("")){
                    	dbleTeams[altI/2].setText(label[altI] + " vs. " + "N/A");
                    }else{
                        dbleTeams[altI/2].setText(label[altI] + " vs. " + label[altI+1]);
                        if(!wins[altI/2]){
                        	dbleTeams[altI/2].setEnabled(true);
                        }
                    }
                    dbleTeams[altI/2].setTag("" + altI/2);
                    dbleTeams[altI / 2].setOnClickListener(scrBrdBtn);
                    dbleTeams[altI / 2].setHeight(pxtodp(140));
                    dbleTeams[altI / 2].setWidth(pxtodp(400));
                                
                }
                altI = 0;
                for (int mpan = 1; mpan < mpcount; mpan++) {
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
                                        if (!manualState)
                                                if (mpan != mpcount - 1)
                                                        pnls[j].addView(dbleTeams[altI / 2]);
                                        pnls[j].setBackgroundColor(Color.WHITE);
                                        mainPnls[mpan].addView(pnls[j]);
                                }
                                j++;
                                altI++;
                        }
                        tn /= 2;
                        mainFrame.addView(mainPnls[mpan]);
                        mainFrame.setGravity(Gravity.CENTER_HORIZONTAL);
                        mainFrame.setGravity(Gravity.CENTER_VERTICAL);
                        mainFrame.setBackgroundColor(Color.BLUE);
                }
                mainFrame.addView(winner);
                setContentView(mainFrame);
                
                
                	
        }

        private int getTeamNum() {
                return teamNum;
        }

        private void setTeamNum(int teamNum) {
                this.teamNum = teamNum;
        }

        private String[] getTrnmntTeams() {
                return trnmntTeams;
        }

        private void setTrnmntTeams(String[] trnmntTeams) {
                this.trnmntTeams = trnmntTeams;
        }

        private boolean isManualState() {
                return manualState;
        }

        private void setManualState(boolean manualState) {
                this.manualState = true;
        }

        public String[] makeShuffle() {
                shfl.setText("shuffled");
                shfl.setEnabled(false);
                Collections.shuffle(Arrays.asList(trnmntTeams));
                for (int i = 0; i < teamNum; i+=2) {
                        //teams[i].setText(trnmntTeams[i]);
                        //teams[i+1].setText(trnmntTeams[i+1]);
                        if (manualState){
                               //teams[i].setEnabled(true);
                        }
                        else if (!manualState){
                                dbleTeams[i/2].setEnabled(true);
                        		dbleTeams[i/2].setOnClickListener(scrBrdBtn);
                        		dbleTeams[i/2].setText(trnmntTeams[i] + "\n" +trnmntTeams[i+1]);
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
                if (input < teamNum * 2 - 1) {
                        wins[input]= true;
                        if(input < teamNum/2){
                        	label[input+teamNum] = extras.getString("WIN");
                        }else if(input < teamNum/4 && teamNum > 4){
                        	label[input+teamNum/2] = extras.getString("WIN");
                        }else if(input < teamNum/8 && teamNum > 8){
                        	label[input+teamNum/4] = extras.getString("WIN");
                        }else if(input < teamNum/16 && teamNum > 16){
                        	label[input+teamNum/8] = extras.getString("WIN");
                        }
                		
                } else {
                        winner.setText("WINNER: "
                                        + extras.getString("WIN"));
                }
        }

        private OnClickListener scrBrdBtn = new OnClickListener() {
                @Override
                public void onClick(View v) {
                        int input = Integer.parseInt(v.getTag().toString());
                        tm1 = label[input*2];
                        tm2 = label[input*2 + 1];
                        dbleTeams[input].setEnabled(false);
                        dbleTeams[input].setBackgroundColor(Color.TRANSPARENT);
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