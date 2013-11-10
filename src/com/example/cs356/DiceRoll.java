package com.example.cs356;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import java.util.Random;

public class DiceRoll extends NeutralButton{
        private int diceSides;
        private int diceNum;
        private String dice = "", name, value = "";
        private boolean rolled = false;
               
        public DiceRoll(Context c, String name, int diceNum, int diceSides){
                super(c);
                this.name = name;
                this.diceNum = diceNum;
                this.diceSides = diceSides;
               setText(Integer.toString(diceNum) + "   dice");  
                dividieGround();
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
                value = Integer.toString(random);
  
        }   
        
        public void adjustDice(){
        	for(int i = 0; i < diceNum; i++){
        		roll();
        		dice += "  " + value + "  ";
        	}	
    		setText(dice);
    		dice = "";
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
        public void dividieGround(){
        	switch(diceNum){
        	case (1):
        		setBackgroundResource(R.drawable.singlep);
        		break;
        	case (2):
        		setBackgroundResource(R.drawable.twop);
        		break;
        	case (3):
        		setBackgroundResource(R.drawable.threep);
        		break;
        	case (4):
        		setBackgroundResource(R.drawable.four_p);
        		break;
 
        	
        	}
        	
        }
        @Override
        public void onClick(View arg0) {
                adjustDice();
        }
}