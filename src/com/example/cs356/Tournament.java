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
import android.widget.ScrollView;

public class Tournament extends Activity {
	LinearLayout[] mainPnls;
	RelativeLayout[] pnls;
	ScrollView scroll;

	int mpcount = 1;

	RadioGroup[] bg;
	RButton[] dbleTeams;
	Button shfl, back, save, cntin, outPut, mnlBtn;
	RadioButton[] teams;
	// //////////////////////////////////////////////////////// from tournament
	// builder
	private int teamNum ; // = 32;
	String[] trnmntTeams ;/*
						  = { "Empire", "CA", "LA", "CPP", "TA", "MN", "OL",
						  "AB", "CD", "EF", "GH", "BO", "CHEVY", "FORD", "TX",
						  "AR", "Empire", "CA", "LA", "CPP", "TA", "MN", "OL",
						  "AB", "CD", "EF", "GH", "BO", "CHEVY", "FORD", "TX",
						  "AR" };*/
						 
	private boolean manualState;// = false;
	// /////////////////////////////////////////////////////// from tournament
	// builder
	private LinearLayout mainFrame;
	int altI = 0;
	int j = 0;
	String tm1, tm2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		
		teamNum = TournamentInitializer.teamNumIni;
		trnmntTeams = TournamentInitializer.trnmntTeamsIni;
		manualState = TournamentInitializer.manualStateIni;



		scroll = new ScrollView(this);
		scroll.setVerticalScrollBarEnabled(true);
		scroll.setHorizontalScrollBarEnabled(true);

	//	manualState = false;// gets from outside from tournament builder

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
		outPut.setText("Output");
		
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

		teams = new RadioButton[2 * teamNum - 1];
		dbleTeams = new RButton[teamNum];
		for (altI = 0; altI < 2 * teamNum - 1; altI++) {
			teams[altI] = new RadioButton(this);
			teams[altI].setTag("" + altI);
			teams[altI].setOnClickListener(rbuttons);
			teams[altI].setEnabled(false);
			if (!manualState)
				if (altI % 2 == 0) {
					dbleTeams[altI / 2] = new RButton(this);
					dbleTeams[altI / 2].setTag("" + altI / 2);
					dbleTeams[altI / 2].setBackgroundColor(Color.YELLOW);
					dbleTeams[altI / 2].setOnClickListener(scrBrdBtn);
					dbleTeams[altI / 2].setEnabled(false);
					dbleTeams[altI / 2].setHeight(90);
					dbleTeams[altI / 2].setWidth(35);
				}
		}
		altI = 0;
		for (int mpan = 1; mpan < mpcount; mpan++) {
			pnls = new RelativeLayout[tn];
			bg = new RadioGroup[tn];
			mainPnls[mpan] = new LinearLayout(this);
			mainPnls[mpan].setOrientation(LinearLayout.VERTICAL);
			j = 0;
			for (int i = 0; i < tn; i++) {
				// teams[altI].setText("t" + (i + 1));
				teams[altI].setTextColor(Color.BLACK);
				if (i % 2 == 0) {
					bg[j] = new RadioGroup(this);
					bg[j].addView(teams[altI]);
					if (tn > 1) {
						bg[j].addView(teams[altI + 1]);
					}
					pnls[j] = new RelativeLayout(this);
					pnls[j].addView(bg[j]);
					if (!manualState)
						if (mpan != mpcount - 1)
							pnls[j].addView(dbleTeams[altI / 2]);
					pnls[j].setBackgroundResource(R.drawable.tourback);
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
		scroll.addView(mainFrame);
		setContentView(scroll);
	}
	

	public int getTeams() {
		return teamNum;
	}

	public void setTeams(int teamNum) {
		this.teamNum = teamNum;
	}

	public String[] getTrnmntTeams() {
		return trnmntTeams;
	}

	public void setTeamNames(String[] trnmntTeams) {
		this.trnmntTeams = trnmntTeams;
	}

	public boolean isManualState() {
		return manualState;
	}

	public void setManualState(int mn) {
		if (mn == 1)
			this.manualState = true;
		else
			manualState = false;
	}

	public void setName(String gameName) {

	}

	public String getName() {
		return "name";
	}

	public String[] makeShuffle() {
		shfl.setText("shuffled");
		shfl.setEnabled(false);
		
		String ar[] = new String[4];
		for(int i = 0; i<4; i++)
			ar[i] = TournamentInitializer.trnmntTeamsIni[i];
		
		Collections.shuffle(Arrays.asList(trnmntTeams));
		for (int i = 0; i < teamNum; i++) {
			teams[i].setText(trnmntTeams[i]);
			if (manualState)
				teams[i].setEnabled(true);
			if (!manualState)
				dbleTeams[i / 2].setEnabled(true);
		}
		return trnmntTeams;
	}

	// for Manual
	private OnClickListener rbuttons = new OnClickListener() {
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
			teams[teamNum + input / 2].setText(teams[input].getText()
					.toString());
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

	private OnClickListener scrBrdBtn = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int input = Integer.parseInt(v.getTag().toString());
			if (input < teamNum - 1) {
				tm1 = teams[input * 2].getText().toString();
				tm2 = teams[input * 2 + 1].getText().toString();
				dbleTeams[input].setEnabled(false);
				dbleTeams[input].setBackgroundColor(Color.TRANSPARENT);
			}

			outPut.setText(tm1 + " " + tm2);// to score builder
			// (input*2) and (input*2 +1) go to score builder, the winner
			// (input) returns back to enable radiobutton
			// enabled teams[teamNum + input / 2].setEnabled(true);

		}
	};
}
