package com.example.cs356;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ScoreList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Scores s;
		try 
        { 
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.cs356/scores.bin")); 
            s = (Scores) ois.readObject();  
        } 
		catch(Exception e){
			Log.v("Serialization Read Error : ",e.getMessage());
			s = new Scores();
		}
		String scores[] = new String[10];
		scores[0] = "No Scores Saved!";
		for(int i = 1; i < 10; i++){
			scores[i] = " ";
		}
		for(int i = s.size() - 1; i >= 0; i--){
			scores[s.size() - i - 1] = (s.size() - i) + ". " + s.getScore(i).getData();
		} 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, scores);
        setListAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
