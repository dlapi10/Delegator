package freeuni.android.delegator.ui;

import freeuni.android.delegator.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class HomeActivity extends SuperActivity{
	
	//Private constants
	private static final String LOG_MESSAGE = "HOME";
	
	
	//Private variables
	private String userName;
	
	
	/*
	 * Activity LifeCycle and helper Methods, dedicated to this cycle
	 */
	
	
	/**
	 * Handling Incoming Intent;
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null){
			userName = intent.getStringExtra(LoginActivity.INTENT_EXTRA_MESSAGE_KEY_USER_NAME);
		}
	}
	
	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		handleIncomingIntent();
		this.setTitle(userName);
		super.onCreate(savedInstanceState);
		setContentView(freeuni.android.delegator.R.layout.activity_home);
		Log.i(LOG_MESSAGE,"onCreate");
	}
	
	
	/**
	 * On resume
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.i(LOG_MESSAGE,"onResume");
	}
	
	/**
	 * On Pause
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(LOG_MESSAGE,"onPause");
	}
	
	/**
	 * 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(LOG_MESSAGE,"onStart");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(LOG_MESSAGE,"onDestroy");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(LOG_MESSAGE,"onStop");
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
