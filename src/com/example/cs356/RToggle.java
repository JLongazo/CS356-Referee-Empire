package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;

public class RToggle extends RButton{
        private boolean isOn;
        private String name;
        
        public RToggle(Context c, String tname){
                super(c);
                name = tname;
                setBackgroundResource(R.drawable.toggleoff);
        }
        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }
        public void changeToggle(){
                if(getIsOn())
                    setBackgroundResource(R.drawable.toggleon);
                else
                	setBackgroundResource(R.drawable.toggleoff);
        }
        public void setIsOn(boolean tog){
                isOn = tog;
                changeToggle();
        }
        public boolean getIsOn(){
                return isOn;
        }
        @Override
        public void onClick(View arg0) {
        		MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.toggle);
        		mp.start();
                setIsOn(!getIsOn());
                changeToggle();        
        }

}