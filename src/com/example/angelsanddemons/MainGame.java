package com.example.angelsanddemons;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainGame extends Activity implements OnTouchListener,OnDragListener {

public final static String winner_key = "exclaim.team.angelsanddemons.winner";
public final static String helpers_key = "exclaim.team.angelsanddemons.helpers";
public final static String players_key = "exclaim.team.angelsanddemons.MESSAGE1";

int width, height, numplayers, goal = 2, mActionBarSize, currentplayernum = 0, turnCounter = 1, numangels;
int dmin = 10000, drange = 20000, tmin = 0, trange = 5000;
double amin = 0.1, arange = 0.5, ymin = 0;
final double yrange = 0.35;
List<Integer> scores = new ArrayList<Integer>();
LinkedList<Integer> icons_used = new LinkedList();

ImageButton nextButton;
TextView scoreboard;
TextView transitiontext;
RelativeLayout relativeLayout;
Random rng = new Random();

ImageView card;
ImageView allegiance;
ImageView player1, player2, player3, player4, player5, player6, player7, player8, player9, player10;
ImageView[] player_icon_images = {player1, player2, player3, player4, player5, player6, player7, player8, player9, player10};

TextView tplayer1, tplayer2, tplayer3, tplayer4, tplayer5, tplayer6, tplayer7, tplayer8, tplayer9, tplayer10;
TextView[] tplayers = {tplayer1, tplayer2, tplayer3, tplayer4, tplayer5, tplayer6, tplayer7, tplayer8, tplayer9, tplayer10};
TextView instructions;

Integer[] cards={R.drawable.actioncard_bless,R.drawable.actioncard_curse,R.drawable.actioncard_disgrace,R.drawable.actioncard_guard,R.drawable.actioncard_steal};
Integer[] icons= {R.drawable.icon_book2,R.drawable.icon_dog2,R.drawable.icon_fish2,R.drawable.icon_flower2,R.drawable.icon_hammer2,R.drawable.icon_lute2,R.drawable.icon_staff2,R.drawable.icon_shield2,R.drawable.icon_tree2,R.drawable.icon_wheat2,};

	public void showScores(){
		for (int i = 0; i < numplayers; i++) {
			//if (scores.get(i) > scores.get(currentplayernum)){
				tplayers[i].setText(String.valueOf(scores.get(i)));
			//}
		}
	}
	
	public void unShowScores(){
		for (int i = 0; i < numplayers; i++){
			tplayers[i].setText("");
		}
	}

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
			int cardnum = (Integer)card.getTag();
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
						//cannot reach goal with bless
						else if (scores.get((Integer) currentplayernum) + 1 == goal){
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
						}
						else{
							scores.set((Integer) playernum, scores.get((Integer) playernum)-1);
						}
						break;
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
						//cannot affect self
						if (currentplayernum ==  (Integer) playernum){
							card.setVisibility(View.VISIBLE);
							break;
						}
						//cannot reach goal with bless
						if (scores.get((Integer) currentplayernum) + 1 == goal){
						}
						else {
							scores.set((Integer) currentplayernum, scores.get((Integer) currentplayernum)+1);
						}
						scores.set((Integer) playernum, scores.get((Integer) playernum)-1);
						break;
					case 10:
						//if disgraced
						if ((Boolean) player_icon.getTag(R.string.disgrace_status)) {
							player_icon.setTag(R.string.disgrace_status,false);
							break;
						}
						//win condition
						if (scores.get((Integer)playernum) + 1 == goal){
							Intent intent2 = new Intent(this, Victory.class);
							intent2.putExtra(winner_key, (Integer) player_icon_images[(Integer) playernum].getTag(R.string.numicon));
						    ArrayList<Integer> helpers = new ArrayList<Integer>();
						    for (int i=0;i<numplayers;i++){
						    	if (i == (Integer)playernum){
						    	}	else if ((Boolean)player_icon_images[i].getTag(R.string.is_angel).equals((Boolean)player_icon_images[(Integer)playernum].getTag(R.string.is_angel))){
						    		helpers.add((Integer) player_icon_images[i].getTag(R.string.numicon));
						    	}
						    }
						    intent2.putIntegerArrayListExtra(helpers_key, helpers);

						    
						    startActivity(intent2);
						}
						scores.set((Integer) playernum, scores.get((Integer) playernum)+1);
						break;
				}
				nextButton.setVisibility(View.VISIBLE);
				updateScoreDisplay();
			}
			else{
				card.setVisibility(View.VISIBLE);
			}
		}
		return true;
	}
	
	public TranslateAnimation makeDrift() {
	    float ycoord = (float) (yrange*height*Math.random());
	    TranslateAnimation drift = new TranslateAnimation((float) width,(float) -width, ycoord, ycoord);
		drift.setDuration((long) (dmin + (Math.random() * (drange))));
	    drift.setStartOffset((long) (tmin + (Math.random() * (trange))));
	    return drift;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//RelativeLayOut Setup
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setOnDragListener(this);
        relativeLayout.setBackgroundResource(R.drawable.nighttimebackground);
		
        //Transition
        
        //Number of players from intent
        Intent intent = getIntent();
        numplayers = intent.getIntExtra("exclaim.team.angelsanddemons.MESSAGE1", 0 );
				
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
		
		//Background stuff
		    //Clouds
		    class MyImageView extends ImageView {
		    	public MyImageView(Context context) {
					super(context);
				}
	
				@Override
		    	protected void onAnimationEnd() {
		    	    super.onAnimationEnd();
		    	    this.setAlpha((float) (amin+(Math.random() * arange)));
		    	    this.startAnimation(makeDrift());
		    	}
		    }
		    
		    ImageView cloud1 = new MyImageView(this);
		    ImageView cloud2 = new MyImageView(this);
		    ImageView cloud3 = new MyImageView(this);
		    ImageView cloud4 = new MyImageView(this);
		    ImageView cloud5 = new MyImageView(this);
		    relativeLayout.addView(cloud1);
		    relativeLayout.addView(cloud2);
		    relativeLayout.addView(cloud4);
		    relativeLayout.addView(cloud5);
		    cloud1.setImageResource(R.drawable.cloud1day);    
		    cloud2.setImageResource(R.drawable.cloud2day);
		    cloud3.setImageResource(R.drawable.cloud3day);
		    cloud4.setImageResource(R.drawable.cloud4day);
		    cloud5.setImageResource(R.drawable.cloud5day);
		    cloud1.setAlpha((float) (amin+(Math.random() * arange)));
		    cloud2.setAlpha((float) (amin+(Math.random() * arange)));
		    cloud3.setAlpha((float) (amin+(Math.random() * arange)));
		    cloud4.setAlpha((float) (amin+(Math.random() * arange)));
		    cloud5.setAlpha((float) (amin+(Math.random() * arange)));
		    cloud1.startAnimation(makeDrift());
		    cloud2.startAnimation(makeDrift());
		    cloud3.startAnimation(makeDrift());
		    cloud4.startAnimation(makeDrift());
		    cloud5.startAnimation(makeDrift());
		
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
	            
        //Size of Icon
        RelativeLayout.LayoutParams icon_dimensions = new RelativeLayout.LayoutParams(width/5, height/6);
        
        //Make card
        card = new ImageView(this);
        drawActionCard("Night");
		RelativeLayout.LayoutParams card_dimensions = new RelativeLayout.LayoutParams(width/3, height/5);
		card.setLayoutParams(card_dimensions);
		card.setX(width/2 - card_dimensions.width/2);
		card.setY(height/2 - card_dimensions.height/2 - mActionBarSize);
		card.setOnTouchListener(this);
		relativeLayout.addView(card);
		
		//Make Button
		nextButton = new ImageButton(this);
		nextButton.setVisibility(View.INVISIBLE);
		nextButton.setImageResource(R.drawable.endturn_selector);
		nextButton.setBackgroundColor(Color.TRANSPARENT);
		nextButton.setX(4*width/5);
		nextButton.setY(4*height/5);
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				if (turnCounter < numplayers){
					relativeLayout.setBackgroundResource(R.drawable.nighttimebackground);
					drawActionCard("Night");
					displayInstructions("Night");
					currentplayernum++;
					displayAllegiance();
					//nextPlayerTransition(1);
				}
				//Night to Day
				else if ((turnCounter - numplayers)% 2 == 0){
					drawActionCard("Day");
					unShowScores();
					relativeLayout.setBackgroundResource(R.drawable.daytimebackground);
					currentplayernum++;
					if (currentplayernum == numplayers){
						currentplayernum = 0;
					}
					displayAllegiance("Day");
					displayInstructions("Day");
				}
				//Day to Night
				else{
					relativeLayout.setBackgroundResource(R.drawable.nighttimebackground);
					drawActionCard("Night");
					displayInstructions("Night");
					displayAllegiance();
					showScores();
				}
				turnCounter++;
				nextButton.setVisibility(View.INVISIBLE);
				card.setVisibility(View.VISIBLE);		
				updateScoreDisplay();
			}
		});
		relativeLayout.addView(nextButton);
        
        //Radius calculation
        int radius = width/2 - width/10;
        
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
            
        //font
        Typeface qcentro = Typeface.createFromAsset(getAssets(),"myfont.ttf");
        
        //i is player index starting from 0
        int i = 0;
        
        //Arrangement Generator
        while (i < numplayers){
        	//Image View Settings
            ImageView player= new ImageView(this); 
            int numicon = getIconNumber();
	    	player.setImageResource(icons[numicon]);
			player.setLayoutParams(icon_dimensions);
			
	        //Math to find and set Image Location
			float x_adjust = -radius * (float) Math.cos((Math.PI * 2 * i/numplayers) + (Math.PI/2));
			float y_adjust = radius * (float) Math.sin((Math.PI * 2 * i/numplayers) + (Math.PI/2));
			float x = width/2 - width/10 + x_adjust;
			float y = height/2 - height/12 - mActionBarSize - y_adjust;
			player.setX(x);
			player.setY(y);
			
			//Textview to display score during night
			TextView tplayer = new TextView(this);
			tplayer.setLayoutParams(icon_dimensions);
			tplayer.setX(x);
			tplayer.setY(y);
			tplayer.setTextSize(height/23);
			tplayer.setGravity(Gravity.CENTER);
			tplayer.setTextColor(getResources().getColor(R.color.white));
			tplayer.setShadowLayer((float) 0.01, -2, 2,   getResources().getColor(R.color.black));
	        tplayer.setTypeface(qcentro);
			tplayers[i] = tplayer;
			
			//Drag/Drop Listener
			player.setOnDragListener(this);
			
			//Icon info setup
			player.setTag(R.string.disgrace_status, false);
			player.setTag(R.string.guard_status, false);
			player.setTag(i);
			player.setTag(R.string.numicon,numicon);
			
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
			relativeLayout.addView(tplayer);
        }
        
        //Fill scoreboard
        int j = 0;
        while (j < numplayers) {
        	scores.add(0);
        	j++;
        }
        
        //TextView for testing
        scoreboard = new TextView(this);
        updateScoreDisplay();
        relativeLayout.addView(scoreboard);
        
        //Allegiance Indicator
        allegiance = new ImageView(this);
        allegiance.setX(3*width/4 - width/8);
        allegiance.setY(5*height/7);
        RelativeLayout.LayoutParams allegiance_dimensions = new RelativeLayout.LayoutParams(width/4, height/8);
        allegiance.setLayoutParams(allegiance_dimensions);
        allegiance.setScaleType(ScaleType.FIT_XY);
        relativeLayout.addView(allegiance);
        displayAllegiance();
        
        //Instructions TextView
        instructions = new TextView(this);
		Typeface q = Typeface.createFromAsset(getAssets(), "myfont.ttf");
		LinearLayout.LayoutParams instruction_layout = new LinearLayout.LayoutParams(width/3,200);
		instructions.setLayoutParams(instruction_layout);
		instructions.setTypeface(q);
		instructions.setTextSize(20);
        instructions.setX(width/7);
        instructions.setY(7*height/10);
        displayInstructions("Night");
        relativeLayout.addView(instructions);
        
        //make visible to program
        setContentView(relativeLayout);
	}

	public void updateScoreDisplay(){
        scoreboard.setText("turncounter:" + turnCounter +"|||CURRENT PLAYER: " + String.valueOf(currentplayernum+1) + " |||" + "Angel?: " + 
        		String.valueOf(player_icon_images[currentplayernum].getTag(R.string.is_angel)));
        for (int i = 0; i < numplayers; i++) {
        	scoreboard.append("Player " + String.valueOf(i+1) + ": " + String.valueOf(scores.get(i)) + " | " );
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

	public void displayAllegiance(){
		if ((Boolean) player_icon_images[currentplayernum].getTag(R.string.is_angel) == true) {
			allegiance.setImageResource(R.drawable.background_angel);
		}
		if ((Boolean) player_icon_images[currentplayernum].getTag(R.string.is_angel) == false){
			allegiance.setImageResource(R.drawable.background_demon);
		}
	}
	
	public void displayAllegiance(String time){
		allegiance.setImageResource(0);
	}
	
	public void displayInstructions(String time){
		if (time == "Day"){
			instructions.setText("Elect A Villager To Ascend");
		}	
		else{
			instructions.setText("Drag To Use Your Action");	
		}
	}

	public void nextPlayerTansition(int n){
		if (n==1){
			transitiontext.setText("Player "+ String.valueOf(currentplayernum+1) +" Start");
		}
		else{
			transitiontext.setText("");
		}

		
		
		
	}
}
