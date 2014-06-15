package freeuni.android.delegator.ui;

import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(), Context.MODE_PRIVATE);
		String userName = pref.getString(getString(R.string.active_session_username),null);
		this.setTitle(userName);
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
		Intent homeIntent = new Intent(this, HomeActivity.class);
		startActivity(homeIntent);
		//TODO
	}
	
	/**
	 * Shows subordinates
	 * @param v
	 */
	public void showSubordinates(View v){
		Toast.makeText(getApplicationContext(), "Subordinates", Toast.LENGTH_LONG).show();
		Intent subordinatesIntent = new Intent(this, SubordinatesActivity.class);
		startActivity(subordinatesIntent);
		//TODO
	}
	
	/**
	 * Shows my groups of subordinates
	 * @param v
	 */
	public void showGroups(View v){
		Toast.makeText(getApplicationContext(), "Groups", Toast.LENGTH_LONG).show();
		Intent groupIntent = new Intent(this, GroupsActivity.class);
		startActivity(groupIntent);
		//TODO
	}
}
