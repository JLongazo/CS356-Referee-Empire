package com.example.cs356;

import android.content.Context;

public class RToggle extends RButton{
	private boolean isOn;
	
	public RToggle(Context c){
		super(c);
	}
	public void changeToggle(){
		
	}
	public void setIsOn(boolean tog){
		isOn = tog;
	}
	public boolean getIsOn(){
		return isOn;
	}

}
