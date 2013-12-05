/**
 * 
 */
package com.example.cs356;

import java.io.Serializable;

/**
 * @author Longazo1
 *
 */
public class ContinueData implements Serializable{
	
	private boolean check;
	
	private RoundData rounds;
	
	private int betNum;
	
	private int incr = 1;
	
	private int scores[] = new int[4];
	
	private String tButtons[] = new String[8];
	
	private String bButtons[] = new String[8];
	
	private Scoreboard sb;
	
	public boolean isCheck(){
		return check;
	}
	
	public void setCheck(boolean c){
		check = c;
	}
	
	public void reset(){
		scores = new int[4];
		tButtons = new String[8];
		bButtons = new String[8];
	}

	public void setRounds(RoundData r){
		rounds = r;
	}
	
	public RoundData getRounds(){
		return rounds;
	}
	
	public void setBet(int b){
		betNum = b;
	}
	
	public int getBet(){
		return betNum;
	}
	
	public Scoreboard getSb() {
		return sb;
	}
	
	public void setScore(int score, int loc){
		scores[loc] = score;
	}
	
	public int getScore(int loc){
		return scores[loc];
	}
	
	public void setTButton(String val, int loc){
		tButtons[loc] = val;
	}
	
	public String getTButton(int loc){
		return tButtons[loc];
	}
	
	public void setBButton(String val, int loc){
		bButtons[loc] = val;
	}
	
	public String getBButton(int loc){
		return bButtons[loc];
	}

	public void setSb(Scoreboard sb) {
		this.sb = sb;
	}

	public int getIncr() {
		return incr;
	}

	public void setIncr(int incr) {
		this.incr = incr;
	}
}
