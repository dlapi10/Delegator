package freeuni.android.delegator.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewStub;
import freeuni.android.delegator.R;

public class HomeActivity extends SuperActivity{

	//Private constants
	private static final String LOG_MESSAGE = "HOME";


	//Private variables

	/*
	 * Activity LifeCycle and helper Methods, dedicated to this cycle
	 */

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_MESSAGE,"onCreate");
		this.setTitle(getResources().getString(R.string.navigation_home));
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.activity_home);
		stub.inflate();
	}


	/**
	 * On resume
	 */
	@Override
	protected void onResume() {
		Log.i(LOG_MESSAGE,"onResume");
		super.onResume();
	}

	/**
	 * On Pause
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onPause");
		super.onPause();
	}

	/**
	 * 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onStart");
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onStop");
		super.onStop();
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
