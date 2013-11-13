package com.example.cs356;

import java.io.Serializable;
import java.util.ArrayList;

public class Scores implements Serializable{

	private ArrayList<ScoreData> scores = new ArrayList<ScoreData>();
	
	public ScoreData getScore(int location){
		return scores.get(location);
	}
	
	public int size(){
		return scores.size();
	}
	
	public void add(ScoreData score){
		scores.add(score);
		if(scores.size() > 10){
			scores.remove(0);
		}
	}
}
