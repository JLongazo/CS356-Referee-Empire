package com.example.cs356;

import java.io.Serializable;

import android.content.Context;
import android.graphics.Path.Direction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RButton extends LinearLayout implements OnClickListener, OnLongClickListener, Serializable{
	private String name;
	private ImageView image;
	private int grid;
	private Context con;
	private TextView t;
	
	public RButton(Context c){
		super(c);
		con = c;
		setOnClickListener(this);
		setOnLongClickListener(this);
		t = new TextView(con);
		addView(t);
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


	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onLongClick(View arg0) {
		return true;
	}


	public void setText(String string) {
		t.setText(string);
	}
	
	public CharSequence getText(){
		return t.getText();
	}
	

}
