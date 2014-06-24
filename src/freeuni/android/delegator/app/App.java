package freeuni.android.delegator.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import freeuni.android.delegator.communicator.FakeCommunicator;
import freeuni.android.delegator.communicator.NetworkCommunicator;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.test.FillBase;

public class App extends Application{
	private static int avatarDimension;
	private static String preferenceFile;
	private static NetworkCommunicator communicator;
	private static Activity currentActivity = null;
	private static DBManager db = null;
	private static Context cntxt;

	// Shared preference keys
	private static final String IS_FIRST_INIT="isFirstInit";

	//Private variables


	@Override
	public void onCreate() {
		super.onCreate();
		preferenceFile = getString(freeuni.android.delegator.R.string.preference_file_key);
		initApp();
	}

	/**
	 * Initializing application
	 * Everything fake should be changed here
	 */
	private void initApp() {
		communicator = new FakeCommunicator();
		db = new DBManager(this);
		fillForTest();
		avatarDimension = getResources().getDimensionPixelSize(freeuni.android.delegator.R.dimen.user_image_size);
	}

	private void fillForTest(){
		SharedPreferences sharedPref = cntxt.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
		if(sharedPref.getBoolean(IS_FIRST_INIT, true)){ //Checking if this is first run of the app on device
			FillBase.fillBase();
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(IS_FIRST_INIT, false);
			editor.commit();
		}else{
			//TODO
		}
		
	}

	/**
	 * @return the db
	 */
	public static DBManager getDb() {
		return db;
	}

	/**
	 * Returns avatar dimension
	 * @return
	 */
	public static int getAvatarDimension() {
		return avatarDimension;
	}

	/**
	 * Gets Communicator Object
	 * @return
	 */
	public static NetworkCommunicator getCommunicator(){
		return communicator;
	}

	public static String getPrefFile(){
		return preferenceFile;
	}

	public static Activity getCurrentActivity(){
		return currentActivity;
	}

	public static void setCurrentActivity(Activity cA){
		currentActivity = cA;
	}
}
