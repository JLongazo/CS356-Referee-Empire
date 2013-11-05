package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class RToggle extends RButton{
	private boolean isOn;
	private String name;
	
	public RToggle(Context c, String tname){
		super(c);
		name = tname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void changeToggle(){
		if(getIsOn())
			setBackgroundColor(Color.YELLOW);
		else
			setBackgroundColor(Color.GRAY);
	}
	public void setIsOn(boolean tog){
		isOn = tog;
	}
	public boolean getIsOn(){
		return isOn;
	}
	@Override
	public void onClick(View arg0) {
		setIsOn(!getIsOn());
		changeToggle();	
	}

}
