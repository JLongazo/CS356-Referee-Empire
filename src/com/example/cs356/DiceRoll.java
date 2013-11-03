package com.example.cs356;

import android.content.Context;

public class DiceRoll extends NeutralButton{
	private int diceSides;
	private int diceNum;
	
	public DiceRoll(Context c){
		super(c);
	}
	public void roll(){
		
	}   
	public void setDiceSide(int ds){
		diceSides = ds;
	}
	public void setDiceNum(int dn){
		diceNum = dn;
	}
	public int getDiceSide(){
		return diceSides;
	}
	public int getDiceNum(){
		return diceNum;
	}
}
