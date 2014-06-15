package freeuni.android.delegator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SuperActivity extends Activity{
	
	// Public constants
	public static final String INTENT_SIGN_OUT_MESSAGE  = "freeuni.android.delegator.ui.LoginActivity.log.out";
	
	/**
	 * Signing out from any activity
	 */
	public void onSignOut(MenuItem item){
		Intent logInIntent = new Intent(this, LoginActivity.class);
		logInIntent.putExtra(INTENT_SIGN_OUT_MESSAGE, true);
		startActivity(logInIntent);
		finish();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(freeuni.android.delegator.R.layout.navigation_drawer);
		super.onCreate(savedInstanceState);
	}
	
	/*
	 * Listeners
	 */
	
	/**
	 * Shows home, my tasks, If clicked on navigation drawable
	 * @param v
	 */
	public void goHome(View v){
		Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
		//TODO
	}
	
	/**
	 * 
	 * @param v
	 */
	public void showSubordinates(View v){
		Toast.makeText(getApplicationContext(), "Subordinates", Toast.LENGTH_LONG).show();
		//TODO
	}
	
	/**
	 * Shows my group of suboridnates
	 * @param v
	 */
	public void showGroups(View v){
		Toast.makeText(getApplicationContext(), "Groups", Toast.LENGTH_LONG).show();
		//TODO
	}
}
