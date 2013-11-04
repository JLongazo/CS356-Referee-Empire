package com.example.cs356;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	//@Override
	Button aButton;
	Button bButton;
	
	
	public void sendMessage(View view) 
	{
		Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreboardUI.class);
		//MainActivity.this.startActivity(myIntent);
		startActivity(myIntent);
		//setContentView(R.layout.scoreboard);
	}
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		aButton = (Button) this.findViewById(R.id.button1);
		bButton = (Button) this.findViewById(R.id.button2);



		aButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	//			aButton.setText("You Clicked Me");
				Intent myIntent = new Intent(MainActivity.this, com.example.cs356.ScoreboardUI.class);
				//MainActivity.this.startActivity(myIntent);
				startActivity(myIntent);
				//setContentView(R.layout.scoreboard);
					
				}});

		
		
		bButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		//		bButton.setText("You Clicked Me");
				//setContentView(R.layout.tototo);
					
				}});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
