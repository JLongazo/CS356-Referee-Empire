package com.example.cs356;
import java.util.Timer;

public class RefereeTimer extends RButton{
	private int countUp;
	private boolean hasHours, hasMinutes;
	private Timer startTime, currentTime;
	
	public RefereeTimer(){
		
	}
	public void start(){
		
	}
	public void stop(){
		
	}
	public void reset(){
		
	}
	public void setCountUp(int cup){
		countUp = cup;
	}
	public void setStartTime(Timer st){
		startTime = st;
	}
	public void setCurrentTime(Timer ct){
		currentTime = ct;
	}
	public int getCountUp(){
		return countUp;
	}
	public boolean getHasHours(){
		return hasHours;
	}
	public boolean getHasMinuts(){
		return hasMinutes;
	}
	public Timer getStartTime(){
		return startTime;
	}
	public Timer getCurrentTime(){
		return currentTime;
	}

}
