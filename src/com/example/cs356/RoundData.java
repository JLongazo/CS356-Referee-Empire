package com.example.cs356;

import java.io.Serializable;
import java.util.ArrayList;

public class RoundData implements Serializable{

	public RoundData(){
		rounds = new ArrayList<CharSequence>();
		roundCount = 1;
	}
	
	private ArrayList<CharSequence> rounds;
	
	private int roundCount;
	
	public void nextRound(String data){
		rounds.add(data);
		roundCount++;
	}
	
	public ArrayList<CharSequence> getData(){
		return rounds;
	}
	
	public int getRoundCount(){
		return roundCount;
	}
}