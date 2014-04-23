package com.example.angelsanddemons;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FirstRoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
        //setContentView(R.layout.activity_first_round);
        RelativeLayout rlMain = (RelativeLayout) findViewById(R.layout.activity_first_round);
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.ic_launcher);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
		params.topMargin=50;
		params.leftMargin=50;
		rlMain.addView(iv, params);

		
	}
	

}
