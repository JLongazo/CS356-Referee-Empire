package com.example.cs356;

import java.io.Serializable;

/**
 * 
 */

/**
 * @author Longazo1
 *
 */
public class ScoreData implements Serializable{

	public ScoreData(String n, String t[], int w[], String d[]){
		name = n;
		teams = t;
		wins = w;
		details = d;
	}
	
	private String name;
	private String teams[];
	private int wins[];
	private String details[];
	
	public String getData(){
		String result = name + ": ";
		for(int i = 0; i < teams.length; i++){
			result += teams[i] + "- " + wins[i] + " ";
		}
		return result;
	}
	
	public String[] getDetails(){
		return details;
	}
}
