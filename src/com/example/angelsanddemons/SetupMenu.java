package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SetupMenu extends Activity {
	
	public final static String extra_player_key = "com.example.angelsanddemons.track_players";
	public final static String extra_goal_key = "com.example.angelsanddemons.track_goal";
	private SeekBar seekBar;
	private TextView textView;
	private SeekBar seekBar2;
	private TextView textView2;
	private int min_players = 4;
	private int min_score = 5;
	public int track_players = 6;
	public int track_goal = 7;

	public void to_intro(View view) {
		Intent intent = new Intent(this, IntroActivity.class);
        intent.putExtra(extra_player_key, track_players);
        intent.putExtra(extra_goal_key, track_goal);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_menu);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		textView = (TextView) findViewById(R.id.textView1);
		int seekBar_progress = seekBar.getProgress() + min_players;
		textView.setText("Number of Players:" + " " + seekBar_progress);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		//int initial_progress = min_players;
        @Override
      public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        int progress_players = progresValue + min_players;
        track_players = progresValue + min_players;
        textView.setText("Number of Players:" + " " + progress_players);
      }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
          // Display the value in textview

        }
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
    });
		seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		textView2 = (TextView) findViewById(R.id.textView2);
		int seekBar2_progress = seekBar2.getProgress() + min_score;
		textView2.setText("Favor needed to win:" + " " + seekBar2_progress);
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        //int initial_progress = progress_score;
        @Override
      public void onProgressChanged(SeekBar seekBar2, int progresValue, boolean fromUser) {
        int progress_score = progresValue + min_score;
        track_goal = progresValue + min_score;
        textView2.setText("Favor needed to win:" + progress_score);
      }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
          // Display the value in textview

        }
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

    });

   }
	

	

}
