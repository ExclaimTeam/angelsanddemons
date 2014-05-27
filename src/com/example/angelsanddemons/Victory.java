package com.example.angelsanddemons;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Victory extends Activity {
	public final static String winner_key = "exclaim.team.angelsanddemons.winner";
	public final static String helpers_key = "exclaim.team.angelsanddemons.helpers";
	List<Integer> helpers = new ArrayList<Integer>();
	Integer[] icons= {R.drawable.icon_book,R.drawable.icon_dog,R.drawable.icon_fish,R.drawable.icon_flower,R.drawable.icon_hammer,R.drawable.icon_lute,R.drawable.icon_staff,R.drawable.icon_sword,R.drawable.icon_tree,R.drawable.icon_wheat,};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RelativeLayout relativeLayout;
		relativeLayout = new RelativeLayout(this);
		
		Intent intent = getIntent();
		int winner = intent.getIntExtra(winner_key, 0);
		
	    //Find Actionbar Size
	    final TypedArray styledAttributes = getBaseContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
		int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
		styledAttributes.recycle();
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
		int icon_width = width/5;
		int icon_height = height/6;
		RelativeLayout.LayoutParams icon_dimensions = new RelativeLayout.LayoutParams(icon_width, icon_height);
		
		ImageView winner_icon = new ImageView(this);
		winner_icon.setLayoutParams(icon_dimensions);
		winner_icon.setImageResource(icons[winner]);
		winner_icon.setX(width/2 - icon_width/2);
		winner_icon.setY(height/3 - icon_height/2);
		relativeLayout.addView(winner_icon);
						
		helpers = intent.getIntegerArrayListExtra(helpers_key);
		
		int numhelpers = helpers.size();
		int horizontal_spacing = width/(numhelpers+1);
		
		int i = 0;
		while (i < numhelpers){
			ImageView helper= new ImageView(this); 
			helper.setLayoutParams(icon_dimensions);
			helper.setImageResource(icons[helpers.get(i)]);
			helper.setX((i+1)*horizontal_spacing - width/10);
			helper.setY(2*height/3 - mActionBarSize);
			relativeLayout.addView(helper);
			i++;
		}
		
		relativeLayout.setBackgroundResource(R.drawable.victory);
		setContentView(relativeLayout);		
	}
}
