package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.example.angelsanddemons.ScrollViewExt.OnBottomReachedListener;

public class IntroActivity extends Activity {
	
	public final static String players_key = "exclaim.team.angelsanddemons.MESSAGE1";
	public final static String goal_key = "exclaim.team.angelsanddemons.MESSAGE2";
	
	
	int players;
	int goal;
	ImageButton nextButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	    // Get the message from the intent
	    Intent intent = getIntent();
	    players = intent.getIntExtra(SetupMenu.players_key, 0);
	    goal = intent.getIntExtra(SetupMenu.goal_key, 0 );
	    //Make Button Invis
	    nextButton = (ImageButton)findViewById(R.id.next);
	    nextButton.setVisibility(View.INVISIBLE);
	    //Set up onbottomreached scroll listener
	    ScrollViewExt scrollView = (ScrollViewExt) findViewById(R.id.scrollView);
	    scrollView.setOnBottomReachedListener(new OnBottomReachedListener(){
	    	public void onBottomReached(){
	    		revealNextButton();
	    	}	
	    });
	    //click listener on imagebutton next
		nextButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v){
				startGameActivity(v);
			}
		});
		scrollDown(scrollView);
	}

	public void revealNextButton(){
		nextButton.setVisibility(View.VISIBLE);
	}

	
	public void startGameActivity(View view){
		Intent intent2 = new Intent(this, MainGame.class);
	    intent2.putExtra(players_key, players);
	    intent2.putExtra(goal_key, goal);
	    startActivity(intent2);
	}
	
	
    public void scrollDown(final ScrollViewExt h){
    	new CountDownTimer(200, 25) { 
    		public void onTick(long millisUntilFinished) { 
    			h.scrollBy(0,2);
    		} 
    		public void onFinish() { 
    			scrollDown(h);
    		} 
    	}.start(); 
    }
}
