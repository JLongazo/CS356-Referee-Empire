package com.example.cs356;


/**
 * 
 */

/**
 * @author Longazo1
 *
 */
public class Scoreboard {

	public Scoreboard() {
	}
	
	private String name;
	private int teamCount;
	private boolean hasNeutral;
	private int gridCount;
	private Button[] teamButtons;
	private Button[] neutralButtons;
	private ScoreCounter[] scores;
	public RuleSheet rules;
	
	public void saveScore(){}
	
	public void endGame(){}
	
	public void options(){}
	
	public void mainMenu(){}
	
	public void ruleSheet(){}
	
	public void tournament(){}
	
	public int getTeamCount() {
		return teamCount;
	}
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	public Button[] getTeamButtons() {
		return teamButtons;
	}
	public void setTeamButtons(Button[] teamButtons) {
		this.teamButtons = teamButtons;
	}
	public Button[] getNeutralButtons() {
		return neutralButtons;
	}
	public void setNeutralButtons(Button[] neutralButtons) {
		this.neutralButtons = neutralButtons;
	}
}
