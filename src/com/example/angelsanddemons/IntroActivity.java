package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class IntroActivity extends Activity {
	
	public final static String extra_player_key = "com.example.angelsanddemons.track_players";
	public final static String extra_goal_key = "com.example.angelsanddemons.track_goal";
	
	private TextView textView3;
	private TextView textView4;
	private int i = 0;
	
	int numplayers;
	int numgoal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);

		Intent intent = getIntent();
		numplayers = intent.getIntExtra("com.example.angelsanddemons.track_players", 0 );
		numgoal = intent.getIntExtra("com.example.angelsanddemons.track_goal", 0 );
		
		//Retrieve intent from SetupMenu and use textview to display Number of players and Score Goal
		
		textView3 = (TextView) findViewById(R.id.textView3);
		textView3.setText("Number of Players:" + " " + String.valueOf(numplayers) + ", " + "Final Goal:" + " " + String.valueOf(numgoal));
	
		//Create a textview that is scrollable
		textView4 = (TextView) findViewById(R.id.textView4);
		textView4.setMovementMethod(new ScrollingMovementMethod());
		
		textView4.post(new Runnable() {
		    @Override
		    public void run() {
		        textView4.scrollTo(0, textView4.getBottom());
		    }
		});
	}
	
	public void to_firstround(View view) {
		
		Intent intent2 = new Intent(this, FirstRoundActivity.class);
		intent2.putExtra("com.example.angelsanddemons.track_players", numplayers);
        intent2.putExtra("com.example.angelsanddemons.track_goal", numgoal);
		startActivity(intent2);
		
	}
	
	public void toggleButton(View view) {
		View button1 = findViewById(R.id.button1);
		if (i == 0){
			Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
			button1.startAnimation(in);
			i = 1;
		}
		button1.setVisibility(View.VISIBLE);
	}
	
	

}
