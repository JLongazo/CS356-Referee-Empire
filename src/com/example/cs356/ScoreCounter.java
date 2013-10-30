package com.example.cs356;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ScoreCounter extends RButton{
	private int score;
	private int digits;
	private int increment;
	private int initial;
	private String name;
    private TextView scoreView;
    private int count = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorecounter);
        scoreView = (TextView) findViewById(R.id.score);
        scoreView.setOnClickListener(this);
    }

	public ScoreCounter(){
		initial = 0;
		score = initial;
		digits = 2;
		increment = 1;		
	}
	public ScoreCounter(int ini, int increment, int digits){
		initial = ini;
		this.score = initial;
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
        if((score - increment) >= 0)
		    score -= increment;		
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
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.score) {
			score++;
			scoreView.setText(Integer.toString(score));
		}
	}
}
