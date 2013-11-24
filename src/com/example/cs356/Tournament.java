package com.example.cs356;


import java.util.Arrays;
import java.util.Collections;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


public class Tournament extends Activity {
	RelativeLayout layout1;
	LinearLayout[] mainPnls;
	RelativeLayout[] pnls;
	RelativeLayout.LayoutParams brdrs;

	int mpcount = 1;

	RadioGroup[] bg;
	RadioButton rb1, rb2;
	Button shfl;
	final int teamNum = 16;
	RadioButton[] teams;
	String[] trnmntTeams = { "Empire", "CA", "LA", "CPP", "TA", "MN", "OL",
			"AB", "CD", "EF", "GH", "BO", "CHEVY", "FORD", "TX", "AR" };
	private LinearLayout mainFrame;
	int altI = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		shfl = new Button(this);
		shfl.setText("shuffle");
		shfl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				makeShuffle();
			}
		});

		int tmp = teamNum;// num of teams
		while (tmp >= 1) {// number of panel levels
			mpcount++;
			tmp /= 2;
		}

		mainPnls = new LinearLayout[mpcount];
		mainPnls[0] = new LinearLayout(this);
		mainPnls[0].setOrientation(LinearLayout.VERTICAL);
		mainPnls[0].addView(shfl);

		mainFrame = new LinearLayout(this);
		mainFrame.setOrientation(LinearLayout.HORIZONTAL);

		mainFrame.addView(mainPnls[0]);

		int tn = teamNum;
		int j = 0;

		teams = new RadioButton[2 * tn - 1];
		for (altI = 0; altI < 2 * tn - 1; altI++) {
			teams[altI] = new RadioButton(this);
			teams[altI].setTag("" + altI);
			teams[altI].setOnClickListener(rbuttons);
		}
		altI = 0;
		boolean chng = true;
		for (int mpan = 1; mpan < mpcount; mpan++) {
			pnls = new RelativeLayout[tn];
			bg = new RadioGroup[tn];
			mainPnls[mpan] = new LinearLayout(this);
			mainPnls[mpan].setOrientation(LinearLayout.VERTICAL);
			j = 0;
			chng = !chng;
			for (int i = 0; i < tn; i++) {
				teams[altI].setText("t"+(i+1));
				if (i % 2 == 0) {
					bg[j] = new RadioGroup(this);
					bg[j].addView(teams[altI]);
					if (tn > 1) {
						bg[j].addView(teams[altI + 1]);
					}
					pnls[j] = new RelativeLayout(this);
					pnls[j].addView(bg[j]);
					if(chng)
						pnls[j].setBackgroundColor(Color.MAGENTA);
					else
						pnls[j].setBackgroundColor(Color.RED);
					mainPnls[mpan].addView(pnls[j]);	
				}
				chng = !chng;
				j++;
				altI++;
			}
			tn /= 2;
			
			mainFrame.addView(mainPnls[mpan]);
			mainFrame.setGravity(Gravity.CENTER_HORIZONTAL);
			mainFrame.setGravity(Gravity.CENTER_VERTICAL);
			mainFrame.setBackgroundColor(Color.BLUE);
		}
		setContentView(mainFrame);
	}

	public String[] makeShuffle() {
		shfl.setText("shuffled");
		shfl.setEnabled(false);
		Collections.shuffle(Arrays.asList(trnmntTeams));
		for (int i = 0; i < teamNum; i++) {
			teams[i].setText(trnmntTeams[i]);
		}
		return trnmntTeams;
	}

	private OnClickListener rbuttons = new OnClickListener() {
		@Override
		public void onClick(View v) {
	       int input = Integer.parseInt(v.getTag().toString());
	        if (input != altI - 1) {
	            teams[teamNum + input / 2].setText(teams[input].getText().toString());
	        } else {
	            shfl.setText("the Winner is " + teams[input].getText().toString());
	        }

		}
	};
}
