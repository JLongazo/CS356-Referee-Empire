package com.example.cs356;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

public class RefereeTimer extends RButton{
	private boolean countUp;
	private boolean on = false;
	private long startTime;
	private long newTime;
	private CountDownTimer timer;
	private long inc = 1;
	int min;
	int sec;
	int mil;
	
	public RefereeTimer(Context c, long s, boolean cu){
		super(c);
		countUp = cu;
		startTime = s;
		if(cu){
			startTime = 6000000;
		}
		newTime = startTime;
        adjustText(startTime);
    }
	
	public void reset(){
		if(on){
			timer.cancel();
		}
		newTime = startTime;
		adjustText(newTime);
		on = false;
	}
	
	public void adjustText(long time){
		if(countUp){
			time = startTime - time;
		}
		if(time == 0){
			min = 0;
			sec = 0;
			mil = 0;
		} else {
			min = (int) (time/1000/60);
			sec = (int) ((time%60000)/1000);
			mil = (int) (time%1000)/10;
		}
		String minutes = Integer.toString(min);
		if(minutes.length() == 1){
			minutes = "0" + minutes;
		}
		String seconds = Integer.toString(sec);
		if(seconds.length() == 1){
			seconds = "0" + seconds;
		}
		String millis = Integer.toString(mil);
		if(millis.length() == 1){
			millis = "0" + millis;
		}
		String timeS = minutes + ":" + seconds + ":" + millis;
		setText(timeS);
	}
	
	@Override
	public void onClick(View v) {
		if(!on){
			timer = new CountDownTimer(newTime, inc) {

			     public void onTick(long millisUntilFinished) {
				        adjustText(millisUntilFinished);
				     }

				     public void onFinish() {
				    	 adjustText(0);
				    	MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.buzzer);
				 		mp.start();
				     }
			  };
			timer.start();
			on = true;
		}
		else{
			timer.cancel();
			newTime = (min*1000*60) + (sec*1000) + mil;
			if(countUp){
				newTime = startTime - newTime;
			}
			on = false;
		}
	}
	
	public boolean onLongClick(View v){
		reset();
		return true;
	}
	
	public void setCountUp(boolean cup){
		countUp = cup;
	}
	public boolean getCountUp(){
		return countUp;
	}
	public long getMillis() {
		return newTime;
	}
	public void setMillis(long millis) {
		this.newTime = millis;
		adjustText(newTime);
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	

}
