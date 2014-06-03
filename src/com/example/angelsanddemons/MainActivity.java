package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_main);
		
		TextView textView = (TextView) findViewById(R.id.titletext);
		Typeface q = Typeface.createFromAsset(getAssets(), "myfont.ttf");
		textView.setTypeface(q);
		
		ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton1);
		imageButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v){
				start_button(v);
			}
		});
		
	}

	
	public void start_button(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, SetupMenu.class);
		startActivity(intent);
		overridePendingTransition(0,0);
	}
}
