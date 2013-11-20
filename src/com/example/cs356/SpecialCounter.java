package com.example.cs356;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class SpecialCounter extends RButton{
	private int count = 0;
    private String name;
    private int max = 99;
	private CharSequence display;
	private TextView d1;
	private TextView d2;
	private Typeface f1;

	
	public SpecialCounter(Context c, String n){
		super(c);
		name = "   " + n + "   ";
		f1 = Typeface.createFromAsset(c.getAssets(), "fonts/Athletic.TTF");
		LayoutParams params = new LayoutParams(50,100);
		params.weight = 1;
		params.gravity = Gravity.CENTER;
		setPadding(1,9,0,1);
		d1 = new TextView(c);
		d2 = new TextView(c);
		d1.setLayoutParams(params);
		d2.setLayoutParams(params);
		d1.setTextSize(25);
		d1.setTypeface(f1);
		d1.setGravity(Gravity.CENTER);
		d1.setTextColor(Color.BLACK);
		d2.setTextSize(25);
		d2.setTypeface(f1);
		d2.setGravity(Gravity.CENTER);
		d2.setTextColor(Color.BLACK);
		addView(d1);
		addView(d2);
		adjustText();
		
	}
	/*
	public void setInitial(int ini){
		for(int i = 0; i < counter_number; i++)
			spCounter[i].setInitial(ini);
	}
	*/
	public void increment(){
		if((count + 1) <= max)
			count++;
	}
	public void decrement(){
		if((count - 1) >= 0){
			count--;		
		}
	}
	public void reset(){
		count = 0;;
	}
	
	public int getSpScore(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
		adjustText();
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	
	public void adjustText(){
		String s = String.valueOf(count);
		if(s.length() < 2){
			s = "0" + s;
		}
		d1.setText(s.substring(0,1));
		d2.setText(s.substring(1,2));
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
}
