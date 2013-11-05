package com.example.cs356;

import android.content.Context;
import android.view.View;

public class ScoreCounter extends RButton{
	private int score;
	private int digits;
	private int increment;
	private int initial;
	private int max;
	private CharSequence display;
	
	
	public ScoreCounter(int ini, int increment, int digits, Context c){
		super(c);
		this.score = ini;
		this.digits = digits;	
		this.increment = increment;
		adjustText();
		switch(digits){
		case 1:
			max = 9;
			break;
		case 2:
			max = 99;
			break;
		case 3:
			max = 999;
			break;
		case 4:
			max = 9999;
			break;
		}
	}
	
	public ScoreCounter(Context c) {//for dice
		super(c);
		// TODO Auto-generated constructor stub
	}

	public void adjustText(){
		display = String.valueOf(score);
		while(display.length() < digits){
			display = "0" + display;
		}
		setText(display);
	}
	
	public void setScore(int s){//for dice and coin
		score = s;
	}
	@Override
	public void onClick(View v) {
		increment();
		adjustText();
	}
	
	@Override
	public boolean onLongClick(View arg0) {
		decrement();
		adjustText();
		return true;
	}
	
	public void setInitial(int ini){
		initial = ini;
	}
	public void increment(){
		if((score + increment) <= max)
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
