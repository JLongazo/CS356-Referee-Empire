package com.example.cs356;

import android.content.Context;
import android.view.View;

import java.util.Random;

public class DiceRoll extends NeutralButton{
	private int diceSides;
	private int diceNum;
	
	public DiceRoll(Context c){
		super(c);
	}
	public int roll(){
		Random generator = new Random();
		int random = generator.nextInt(diceSides) + 1;	
		return random;
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
	@Override
	public void onClick(View arg0) {
		setScore(roll());
	}
}
