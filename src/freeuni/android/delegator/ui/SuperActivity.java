package freeuni.android.delegator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.User;

public class SuperActivity extends Activity {

	// Public constants
	public static final String INTENT_SIGN_OUT_MESSAGE = "freeuni.android.delegator.ui.LoginActivity.log.out";
	public static final String INTENT_EXTRA_MESSAGE_KEY_USER_NAME = "freeuni.android.delegator.ui.SuperActivity.usernameKey";
	public static final String INTENT_EXTRA_MESSAGE_KEY_GROUP_ID = "freeuni.android.delegator.ui.SuperActivity.groupID";

	// Private variables
	private DrawerLayout drawer;
	private ActionBarDrawerToggle drawerToggle;
	protected String userName;

	/**
	 * Signing out from any activity
	 */
	public void onSignOut(MenuItem item) {
		Intent logInIntent = new Intent(this, LoginActivity.class);
		logInIntent.putExtra(INTENT_SIGN_OUT_MESSAGE, true);
		startActivity(logInIntent);
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		App.setCurrentActivity(this); // To know which activity is this
		setContentView(freeuni.android.delegator.R.layout.navigation_drawer);
		SharedPreferences pref = getSharedPreferences(App.getPrefFile(),
				Context.MODE_PRIVATE);
		userName = pref.getString(getString(R.string.active_session_username),
				null);
		
		User currentUser = App.getDb().getUser(userName);
		Bitmap avatar = currentUser.getAvatar();
		
		((TextView) findViewById(R.id.profile_name)).setText(userName);
		if(avatar != null)
			((ImageView) findViewById(R.id.profile_image)).setImageBitmap(avatar);
		// TODO To set real user profile
		super.onCreate(savedInstanceState);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle( // Drawer toggle code based on
													// android developer's
													// website
				this, /* host Activity */
				drawer, /* DrawerLayout object */
				R.drawable.ic_drawer, /*
									 * navigation drawer icon to replace 'Up'
									 * caret
									 */
				R.string.drawer_open, /* "open drawer" description */
				R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		drawer.setDrawerListener(drawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	/*
	 * Listeners
	 */

	/**
	 * Handles where to go or stay. If we are on the same activity we stay,
	 * elsewhere we go to another
	 * 
	 * @param activity
	 */
	protected void handleReference(Class<?> activity) {
		if (activity.isInstance(App.getCurrentActivity())) {
			drawer.closeDrawer(Gravity.LEFT);
		} else {
			Intent intent = new Intent(this, activity);
			startActivity(intent);
		}
	}

	/**
	 * Shows home, my tasks, If clicked on navigation drawable
	 * 
	 * @param v
	 */
	public void goHome(View v) {
		handleReference(HomeActivity.class);
	}

	/**
	 * Shows subordinates
	 * 
	 * @param v
	 */
	public void showSubordinates(View v) {
		handleReference(SubordinatesActivity.class);
	}

	/**
	 * Shows my groups of subordinates
	 * 
	 * @param v
	 */
	public void showGroups(View v) {
		handleReference(GroupsActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** aqedan wasashlelia */
	public void goToSettings(View v) {
		handleReference(ProfileSettings.class);
	}
}
