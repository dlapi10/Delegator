package freeuni.android.delegator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import freeuni.android.delegator.R;


public class LoginActivity extends SuperActivity{
	
	// Public constants
	public static final String INTENT_EXTRA_MESSAGE_KEY_USER_NAME = "freeuni.android.delegator.ui.LoginActivity.usernameKey";
	
	//Private constants
	public static final String LOG_CAT_TAG = "Login";
	
	
	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	
	/**
	 * Method is called after submitting of credentials.
	 * If user name and password are legal then it should guide to the home activity.
	 * If there isn't such user, it should inform user.
	 * @param v View of the button
	 */
	public void submitAuthentication(View v){
		Log.i(LOG_CAT_TAG, "submitted");
		String username = ((EditText)findViewById(freeuni.android.delegator.R.id.user_name)).getText().toString();
		String password = ((EditText) findViewById(freeuni.android.delegator.R.id.password)).getText().toString();
		if(checkCredentials(password,password)){
			Intent homeIntent = new Intent(this, HomeActivity.class);
			homeIntent.putExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME, username);
			startActivity(homeIntent);
		}else{
			Toast.makeText(getApplicationContext(), freeuni.android.delegator.R.string.la_wrong_pass_or_name, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Checking credentials of user. returns true if given information matches
	 * @param name user name
	 * @param pass password
	 * @return false if there is not such user with password, true otherwise
	 */
	public boolean checkCredentials(String name, String pass){
		//TODO
		return true;
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
