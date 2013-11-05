package com.example.cs356;
import android.content.Context;
import android.media.SoundPool;
import android.provider.MediaStore.Audio.Media;


public class Whistle extends NeutralButton{
	private SoundPool sound;
	//Media sound2; //OR
	
	public Whistle(Context c){
		super(c);
	}
	
	public void playSound(){
		
	}
	public void setSound(SoundPool sp){
		sound = sp;
	}
	public SoundPool getSound(){
		return sound;
	}
}
