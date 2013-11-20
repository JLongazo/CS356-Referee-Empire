package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreCounter extends RButton{
	private int score;
	private int digits;
	private int increment;
	private int initial;
	private int max;
	private CharSequence display;
	private TextView d1;
	private TextView d2;
	private TextView d3;
	private TextView d4;
	private Typeface f1;
	
	public ScoreCounter(int ini, int increment, int digits, Context c){
		super(c);
		this.score = ini;
		this.digits = digits;	
		this.increment = increment;
		f1 = Typeface.createFromAsset(c.getAssets(), "fonts/Athletic.TTF");
		LayoutParams params = new LayoutParams(75,150);
		params.weight = 1;
		params.gravity = Gravity.CENTER;
		setPadding(1,13,0,1);
		setGravity(Gravity.CENTER);
		d1 = new TextView(c);
		d2 = new TextView(c);
		d3 = new TextView(c);
		d4 = new TextView(c);
		d1.setTextSize(32);
		d1.setTypeface(f1);
		d1.setGravity(Gravity.CENTER);
		d1.setTextColor(Color.BLACK);
		d2.setTextSize(32);
		d2.setTypeface(f1);
		d2.setGravity(Gravity.CENTER);
		d2.setTextColor(Color.BLACK);
		d3.setTextSize(32);
		d3.setTypeface(f1);
		d3.setGravity(Gravity.CENTER);
		d3.setTextColor(Color.BLACK);
		d4.setTextSize(32);
		d4.setTypeface(f1);
		d4.setGravity(Gravity.CENTER);
		d4.setTextColor(Color.BLACK);
		adjustText();
		switch(digits){
		case 1:
			max = 9;
			addView(d1,params);
			break;
		case 2:
			max = 99;
			addView(d1,params);
			addView(d2,params);
			break;
		case 3:
			max = 999;
			addView(d1, params);
			addView(d2, params);
			addView(d3, params);
			break;
		case 4:
			max = 9999;
			max = 999;
			addView(d1, params);
			addView(d2, params);
			addView(d3, params);
			addView(d4, params);
			break;
		}
	}
	
	public void adjustText(){
		String s = String.valueOf(score);
		while(s.length() < digits){
			s = "0" + s;
		}
		switch(digits){
		case 2:
			d1.setText(s.substring(0,1));
			d2.setText(s.substring(1,2));
			break;
		case 3:
			d1.setText(s.substring(0,1));
			d2.setText(s.substring(1,2));
			d3.setText(s.substring(2,3));
			break;
		case 4:
			d1.setText(s.substring(0,1));
			d2.setText(s.substring(1,2));
			d3.setText(s.substring(2,3));
			d4.setText(s.substring(3,4));
			break;
		}
		
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
	
	public void setScore(int ini){
		score = ini;
		adjustText();
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
