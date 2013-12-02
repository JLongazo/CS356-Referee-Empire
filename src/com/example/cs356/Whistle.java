package com.example.cs356;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore.Audio.Media;
import android.view.View;


public class Whistle extends NeutralButton{
	
	
	@SuppressWarnings("deprecation")
	public Whistle(Context c){
		super(c);
		setBackgroundResource(R.drawable.whistleb);
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] {android.R.attr.state_pressed},
		    getResources().getDrawable(R.drawable.whistleb2));
		states.addState(new int[] { },
		    getResources().getDrawable(R.drawable.whistleb));
		setBackgroundDrawable(states);
	}
	
	public void onClick(View arg0) {
		MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.whistle);
 		mp.start();
	}
}
