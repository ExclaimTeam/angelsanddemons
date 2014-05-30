package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Find Screen dimensions
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
		//Pillars
		ImageView leftpillar = (ImageView)findViewById(R.id.pillar2);
		ImageView rightpillar = (ImageView)findViewById(R.id.pillar1);
		//Size of Pillars
		
        RelativeLayout.LayoutParams pillar_dimensions = new RelativeLayout.LayoutParams(width/5, height);
        //leftpillar.setLayoutParams(pillar_dimensions);
        //rightpillar.setLayoutParams(pillar_dimensions);

		
		setContentView(R.layout.activity_main);
		
	}

	
	public void start_button(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, SetupMenu.class);
		startActivity(intent);
	}
}
