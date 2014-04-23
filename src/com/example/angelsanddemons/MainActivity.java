package com.example.angelsanddemons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void start_button(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, SetupMenu.class);
		startActivity(intent);
	}
}
