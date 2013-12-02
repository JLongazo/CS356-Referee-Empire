package com.example.cs356;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RefereeTimer extends RButton{
	private boolean countUp;
	private boolean on = false;
	private long startTime;
	private long newTime;
	private CountDownTimer timer;
	private long inc = 1;
	private int min;
	private int sec;
	private int mil;
	private TextView m1;
	private TextView m2;
	private TextView s1;
	private TextView s2;
	private TextView mi1;
	private TextView mi2;
	private TextView c1;
	private TextView c2;
	private Typeface f1;
	private LayoutParams params;
	
	
	
	public RefereeTimer(Context c, long s, boolean cu){
		super(c);
		countUp = cu;
		startTime = s;
		if(cu){
			startTime = 6000000;
		}
		setGravity(Gravity.CENTER);
		f1 = Typeface.createFromAsset(c.getAssets(), "fonts/Athletic.TTF");
		LayoutParams cParams = new LayoutParams(pxtodp(ScoreboardUI.BUTTON_W)/4,pxtodp(ScoreboardUI.BUTTON_H));
		params = new LayoutParams(pxtodp(ScoreboardUI.BUTTON_W)/2,pxtodp(ScoreboardUI.BUTTON_H));
		params.weight = 1;
		params.gravity = Gravity.CENTER;
		m1 = new TextView(c);
		m2 = new TextView(c);
		s1 = new TextView(c);
		s2 = new TextView(c);
		mi1 = new TextView(c);
		mi2 = new TextView(c);
		c1 = new TextView(c);
		c2 = new TextView(c);
		m1.setPadding(pxtodp(6),pxtodp(10),0,0);
		m2.setPadding(pxtodp(6),pxtodp(10),0,0);
		s1.setPadding(pxtodp(6),pxtodp(10),0,0);
		s2.setPadding(pxtodp(6),pxtodp(10),0,0);
		mi1.setPadding(pxtodp(3),pxtodp(10),0,0);
		mi2.setPadding(pxtodp(3),pxtodp(10),0,0);
		c1.setText(":");
		c2.setText(":");
		c2.setPadding(pxtodp(6),0,0,0);
		c1.setPadding(pxtodp(3),0,0,0);
		setUp(m1);
		setUp(m2);
		setUp(c2);
		c2.setLayoutParams(cParams);
		setUp(s1);
		setUp(s2);
		setUp(c1);
		c1.setLayoutParams(cParams);
		setUp(mi2);
		setUp(mi1);
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
		String timeS = minutes + seconds + millis;
		m1.setText(timeS.substring(0,1));
		m2.setText(timeS.substring(1,2));
		s1.setText(timeS.substring(2,3));
		s2.setText(timeS.substring(3,4));
		mi1.setText(timeS.substring(4,5));
		mi2.setText(timeS.substring(5,6));
	}
	
	@Override
	public void onClick(View v) {
		if(!on){
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click);
			mp.start();
			timer = new CountDownTimer(newTime, inc) {

			     public void onTick(long millisUntilFinished) {
				        adjustText(millisUntilFinished);
				     }

				     public void onFinish() {
				    	 adjustText(0);
				    	MediaPlayer mp2 = MediaPlayer.create(getContext(), R.raw.buzzer);
				 		mp2.start();
				     }
			  };
			timer.start();
			on = true;
		}
		else{
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click2);
			mp.start();
			timer.cancel();
			newTime = (min*1000*60) + (sec*1000) + mil;
			if(countUp){
				newTime = startTime - newTime;
			}
			on = false;
		}
	}
	
	public boolean onLongClick(View v){
		MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.click2);
		mp.start();
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
	
	private void setUp(TextView t){
		addView(t);
		t.setLayoutParams(params);
		t.setTextSize(25);
		t.setTypeface(f1);
		t.setGravity(Gravity.CENTER);
		t.setTextColor(Color.BLACK);
	}
	
	public int pxtodp(int px){
		DisplayMetrics displayMetrics = RefereeTimer.this.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}

}
