package com.example.cs356;

public class TournamentInitializer {
	public static int teamNumIni;
	public static String[] trnmntTeamsIni;
	public static boolean manualStateIni;

	public TournamentInitializer(int tn, String[] trnT, int ms) {
		teamNumIni = tn;
		trnmntTeamsIni = trnT;
		if (ms == 1)
			manualStateIni = true;
		else
			manualStateIni = false;
	}	
}
