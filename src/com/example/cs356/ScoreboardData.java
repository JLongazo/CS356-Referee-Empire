package com.example.cs356;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoreboardData implements Serializable{
	
	public ScoreboardData(String sb[]){
		for(String scoreb : sb){
			scoreboards.add(scoreb);
		}
	}
	
	private ArrayList<String> scoreboards = new ArrayList<String>();
	
	public String[] getSbs(){
		String sbs[] = new String[scoreboards.size()];
		for(int i = 0; i < sbs.length; i++){
			sbs[i] = scoreboards.get(i);
		}
		return sbs;
	}
	
	public void addSb(String name){
		scoreboards.add(name);
	}
	
	public void remove(int loc){
		scoreboards.remove(loc);
	}

}
