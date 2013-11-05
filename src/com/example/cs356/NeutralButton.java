package com.example.cs356;

import android.content.Context;

public class NeutralButton extends ScoreCounter{
	private String name;
	
	public NeutralButton(Context c){
		super(c);
	}
	public void displayGraphic(){
		
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
}
