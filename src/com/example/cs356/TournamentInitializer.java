package com.example.cs356;

import java.io.Serializable;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class TournamentInitializer implements Serializable{
	public int teamNumIni;
	public String[] trnmntTeamsIni;
	public boolean manualStateIni;
	public Scoreboard sb;
	public String[] label;
	public boolean[] wins;
	public String name;
	public boolean cont = false;

	public TournamentInitializer() {
		
	}	
}
