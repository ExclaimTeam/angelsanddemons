package com.example.angelsanddemons;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String players_key = "exclaim.team.angelsanddemons.MESSAGE1";
	public final static String goal_key = "exclaim.team.angelsanddemons.MESSAGE2";
	
	private SeekBar seekBarPlayers;
	private SeekBar seekBarGoal;
	private TextView textView;
	private TextView textView2;
	TextView title;
	
	ScrollViewExt scrollView; 
	
	public int starting_players = 4;
	public int progress = 2;
	public int players = starting_players + progress;
	public int goal;
	
	Button next2;
	Button next1;
	Button next3;
	Button back1;
	Button back2;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		

		
	    next3 = (Button)findViewById(R.id.next3);
	    
	    //click listener on Button next
		next3.setOnClickListener(new OnClickListener() {

			public void onClick(View v){
				startGameActivity(v);
				overridePendingTransition(0,0);
			}
		});
		
		scrollView= (ScrollViewExt) findViewById(R.id.introscroll);
		
		scrollDown(scrollView);
				
		title = (TextView) findViewById(R.id.titletext);
		Typeface q = Typeface.createFromAsset(getAssets(), "myfont.ttf");
		title.setTypeface(q);	

		
		next1 = (Button)findViewById(R.id.nextbutton1);
		next1.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				start_setup(v);
			}
		});
		
		next2 = (Button)findViewById(R.id.nextbutton2);
		next2.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				startIntro(v);
 			}
		});
		
		back1 = (Button)findViewById(R.id.backbutton);
		back1.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				backToTitle(v);
			}
		});
		
		back2 = (Button)findViewById(R.id.backbutton2);
		back2.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				backToSetup(v);
			}
		});
		
		next1.setTypeface(q);
		next2.setTypeface(q);
		next3.setTypeface(q);
		back1.setTypeface(q);
		back2.setTypeface(q);
						
		seekBarPlayers = (SeekBar) findViewById(R.id.seekBar1);
		textView = (TextView) findViewById(R.id.textView1);
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
	
	public void start_setup(View view) {
		title = (TextView) findViewById(R.id.titletext);
		//Old		
		next1.setVisibility(View.INVISIBLE);
		title.setVisibility(View.INVISIBLE);
				
		//New		
		seekBarPlayers.setVisibility(View.VISIBLE);
		seekBarGoal.setVisibility(View.VISIBLE);
		textView.setVisibility(View.VISIBLE);
		textView2.setVisibility(View.VISIBLE);
		next2.setVisibility(View.VISIBLE);
		back1.setVisibility(View.VISIBLE);
	}
	
	public void backToTitle(View view){
		//New		
		next1.setVisibility(View.VISIBLE);
		title.setVisibility(View.VISIBLE);
				
		//Old		
		seekBarPlayers.setVisibility(View.INVISIBLE);
		seekBarGoal.setVisibility(View.INVISIBLE);
		textView.setVisibility(View.INVISIBLE);
		textView2.setVisibility(View.INVISIBLE);
		next2.setVisibility(View.INVISIBLE);
		back1.setVisibility(View.INVISIBLE);
		
	}
	
	public void startIntro(View view){	    
		//old
		seekBarPlayers.setVisibility(View.INVISIBLE);
		seekBarGoal.setVisibility(View.INVISIBLE);
		textView.setVisibility(View.INVISIBLE);
		textView2.setVisibility(View.INVISIBLE);
		next2.setVisibility(View.INVISIBLE);
		back1.setVisibility(View.INVISIBLE);
		
		//New
		scrollView.setVisibility(View.VISIBLE);
		next3.setVisibility(View.VISIBLE);
		back2.setVisibility(View.VISIBLE);
 
	}
	
	public void backToSetup(View view){
		//New
		seekBarPlayers.setVisibility(View.VISIBLE);
		seekBarGoal.setVisibility(View.VISIBLE);
		textView.setVisibility(View.VISIBLE);
		textView2.setVisibility(View.VISIBLE);
		next2.setVisibility(View.VISIBLE);
		back1.setVisibility(View.VISIBLE);
		
		//Old
		scrollView.setVisibility(View.INVISIBLE);
		next3.setVisibility(View.INVISIBLE);
		back2.setVisibility(View.INVISIBLE);
		
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
	public void startGameActivity(View view){
		
		Intent intent2 = new Intent(this, MainGame.class);
	    intent2.putExtra(players_key, players);
	    intent2.putExtra(goal_key, goal);
	    startActivity(intent2);
	}
}
