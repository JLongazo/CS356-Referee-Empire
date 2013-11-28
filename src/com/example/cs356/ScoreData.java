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

	public ScoreData(String n, String t[], int s[]){
		name = n;
		teams = t;
		scores = s;
	}
	
	private String name;
	private String teams[];
	private int scores[];
	
	public String getData(){
		String result = name + ": ";
		for(int i = 0; i < teams.length; i++){
			result += teams[i] + "- " + scores[i] + " ";
		}
		return result;
	}
}
