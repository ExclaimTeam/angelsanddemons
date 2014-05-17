package com.example.angelsanddemons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstRoundActivity extends Activity implements OnTouchListener,OnDragListener {

int width, height;
ImageView card;
int numplayers;
int goal;
int mActionBarSize;
int buffer = 30;
int currentplayernum = 0;
int timecounter = 1;
int goal_score;
ImageView player;
ImageView timebackground;
int numangels;
List<Integer> scores = new ArrayList<Integer>();
Integer[] icons= {R.drawable.icon_book,R.drawable.icon_dog,R.drawable.icon_fish,R.drawable.icon_flower,R.drawable.icon_hammer,R.drawable.icon_lute,R.drawable.icon_staff,R.drawable.icon_sword,R.drawable.icon_tree,R.drawable.icon_wheat,};
LinkedList<Integer> icons_used = new LinkedList();

boolean is_night = true;

TextView counter;
RelativeLayout relativeLayout;
Random rng = new Random();
Button end_turn;
int time = 0;

ImageView player1, player2, player3, player4, player5, player6, player7, player8, player9, player10;
ImageView[] player_icon_images = {player1, player2, player3, player4, player5, player6, player7, player8, player9, player10};
Integer[] cards={R.drawable.actioncard_bless,R.drawable.actioncard_curse,R.drawable.actioncard_disgrace,R.drawable.actioncard_guard,R.drawable.actioncard_steal};
Integer[] cloudanim={R.anim.cloudprogression, R.anim.cloudprogression2, R.anim.cloudprogression3, R.anim.cloudprogression4, R.anim.cloudprogression5};

ImageView cloud1, cloud2, cloud3, cloud4, cloud5;
ImageView[] cloudimages = {cloud1, cloud2, cloud3, cloud4, cloud5};
Integer[] cloudsnight={R.drawable.cloud1night, R.drawable.cloud2night, R.drawable.cloud3night, R.drawable.cloud4night, R.drawable.cloud5night};


	public Integer getIconNumber(){
    	while(true) {	
	        Integer num = rng.nextInt(icons.length);
	        // find a new number and add to array
	        if (!icons_used.contains(num)) {
		        icons_used.addLast(num);	
		        return num;
		    }
    	}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		    v.startDrag(null, shadowBuilder, v, 0);
		    v.setVisibility(View.INVISIBLE);
		    return true;
		} else {
		    return false;
		}
	}

	@Override
	public boolean onDrag(View player_icon, DragEvent e) {
		//When Dropped
		if (e.getAction()==DragEvent.ACTION_DROP) {
			View cardView = (View) findViewById(1);
			cardView.setVisibility(View.INVISIBLE);
			int cardnum = (Integer)cardView.getTag();
			Object playernum = player_icon.getTag();
			//If the card is dropped onto a valid icon
			if (playernum instanceof Integer){
				switch (cardnum){
					//Bless +1
					case 0:
						//if disgraced
						if ((Boolean) player_icon.getTag(R.string.disgrace_status)) {
							player_icon.setTag(R.string.disgrace_status,false);
							break;
						}
						if (scores.get((Integer) playernum)+1 == 10) {
							//if this blessing grants the 10th point, do not add 1 point
							break;
						}
						else {
							scores.set((Integer) playernum, scores.get((Integer) playernum)+1);
							break;
						}
					//Curse -1
					case 1:
						// guarded
						if ((Boolean) player_icon.getTag(R.string.guard_status)) {
							player_icon.setTag(R.string.guard_status,false);
							break;
						}
						else{
							scores.set((Integer) playernum, scores.get((Integer) playernum)-1);
							break;
						}
					//Disgrace
					case 2:
						player_icon.setTag(R.string.disgrace_status,true);
						break;
					//Guard
					case 3:
						player_icon.setTag(R.string.guard_status,true);
						break;
					//Steal +1 self -1
					case 4:
						if (scores.get((Integer) playernum)+1 == 10) {
							//if this steal grants the 10th point, do not add 1 point
							scores.set((Integer) playernum, scores.get((Integer) playernum)-1);
							break;
						} else {
							scores.set((Integer) currentplayernum, scores.get((Integer) currentplayernum)+1);
							scores.set((Integer) playernum, scores.get((Integer) playernum)-1);
							break;
						}
					//Elect +1
					case 10:
						//if disgraced
						if ((Boolean) player_icon.getTag(R.string.disgrace_status)) {
							player_icon.setTag(R.string.disgrace_status,false);
							break;
						}
						//victory condition
						if (scores.get((Integer) playernum)+1 == goal_score) {
							Intent intent3 = new Intent(this, VictoryActivity.class);
							intent3.putExtra("com.example.angelsanddemons.track_players", numplayers);
					        intent3.putExtra("com.example.angelsanddemons.track_goal", goal_score);
							startActivity(intent3);
						}
						//normal election
						else {
							scores.set((Integer) playernum, scores.get((Integer) playernum)+1);
							break;
						}
					}
				end_turn.setVisibility(View.VISIBLE);
				updateScoreDisplay();
			}
		else{
			cardView.setVisibility(View.VISIBLE);
			}
		}
		return true;
	}
	
	public void cloudsetter() {
		Animation cloudprogression = AnimationUtils.loadAnimation(this, cloudanim[rng.nextInt(cloudanim.length) - 1]);
		cloudimages[rng.nextInt(cloudimages.length) - 1].startAnimation(cloudprogression);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		goal_score = intent.getIntExtra("com.example.angelsanddemons.track_goal", 0);
		
	    //Find Actionbar Size
	    final TypedArray styledAttributes = getBaseContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
		mActionBarSize = (int) styledAttributes.getDimension(0, 0);
		styledAttributes.recycle();
		
		//Find Screen dimensions
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		height = metrics.heightPixels;	
		
		//LinearLayOut Setup
        relativeLayout= new RelativeLayout(this);
        relativeLayout.setOnDragListener(this);
        
        timebackground = new ImageView(this);
        timebackground.setImageResource(R.drawable.nighttimebackground);
		timebackground.setScaleType(ScaleType.CENTER_CROP);
		relativeLayout.addView(timebackground);

        //background clouds
		/*
		int x = 0;
		while (x <= cloudimages.length){;
		cloudimages[(cloudimages.length) - x - 1].setImageResource(cloudsnight[(cloudsnight.length) - x - 1]);
		relativeLayout.addView(cloudimages[(cloudimages.length) - x - 1]);
		x++;
		}
		*/
		cloud1 = new ImageView(this);
		cloud2 = new ImageView(this);

		cloud3 = new ImageView(this);

		cloud4 = new ImageView(this);

		cloud5 = new ImageView(this);

		cloud1.setImageResource(R.drawable.cloud1night);
		cloud2.setImageResource(R.drawable.cloud2night);
		cloud3.setImageResource(R.drawable.cloud3night);
		cloud4.setImageResource(R.drawable.cloud4night);
		cloud5.setImageResource(R.drawable.cloud5night);
		relativeLayout.addView(cloud1);
		relativeLayout.addView(cloud2);
		relativeLayout.addView(cloud3);
		relativeLayout.addView(cloud4);
		relativeLayout.addView(cloud5);


		//Declare the timer
		Timer t = new Timer();
		//Set the schedule function and rate
		t.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        //Called each time when 1000 milliseconds (1 second) (the period parameter)
		    	//We must use this function in order to change the text view text
		    	runOnUiThread(new Runnable() {

		    	    @Override
		    	    public void run() {
		    	        cloudsetter();
		    	    }

		    	});
		    }

		},
		//Set how long before to start calling the TimerTask (in milliseconds)
		0,
		//Set the amount of time between each execution (in milliseconds)
		rng.nextInt(5) * 2000);
        
        



       
        //Size of Icon
        RelativeLayout.LayoutParams icon_dimensions = new RelativeLayout.LayoutParams(width/5, height/6);
        
        //Make card
        card = new ImageView(this);
        drawActionCard("Night");
        card.setId(1);
		RelativeLayout.LayoutParams card_dimensions = new RelativeLayout.LayoutParams(width/3, height/5);
		card.setLayoutParams(card_dimensions);
		card.setX(width/2 - card_dimensions.width/2);
		card.setY(height/2 - card_dimensions.height/2 - mActionBarSize);
		card.setOnTouchListener(this);
		relativeLayout.addView(card);
           
        //Number of players from intent
        numplayers = intent.getIntExtra("com.example.angelsanddemons.track_players", 0 );
        
        //Fill scoreboard
        int j = 0;
        while (j < numplayers) {
        	scores.add(0);
        	j++;
        }
        
        //Radius calculation
        int radius = width/2 - width/10;
        
        //i is player index starting from 0
        int i = 0;
        
        //set up numangels
        if (numplayers % 2 != 0){
        	if (rng.nextInt() == 1){
        		numangels = Math.round(numplayers/2);
        	}
        	else{
        		numangels = Math.round(numplayers/2) - 1;
        	}
        } else {
        	numangels = numplayers/2;
        }
        
        
      //Background stuff
        //Pillars
        ImageView leftpillar = new ImageView(this);
        ImageView rightpillar = new ImageView(this);
        leftpillar.setImageResource(R.drawable.background_column_right);
        rightpillar.setImageResource(R.drawable.background_column_left);
        //Size of Pillars
        RelativeLayout.LayoutParams pillar_dimensions = new RelativeLayout.LayoutParams(width/5, height);
        leftpillar.setLayoutParams(pillar_dimensions);
        rightpillar.setLayoutParams(pillar_dimensions);
        leftpillar.setScaleType(ScaleType.FIT_XY);
        rightpillar.setScaleType(ScaleType.FIT_XY);
        leftpillar.setX((float)0);
        leftpillar.setY((float)0);
        rightpillar.setX((float) 4/5*width);
        rightpillar.setY((float)0);

        relativeLayout.addView(leftpillar);
        relativeLayout.addView(rightpillar);
        
        





        
        //Arrangement Generator
        while (i < numplayers){
        	//Image View Settings
            player= new ImageView(this);          
	    	player.setImageResource(icons[getIconNumber()]);
			player.setLayoutParams(icon_dimensions);
			
	        //Math to find and set Image Location
			float x_adjust = -radius * (float) Math.cos((Math.PI * 2 * i/numplayers) + (Math.PI/2));
			float y_adjust = radius * (float) Math.sin((Math.PI * 2 * i/numplayers) + (Math.PI/2));          
			player.setX(width/2 - width/10 + x_adjust);
			player.setY(height/2 - height/12 - mActionBarSize - y_adjust);
			
			//Drag/Drop Listener
			player.setOnDragListener(this);
			
			//Icon info setup
			player.setTag(R.string.disgrace_status, false);
			player.setTag(R.string.guard_status, false);
			player.setTag(i);
			
			//Distribute angels and demons
			if ((numangels > 0 && rng.nextBoolean() == true) || numangels >= (numplayers - i)){
				player.setTag(R.string.is_angel, true);
				numangels --;
			}
			else {
				player.setTag(R.string.is_angel, false);
			}
			


			
			//Updates existing list with generated icon
			player_icon_images[i] = player;
						  
			i += 1;
			relativeLayout.addView(player);
        }
        
        //TextView for testing
        counter = new TextView(this);
        updateScoreDisplay();
        relativeLayout.addView(counter);
        
        //Allegiance background
        show_allegiance();
        	
        	
        //end turn button
        end_turn=new Button(this);
	    end_turn.setText("End turn");
	    end_turn.setVisibility(View.INVISIBLE);
	    end_turn.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
	        	  if (timecounter < numplayers){
	 			     drawActionCard("Night");
	 			     currentplayernum++;
	 			     show_allegiance();
	 			     if (currentplayernum == numplayers){
	 			      currentplayernum = 0;
	 			     }
	 			    }
	 			    //Night to Day
	 			    else if ((timecounter - numplayers)% 2 == 0){
	 			     drawActionCard("Day");
	 			    timebackground.setImageResource(R.drawable.daytimebackground);
	 				timebackground.setScaleType(ScaleType.CENTER_CROP);
	 			     currentplayernum++;
	 			    if (currentplayernum == numplayers){
		 			      currentplayernum = 0;
		 			     }
	 			     show_allegiance();
	 			    }
	 			    //Day to Night
	 			    else{
	 			     drawActionCard("Night");
	 			    timebackground.setImageResource(R.drawable.nighttimebackground);
	 				timebackground.setScaleType(ScaleType.CENTER_CROP);
	 			     show_allegiance();
	 			    }
	 			    timecounter++;
	 			    end_turn.setVisibility(View.INVISIBLE);
	 			    card.setVisibility(View.VISIBLE);  
	 			    updateScoreDisplay();
	 			   }
	        });
		end_turn.setX(width - width/5);
		end_turn.setY(height - height/6);
		relativeLayout.addView(end_turn);


		
        //make visible to program
        setContentView(relativeLayout);
	}

	public void updateScoreDisplay(){
        counter.setText(String.valueOf(currentplayernum+1) + " " + String.valueOf(player_icon_images[0].getTag(R.string.is_angel))+ " " );
        for (int i = 0; i < numplayers; i++) {
        	counter.append("Player " + String.valueOf(i+1) + ": " + String.valueOf(scores.get(i)) + " | ");
        }
	}

    //Draws an action card, which can interact with the icons using setOnTouchListener
	public void drawActionCard(String time_of_day){
		  if (time_of_day == "Night"){
		         Integer randomcardnum = rng.nextInt(cards.length);
		         card.setImageResource(cards[randomcardnum]);
		         card.setTag(randomcardnum);   
		  } else if (time_of_day == "Day"){
		   card.setImageResource(R.drawable.elect_coin);
		   card.setTag(10);
		  }

	}
	
	public void show_allegiance(){
		if ((Boolean) player_icon_images[currentplayernum].getTag(R.string.is_angel) == true) {
            //relativeLayout.setBackgroundResource(R.drawable.background_angel);
        }
        if ((Boolean) player_icon_images[currentplayernum].getTag(R.string.is_angel) == false){
            //relativeLayout.setBackgroundResource(R.drawable.background_demon);
        }
	}
	



	/*
	public void set_time(String time_of_day) {
		if (time_of_day == "Night") {
			timebackground.setImageResource(R.drawable.nighttimebackground);
			timebackground.setScaleType(ScaleType.CENTER_CROP);
			relativeLayout.addView(timebackground);
		} else if (time_of_day == "Day") {
			timebackground.setImageResource(R.drawable.daytimebackground);
			timebackground.setScaleType(ScaleType.CENTER_CROP);
			relativeLayout.addView(timebackground);
		}
	}
	*/
}
