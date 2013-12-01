package com.example.cs356;

import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class Tournament extends Activity {
	
		private Scoreboard sb;
        LinearLayout[] mainPnls;
        RelativeLayout[] pnls;

        int mpcount = 1;

        //RadioGroup[] bg;
        Button[] dbleTeams;
        Button shfl, back, save, cntin, outPut, mnlBtn;
        //RadioButton[] teams;
        // //////////////////////////////////////////////////////// from tournament builder
        private int teamNum = 16;
        String[] trnmntTeams = { "Empire", "CA", "LA", "CPP", "TA", "MN", "OL",
                        "AB", "CD", "EF", "GH", "BO", "CHEVY", "FORD", "TX", "AR" };
        private boolean manualState ;
        // /////////////////////////////////////////////////////// from tournament builder
        private LinearLayout mainFrame;
        int altI = 0;
        int j = 0;
        String tm1, tm2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                
                manualState = false;//gets from outside from tournament builder
                
                shfl = new Button(this);
                shfl.setText("Shuffle");
                shfl.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                                makeShuffle();
                        }
                });

                back = new Button(this);
                back.setText("Back");

                outPut = new Button(this);
                outPut.setText("outPut");

                cntin = new Button(this);
                cntin.setText("Continue");

                save = new Button(this);
                save.setText("Save");

                int tmp = teamNum;// num of teams
                while (tmp >= 1) {// number of panel levels
                        mpcount++;
                        tmp /= 2;
                }

                mainPnls = new LinearLayout[mpcount];
                mainPnls[0] = new LinearLayout(this);
                mainPnls[0].setOrientation(LinearLayout.VERTICAL);
                mainPnls[0].addView(back);
                mainPnls[0].addView(cntin);
                mainPnls[0].addView(shfl);
                mainPnls[0].addView(outPut);

                mainFrame = new LinearLayout(this);
                mainFrame.setOrientation(LinearLayout.HORIZONTAL);

                mainFrame.addView(mainPnls[0]);
                int tn = teamNum;

               // teams = new RadioButton[2 * teamNum - 1];
                dbleTeams = new Button[teamNum];
                for (altI = 0; altI < 2 * teamNum - 1; altI++) {
                       // teams[altI] = new RadioButton(this);
                        //teams[altI].setTag("" + altI);
                        //teams[altI].setOnClickListener(Buttons);
                        //teams[altI].setEnabled(false);
                        if (!manualState)
                                if (altI % 2 == 0) {
                                        dbleTeams[altI / 2] = new Button(this);
                                        dbleTeams[altI / 2].setTag("" + altI / 2);
                                        //dbleTeams[altI / 2].setBackgroundColor(Color.YELLOW);
                                        dbleTeams[altI / 2].setOnClickListener(scrBrdBtn);
                                        dbleTeams[altI / 2].setEnabled(false);
                                        dbleTeams[altI / 2].setHeight(90);
                                        dbleTeams[altI / 2].setWidth(70);
                                }
                }
                altI = 0;
                for (int mpan = 1; mpan < mpcount; mpan++) {
                        pnls = new RelativeLayout[tn];
                       // bg = new RadioGroup[tn];
                        mainPnls[mpan] = new LinearLayout(this);
                        mainPnls[mpan].setOrientation(LinearLayout.VERTICAL);
                        j = 0;
                        for (int i = 0; i < tn; i++) {
                                // teams[altI].setText("t" + (i + 1));
                                //teams[altI].setTextColor(Color.BLACK);
                                if (i % 2 == 0) {
                                       // bg[j] = new RadioGroup(this);
                                        //bg[j].addView(teams[altI]);
                                        if (tn > 1) {
                                               // bg[j].addView(teams[altI + 1]);
                                        }
                                        pnls[j] = new RelativeLayout(this);
                                       // pnls[j].addView(bg[j]);
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
                        if (manualState){
                               // teams[i].setEnabled(true);
                        }
                        else if (!manualState){
                                dbleTeams[i/2].setEnabled(true);
                        		dbleTeams[i/2].setOnClickListener(scrBrdBtn);
                        		dbleTeams[i/2].setText(trnmntTeams[i/2] + "\n" +trnmntTeams[i/2+1]);
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
                                teams[input].setEnabled(false);
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
        
        // automated from ScoreBoard
        private void autoFromScoreBoard(int input) {
                if (input != altI - 1) {
                        teams[teamNum + input / 2].setEnabled(true);
                        teams[teamNum + input / 2].setText(teams[input].getText().toString());
                        teams[input].setEnabled(false);
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
        */

        private OnClickListener scrBrdBtn = new OnClickListener() {
                @Override
                public void onClick(View v) {
                        int input = Integer.parseInt(v.getTag().toString());
                        if (input < teamNum - 1) {
                                tm1 = trnmntTeams[input * 2];
                                tm2 = trnmntTeams[input * 2 + 1];
                                dbleTeams[input].setEnabled(false);
                                dbleTeams[input].setBackgroundColor(Color.TRANSPARENT);
                        }

                        outPut.setText(tm1 + " " + tm2);
                        Intent myIntent = new Intent(Tournament.this, com.example.cs356.ScoreboardUI.class);
						String type = "tournament";
						myIntent.putExtra("TYPE",type);
						sb = new Scoreboard();
						sb.setName("Football");
						String file = "/data/data/com.example.cs356/" + sb.getName().toLowerCase() + ".bin";
						String teamNames = tm1 + "," + tm2;
						myIntent.putExtra("TEAMS", teamNames);
						myIntent.putExtra("FILE",file);
						startActivity(myIntent);
                        // to score builder
                        // (input*2) and (input*2 +1)  go to score builder, the winner
                        // (input) returns back to enable radiobutton
                        // enabled        teams[teamNum + input / 2].setEnabled(true);
                
                }
        };
}