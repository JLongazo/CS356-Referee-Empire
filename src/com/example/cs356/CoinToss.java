package com.example.cs356;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CoinToss extends NeutralButton{
        private int teamCount;
        private String name;
        ImageView display;
        LinearLayout mLinearLayout;
        
        public CoinToss(Context c, String name){
                super(c);
                this.name = name;
                setBackgroundResource(R.drawable.cointoss);

        }
        public void toss(){
                Random generator = new Random();
                int random = generator.nextInt(2) + 1;    
                if(random == 1)
                	setBackgroundResource(R.drawable.coinhead);
                else
                	setBackgroundResource(R.drawable.cointail);
        }   
        
        public void chooseTeam(){
                
        }
        public void setTeamCount(int tc){
                teamCount = tc;
        }
        public int getTeamCount(){
                return teamCount;
        }

		@Override
        public void onClick(View arg0) {
					
/*	1	doesn't work
 * 
 * 	
			ImageView tossImage = (ImageView) findViewById(R.layout.rbutton);
			tossImage.setBackgroundResource(R.drawable.toss_images);
			  AnimationDrawable tossAnimation = (AnimationDrawable) tossImage.getBackground();
			
			  tossAnimation.start();
			  
/*
 * 2 doesn't work
 * 
 * setBackgroundResource(R.drawable.gear);
try {
	  Thread.sleep(1000);
	} catch (InterruptedException ie) {
	}*/		
	toss();
        }
}