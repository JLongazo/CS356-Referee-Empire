package com.example.cs356;
import java.util.Scanner;

import android.content.Context;
import android.view.View;

public class SpecialCounter extends RButton{
	private int count = 0;
    private String name;
    private int max = 99;
	private CharSequence display;
	
	public SpecialCounter(Context c, String n){
		super(c);
		name = "   " + n + "   ";
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
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	
	public void adjustText(){
		display = String.valueOf(count);
		while(display.length() < 2){
			display = "0" + display;
		}
		setText(display);
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
