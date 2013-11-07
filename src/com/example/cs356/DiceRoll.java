package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
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
                if(rolled)
                        setBackgroundColor(Color.CYAN);
                else
                        setBackgroundColor(Color.MAGENTA);//WILL BE replaced by image
                adjustDice(Integer.toString(random));
                rolled = !rolled;
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
        @Override
        public void onClick(View arg0) {
                diceSides = 6;
                roll();
        }
}