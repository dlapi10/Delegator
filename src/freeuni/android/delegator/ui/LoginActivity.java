package freeuni.android.delegator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;


public class LoginActivity extends Activity{
	
	//Private constants
	private static final String LOG_MESSAGE = "LOGIN";

	// Public constants
	

	
	
	/*
	 * Activity LifeCycle and helper Methods, dedicated to this cycle
	 */
	
	/**
	 * Handling Incoming Intent;
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null && intent.getBooleanExtra(SuperActivity.INTENT_SIGN_OUT_MESSAGE,false)){
			stopActiveSession();
		}
	}

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(LOG_MESSAGE,"onCreate");
		handleIncomingIntent();
		String userName = userLoggedIn();
		if(userName!=null){
			goToHomeActivity(userName);
		}
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
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
	

	/*
	 * Other methods
	 */
	
	/**
	 * Deleting active session user
	 */
	private void stopActiveSession(){
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(getString(R.string.active_session_username));
		editor.commit();
	}

	/**
	 * starting active session
	 * @param username
	 */
	private void startActiveSession(String username){
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(getString(R.string.active_session_username), username);
		editor.commit();
	}
	
	/**
	 * Checks if user has already logged in. If true, returns User object, null otherwise
	 * @return User object or null
	 */
	private String userLoggedIn(){
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(), Context.MODE_PRIVATE);
		String ans = pref.getString(getString(R.string.active_session_username),null);
		return ans;
	}


	/**
	 * Method is called after submitting of credentials.
	 * If user name and password are legal then it should guide to the home activity.
	 * If there isn't such user, it should inform user.
	 * @param v View of the button
	 */
	public void submitAuthentication(View v){
		String username = ((EditText)findViewById(freeuni.android.delegator.R.id.user_name)).getText().toString();
		String password = ((EditText) findViewById(freeuni.android.delegator.R.id.password)).getText().toString();
		if(App.getCommunicator().checkCredentials(username,password)){
			goToHomeActivity(username);
		}else{
			Toast.makeText(getApplicationContext(), freeuni.android.delegator.R.string.la_wrong_pass_or_name, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Redirects to home activity
	 * @param username
	 */
	private void goToHomeActivity(String username){
		startActiveSession(username);
		Intent homeIntent = new Intent(this, HomeActivity.class);
		startActivity(homeIntent);
		finish();
	}

	/**
	 * Menu for the Login activity.
	 * Not used 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	
	
}
