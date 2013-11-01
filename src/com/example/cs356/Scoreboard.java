package com.example.cs356;

<<<<<<< HEAD
import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

=======
>>>>>>> Empire_Rafik

/**
 * 
 */

/**
 * @author Longazo1
 *
 */
public class Scoreboard {

<<<<<<< HEAD
	//LinearLayout Teams;
	//LinearLayout Scores;
	//LinearLayout Tbuttons;
	//LinearLayout Bbuttons;
	
	//Button dummy = new Button(this);
	
	private String name;
	private int teams;
	private int teamCount;
	private int nCount;
	private boolean hasNeutral;
	private int gridCount;
	private String[] teamNames = new String[4];
=======
	public Scoreboard() {
	}
	
	private String name;
	private int teamCount;
	private boolean hasNeutral;
	private int gridCount;
>>>>>>> Empire_Rafik
	private RButton[] teamButtons;
	private RButton[] neutralButtons;
	private ScoreCounter[] scores;
	public RuleSheet rules;
	
<<<<<<< HEAD
	
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
=======
	public void saveScore(){}
	
	public void endGame(){}
	
	public void options(){}
	
	public void mainMenu(){}
	
	public void ruleSheet(){}
	
	public void tournament(){}
>>>>>>> Empire_Rafik
	
	public int getTeamCount() {
		return teamCount;
	}
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	public RButton[] getTeamButtons() {
		return teamButtons;
	}
	public void setTeamButtons(RButton[] teamButtons) {
		this.teamButtons = teamButtons;
	}
	public RButton[] getNeutralButtons() {
		return neutralButtons;
	}
	public void setNeutralButtons(RButton[] neutralButtons) {
		this.neutralButtons = neutralButtons;
	}
}
