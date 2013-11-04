package com.example.cs356;

import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * 
 */

/**
 * @author Longazo1
 *
 */
public class Scoreboard {

	//LinearLayout Teams;
	//LinearLayout Scores;
	//LinearLayout Tbuttons;
	//LinearLayout Bbuttons;
	
	//Button dummy = new Button(this);
	
	private String name;
	private int teams;
	private int tCount;
	private int bCount;
	private int digits;
	private boolean hasNeutral;
	private String[] teamNames = new String[3];
	private String[] tNames = new String[16];
	private String[] bNames = new String[16];
	private char[] topButtons = new char[16];
	private char[] bottomButtons = new char[16];
	public RuleSheet rules;
	
	
	/**
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.scoreboard);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//Teams = (LinearLayout) this.findViewById(R.id.Teams);
		//Scores = (LinearLayout) this.findViewById(R.id.Scores);
		//Tbuttons = (LinearLayout) this.findViewById(R.id.Tbuttons);
		//Bbuttons = (LinearLayout) this.findViewById(R.id.Bbuttons);
		
		//Teams.addView(dummy);
	}
	*/
	
	public void saveScore(){}
	
	public void endGame(){
		saveScore();
		//setContentView(R.layout.activity_main);
	}
	
	public void options(){
		//setContentView(options list);
	}
	
	public void mainMenu(){
		//setContentView(R.layout.activity_main);
	}
	
	public void ruleSheet(){
		//setContentView(rulesheet list);
	}
	
	public void tournament(){
		//implement later
	}
	
	public String getName(){
		return name;
	}
	
	public int getTCount() {
		return tCount;
	}
	public void setTCount(int tCount) {
		this.tCount = tCount;
	}
	public char[] getTopButtons() {
		return topButtons;
	}
	public void setTopButtons(char[] topButtons) {
		this.topButtons = topButtons;
	}
	public char[] getBottomButtons() {
		return bottomButtons;
	}
	public void setBottomButtons(char[] bottomButtons) {
		this.bottomButtons = bottomButtons;
	}

	public int getTeams() {
		return teams;
	}

	public void setTeams(int teams) {
		this.teams = teams;
	}

	public int getBCount() {
		return bCount;
	}

	public void setBCount(int bCount) {
		this.bCount = bCount;
	}

	public boolean isHasNeutral() {
		return hasNeutral;
	}

	public void setHasNeutral(boolean hasNeutral) {
		this.hasNeutral = hasNeutral;
	}

	public String[] getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(String[] teamNames) {
		this.teamNames = teamNames;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public String[] gettNames() {
		return tNames;
	}

	public void settNames(String[] tNames) {
		this.tNames = tNames;
	}

	public String[] getbNames() {
		return bNames;
	}

	public void setbNames(String[] bNames) {
		this.bNames = bNames;
	}
}
