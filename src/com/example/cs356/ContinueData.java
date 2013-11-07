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
	
	private int scores[] = new int[4];
	
	private String tButtons[] = new String[8];
	
	private String bButtons[] = new String[8];
	
	private Scoreboard sb;
	
	public boolean isCheck(){
		return check;
	}
	
	public void changeCheck(){
		check = !check;
	}
	
	public void reset(){
		scores = new int[4];
		tButtons = new String[8];
		bButtons = new String[8];
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
}
