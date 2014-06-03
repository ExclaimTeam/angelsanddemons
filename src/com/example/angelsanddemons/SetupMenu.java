package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SetupMenu extends Activity {
	
	public final static String players_key = "exclaim.team.angelsanddemons.MESSAGE1";
	public final static String goal_key = "exclaim.team.angelsanddemons.MESSAGE2";
	
	private SeekBar seekBarPlayers;
	private SeekBar seekBarGoal;
	private TextView textView;
	private TextView textView2;
	
	public int starting_players = 4;
	public int progress = 2;
	public int players = starting_players + progress;
	public int goal;
	
	ImageButton imageButton;
	

	public void startIntro(View view){
		Intent intent = new Intent(this, IntroActivity.class);
	    intent.putExtra(players_key, players);
	    intent.putExtra(goal_key, goal);
	    startActivity(intent);
	    overridePendingTransition(0,0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_menu);
		
		
		ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton1);
		imageButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v){
				startIntro(v);
			}
		});
						
		seekBarPlayers = (SeekBar) findViewById(R.id.seekBar1);
		textView = (TextView) findViewById(R.id.textView1);
		Typeface q = Typeface.createFromAsset(getAssets(), "myfont.ttf");
		textView.setTypeface(q);
		textView.setText("Number of Players:  " + Integer.toString(starting_players + seekBarPlayers.getProgress()));
		seekBarPlayers.setOnSeekBarChangeListener(
		    new OnSeekBarChangeListener() {
		    	
		        @Override
		        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
		        	int progress_players = players = starting_players + progresValue;
		        	// Display the value in textview
			        textView.setText("Number of Players:  " + progress_players );
		        }

			    @Override
			    public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here, 
		        //if you want to do anything at the start of
		        // touching the seekbar
			    }
	
			    @Override
			    public void onStopTrackingTouch(SeekBar seekBar) {
			    }
		});
		seekBarGoal = (SeekBar) findViewById(R.id.seekBar2);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setTypeface(q);
		textView2.setText("Goal:  " + seekBarGoal.getProgress());	
		
		seekBarGoal.setOnSeekBarChangeListener(
		    new OnSeekBarChangeListener() {
		    	int progress_goal = 0;
		        @Override
		        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
		        	progress_goal = progresValue;
		        	goal = progresValue;
		        	// Display the value in textview
			        textView2.setText("Goal:  " + progress_goal);
		        }

			    @Override
			    public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here, 
		        //if you want to do anything at the start of
		        // touching the seekbar
			    }
	
			    @Override
			    public void onStopTrackingTouch(SeekBar seekBar) {
			    }
		});
	}
}
