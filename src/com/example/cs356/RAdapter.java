package com.example.cs356;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;

public class RAdapter extends ArrayAdapter<String>{
	
	public RAdapter(Context c, int l, String[] s, Typeface f){
		super(c,l,s);
		this.c = c;
		this.l = l;
		this.s = s;
		this.f = f;
	}
	
	private Context c;
	private int l;
	private String[] s;
	private Typeface f;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	    String ss = s[position];

	    TextView tv = new TextView(c);
	    int height = 130 + (ss.length()/30 * 30);
	    AbsListView.LayoutParams tl = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height);
	    tv.setText(ss.toUpperCase());
	    tv.setLayoutParams(tl);
	    tv.setGravity(Gravity.CENTER_VERTICAL);
	    tv.setTypeface(f);
	    tv.setTextColor(Color.WHITE);
	    tv.setTextSize(20);
	    tv.setPadding(20,0,20,0);
	    tv.setBackgroundResource(R.drawable.background5);
	    return tv;
	}

}
