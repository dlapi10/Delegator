package freeuni.android.delegator.ui;

import freeuni.android.delegator.R;
import android.os.Bundle;
import android.view.Menu;

public class HomeActivity extends SuperActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(freeuni.android.delegator.R.layout.activity_home);
	}
	
	/**
	 * Menu for the Home activity.
	 * Not used 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
