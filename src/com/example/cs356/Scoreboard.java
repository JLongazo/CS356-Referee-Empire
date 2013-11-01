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
	private RButton[] teamButtons;
	private RButton[] neutralButtons;
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
