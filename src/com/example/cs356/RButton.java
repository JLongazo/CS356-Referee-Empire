package com.example.cs356;

import android.app.Activity;
import android.graphics.Path.Direction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


public class RButton extends Activity implements OnClickListener  {
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
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
