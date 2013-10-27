package com.example.cs356;

public class DiceRoll extends NeutralButton{
	private int diceSides;
	private int diceNum;
	
	public DiceRoll(){
		
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
