package com.example.cs356;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreCounter extends RButton{
	private int score = 0;
	private int digits;
	private int increment;
	private int initial;
	private String name;
    private int count = 1;//number of scores
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_counter);
        
        final Button orderButton = (Button) findViewById(R.id.scoreID);
        orderButton.setOnClickListener(new OnClickListener() {

            @Override
         public void onClick(View v) {
     			orderButton.setText(Integer.toString(++score));
         }});
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

}

