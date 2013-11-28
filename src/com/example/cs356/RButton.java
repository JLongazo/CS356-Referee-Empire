package com.example.cs356;

import android.content.Context;
import android.graphics.Path.Direction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RButton extends Button implements OnClickListener, OnLongClickListener{
	private String name;
	private ImageView image;
	private int grid;
	
	public RButton(Context c){
		super(c);
		setOnClickListener(this);
		setOnLongClickListener(this);
	}
	
	
	public void setName(String n){
		name = n;		
	}
	public void setImage(ImageView i){
		image = i;		
	}
	public void setGrid(int g){
		grid = g;		
	}
	public String getName(){
		return name;		
	}
	public ImageView getImage(){
		return image;		
	}
	public int getGrid(){
		return grid;		
	}
	public void setLaout(){
		//	
	}
	


	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onLongClick(View arg0) {
		return true;
	}
	

}
