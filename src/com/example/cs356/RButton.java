package com.example.cs356;

import android.graphics.Path.Direction;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RButton {
	private String name;
	private ImageView image;
	private int gridLayout;
	
	public RButton(){
		
	}
	public void onTap(){
		
	}
	public void onHold(){	
	}
	public void onSwipe(Direction dir){
		
	}
	public void setName(String n){
		name = n;		
	}
	public void setImage(ImageView i){
		image = i;		
	}
	public void setGrid(int g){
		gridLayout = g;		
	}
	public String getName(){
		return name;		
	}
	public ImageView getImage(){
		return image;		
	}
	public int getGrid(){
		return gridLayout;		
	}
	

}
