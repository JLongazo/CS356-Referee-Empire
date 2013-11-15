package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import java.util.Random;

public class DiceRoll extends NeutralButton{
        private int diceSides;
        private int diceNum;
        private String name;
        private boolean rolled = false;
        
        public DiceRoll(Context c, String name){
                super(c);
                this.name = name;
                adjustDice("dice");
        }
        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }
        public void roll(){
                Random generator = new Random();
                int random = generator.nextInt(diceSides) + 1;                
                adjustDice(Integer.toString(random));

        }   
        
        public void adjustDice(String display){
    		setText(display);
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
        public void animation(){

        	CountDownTimer ctimer = new CountDownTimer(1000, 50) {

			     public void onTick(long millisUntilFinished) {
			    	 if(rolled)
			    	 	setBackgroundResource(R.drawable.gear);
			    	 else
			    		 setBackgroundResource(R.drawable.geara);
			    	 rolled = !rolled;

				     }

				     public void onFinish() {
				    	 setBackgroundResource(R.drawable.blankwhite);
				    	 roll();

				  //  	MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.buzzer);
				// 		mp.start();
				     }
			  };
			ctimer.start();
	
        }
        @Override
        public void onClick(View arg0) {
                diceSides = 6;
                animation();
        }
}