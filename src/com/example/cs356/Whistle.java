package com.example.cs356;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore.Audio.Media;
import android.view.View;


public class Whistle extends NeutralButton{
	
	
	public Whistle(Context c){
		super(c);
		setBackgroundResource(R.drawable.whistleb);
	}
	
	public void onClick(View arg0) {
		MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.whistle);
 		mp.start();
	}
}
