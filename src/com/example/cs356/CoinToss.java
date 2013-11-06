package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.Random;

public class CoinToss extends NeutralButton{
	private int teamCount;
	private String name;
	private boolean tossed = true;
	
	public CoinToss(Context c, String name){
		super(c);
		this.name = name;
		adjustCoin("Coin");
	}
	public void toss(){
		Random generator = new Random();
		int random = generator.nextInt(2) + 1;	
		if(tossed)
			setBackgroundColor(Color.BLUE);
		else
			setBackgroundColor(Color.GREEN);
		if(random == 1)
			adjustCoin("Head");
		else
			adjustCoin("Tail");
		tossed = !tossed;
	}   
	public void chooseTeam(){
		
	}
	public void setTeamCount(int tc){
		teamCount = tc;
	}
	public int getTeamCount(){
		return teamCount;
	}
	@Override
	public void onClick(View arg0) {
		toss();
	}
}
