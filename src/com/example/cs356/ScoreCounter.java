package com.example.cs356;
public class ScoreCounter extends RButton{
	private int score;
	private int digits;
	private int increment;
	private int initial;
	private String name;

	public ScoreCounter(){
		initial = 0;
		score = initial;
		digits = 2;
		increment = 1;		
	}
	
	public ScoreCounter(int ini, int increment, int digits){
		this.score = ini;
		this.digits = digits;	
		this.increment = increment;
	}
	public void setInitial(int ini){
		initial = ini;
	}
	public void increment(){
		score += increment;
	}
	public void decrement(){
		if((score - increment) >= 0){
		score -= increment;		
		}
	}
	public void reset(){
		score = initial;
	}
   
	public void setIncrement(int inc){
		increment = inc;
	}
	
	public void setDigits(int dig){
		digits = dig;
	}
	public int getDigits(){
		return digits;
	}
	
	public int getScore(){
		return score;
	}
	public int getIncrement(){
		return increment;
	}
}
