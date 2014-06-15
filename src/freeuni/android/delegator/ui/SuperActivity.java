package freeuni.android.delegator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;

public class SuperActivity extends Activity{
	
	// Public constants
	public static final String INTENT_SIGN_OUT_MESSAGE  = "freeuni.android.delegator.ui.LoginActivity.log.out";
	
	// Private variables
	private DrawerLayout drawer;
	private ActionBarDrawerToggle drawerToggle;
	
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
		((TextView)findViewById(R.id.profile_name)).setText(userName);
		//TODO To set real user profile
		super.onCreate(savedInstanceState);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle( // Drawer toggle code based on android developer's website
                this,                  /* host Activity */
                drawer,     		    /* DrawerLayout object */
                R.drawable.ic_drawer,  /* navigation drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
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
	 * Shows home, my tasks, If clicked on navigation drawable
	 * @param v
	 */
	public void goHome(View v){
		drawer.closeDrawer(Gravity.LEFT);
		Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
		Intent homeIntent = new Intent(this, HomeActivity.class);
		startActivity(homeIntent);
		//TODO if current activity is already HOME don't go
	}
	
	/**
	 * Shows subordinates
	 * @param v
	 */
	public void showSubordinates(View v){
		Toast.makeText(getApplicationContext(), "Subordinates", Toast.LENGTH_LONG).show();
		Intent subordinatesIntent = new Intent(this, SubordinatesActivity.class);
		startActivity(subordinatesIntent);
		//TODO if current activity is already subordinates don't go
	}
	
	/**
	 * Shows my groups of subordinates
	 * @param v
	 */
	public void showGroups(View v){
		Toast.makeText(getApplicationContext(), "Groups", Toast.LENGTH_LONG).show();
		Intent groupIntent = new Intent(this, GroupsActivity.class);
		startActivity(groupIntent);
		//TODO if current activity is already groups don't go
	}
}
