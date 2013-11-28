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
        private CountDownTimer ct;
        private Random r;
        
        public CoinToss(Context c, String name){
                super(c);
                this.name = name;
                setBackgroundResource(R.drawable.coinh);
        }
        public void toss(){
        		MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.coin);
        		mp.start();
                r = new Random();
                long time = 2000;
                long inc = 50;
            	r = new Random();
                
            	ct = new CountDownTimer(time,inc) {
    			     public void onTick(long millisUntilFinished) {
    			    	 	int random = r.nextInt(2) + 1;        
    			    	 	if(random == 1)
    			    	 		setBackgroundResource(R.drawable.coinh);
    			    	 	else
    			    	 		setBackgroundResource(R.drawable.coint);
    			     }

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						
					}
            	};
            	ct.start();
        }   
        
        @Override
        public void onClick(View arg0) {
                toss();
        }
}