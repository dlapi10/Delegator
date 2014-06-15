package freeuni.android.delegator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
	
}
