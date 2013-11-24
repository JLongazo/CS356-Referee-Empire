package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import java.util.Random;

public class DiceRoll extends NeutralButton{
	
        private int diceSides;
        private int diceNum;
        private String name;
        private boolean rolled = false;
        private CountDownTimer ct;
        private Random r;
        
        public DiceRoll(Context c, String name){
                super(c);
                this.name = name;
                diceSides = 6;
                setBackgroundResource(R.drawable.dice6);
        }
        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }  
        
        public void animateDice(){
        	long time = 2000;
        	long inc = 50;
        	r = new Random();
            
        	ct = new CountDownTimer(time,inc) {
			     public void onTick(long millisUntilFinished) {
			    	 int random = r.nextInt(diceSides) + 1;
				        switch(random){
				        case 1:
				        	setBackgroundResource(R.drawable.dice1);
				        	break;
				        case 2:
				        	setBackgroundResource(R.drawable.dice2);
				        	break;
				        case 3:
				        	setBackgroundResource(R.drawable.dice3);
				        	break;
				        case 4:
				        	setBackgroundResource(R.drawable.dice4);
				        	break;
				        case 5:
				        	setBackgroundResource(R.drawable.dice5);
				        	break;
				        case 6:
				        	setBackgroundResource(R.drawable.dice6);
				        	break;
				        }
				     }

				     public void onFinish() {
			}};
			ct.start();
				     
        }
        
        public void setDiceSide(int ds){
                diceSides = ds;
        }
        public void setDiceNum(int dn){
                diceNum = dn;
        }
        public int getDiceSide(){
                return diceSides;
        }
        public int getDiceNum(){
                return diceNum;
        }
        @Override
        public void onClick(View arg0) {
        	MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.dice);
        	mp.start();
            animateDice();
        }
}