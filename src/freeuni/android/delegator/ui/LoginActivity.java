package freeuni.android.delegator.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;


public class LoginActivity extends SuperActivity{

	// Public constants
	public static final String INTENT_EXTRA_MESSAGE_KEY_USER_NAME = "freeuni.android.delegator.ui.LoginActivity.usernameKey";
	public static final String INTENT_LOG_OUT_MESSAGE  = "freeuni.android.delegator.ui.LoginActivity.log.out";


	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if(intent!=null && intent.getBooleanExtra(INTENT_LOG_OUT_MESSAGE,false)){
			stopActiveSession();
		}
		String userName = userLoggedIn();
		if(userName!=null){
			goToHomeActivity(userName);
		}
		setContentView(R.layout.activity_login);
	}

	/**
	 * Deleting active session user
	 */
	private void stopActiveSession(){
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(getString(R.string.active_session_username), null);
		editor.commit();
	}

	/**
	 * srating active session
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
		homeIntent.putExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME, username);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
}
