package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import java.util.Random;

public class CoinToss extends NeutralButton{
        private int teamCount;
        private String name;
        private boolean tossed = true;
        
        public CoinToss(Context c, String name){
                super(c);
                this.name = name;
                setBackgroundResource(R.drawable.blankwhite);
        }
        public void toss(){
                Random generator = new Random();
                int random = generator.nextInt(2);        
                if(random == 1)
                	setBackgroundResource(R.drawable.head);

                else
                	setBackgroundResource(R.drawable.tail);
        }   
        
        public void adjustCoin(String display){
    		setText(display);
    	}
        
        public void animation(){

        	CountDownTimer ctimer = new CountDownTimer(1000, 50) {

			     public void onTick(long millisUntilFinished) {
			    	 if(tossed)
			    	 	setBackgroundResource(R.drawable.gear);
			    	 else
			    		 setBackgroundResource(R.drawable.geara);
			    	 tossed = !tossed;

				     }

				     public void onFinish() {
				    //	 setBackgroundResource(R.drawable.blankwhite);//
				    	 toss();

				  //  	MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.buzzer);
				// 		mp.start();
				     }
			  };
			ctimer.start();
        	
        	
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
        	animation();
                
        }
}