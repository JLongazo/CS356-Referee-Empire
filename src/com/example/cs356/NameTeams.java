package com.example.cs356;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class NameTeams extends Activity {

	private int teamCount = 0;
	private int fieldCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_teams);
		
		teamCount = InputMenu.sb2.getTeams();
		fieldCount = teamCount + 1;
		
		
		for(int i = 0; i < fieldCount; i++) {
			
		EditText newName = new EditText(this);
		
		}
		
		
	}

}
