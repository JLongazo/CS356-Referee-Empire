package com.example.cs356;

import android.content.Context;

public class CoinToss extends NeutralButton{
	private int teamCount;
	
	public CoinToss(Context c){
		super(c);
	}
	public void chooseTeam(){
		
	}
	public void setTeamCount(int tc){
		teamCount = tc;
	}
	public int getTeamCount(){
		return teamCount;
	}
  
}
