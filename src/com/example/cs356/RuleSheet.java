package com.example.cs356;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RuleSheet extends Activity {
	
	private LinearLayout background;
	private Button back;
	private ListView rules;
	private TextView head;
	private ContinueData cd;
	private String file = "/data/data/com.example.cs356/continue.bin";
	private Scoreboard sb;
	private String rule[];
	private String ruleList[];
	private Typeface f1;
	private Typeface f2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule_sheet);
		f1 = Typeface.createFromAsset(getAssets(), "fonts/CFMontrealHighSchool-Regula.ttf");
		f2 = Typeface.createFromAsset(getAssets(), "fonts/Athletic.TTF");
		background = (LinearLayout) this.findViewById(R.id.rulebar);
		back = (Button) this.findViewById(R.id.back);
		rules = (ListView) this.findViewById(R.id.listView1);
		head = (TextView) this.findViewById(R.id.textView1);
		head.setTypeface(f1);
		head.setTextSize(30);
		head.setTextColor(Color.WHITE);
		try 
        { 
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)); 
            cd = (ContinueData) ois.readObject();  
        } 
		catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
		}
		sb = cd.getSb();
		rule = sb.getRules();
		ruleList = new String[rule.length];
		for(int i = 0; i < rule.length; i++){
			ruleList[i] = (i+1) + ". " + rule[i];
		}
		RAdapter adapter = new RAdapter(this,android.R.layout.simple_list_item_1, ruleList,f2);
        rules.setAdapter(adapter);
		
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MediaPlayer mp = MediaPlayer.create(RuleSheet.this, R.raw.click);
				mp.start();
				cd.setSb(sb);
				try 
			    { 
			       ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/data/data/com.example.cs356/continue.bin"))); 
			       //Select where you wish to save the file... 
			       oos.writeObject(cd); // write the class as an 'object' 
			       oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin' 
			       oos.close();// close the stream 
			    } 
			    catch(Exception ex) 
			    { 
			       Log.v("Serialization Save Error : ",ex.getMessage()); 
			       ex.printStackTrace(); 
			    }
				Intent myIntent = new Intent(RuleSheet.this, com.example.cs356.ScoreboardUI.class);
				myIntent.putExtra("FILE", file);
				myIntent.putExtra("TYPE", "continue");
				startActivity(myIntent);
			}
		});
		
	}
	
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rule_sheet, menu);
		return true;
	}

}
