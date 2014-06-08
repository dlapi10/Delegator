package freeuni.android.delegator.app;

import freeuni.android.delegator.communicator.FakeCommunicator;
import freeuni.android.delegator.communicator.NetworkCommunicator;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application{
	private static int avatarDimension;
	private static Context cntxt;
	private static String preferenceFile;
	private static NetworkCommunicator communicator;
	
	@Override
	public void onCreate() {
		super.onCreate();
		cntxt = getApplicationContext(); 
		preferenceFile = getString(freeuni.android.delegator.R.string.preference_file_key);
		initApp();
	}
	
	/**
	 * Initializing application
	 */
	private void initApp() {
		communicator = new FakeCommunicator();
		avatarDimension = getResources().getDimensionPixelSize(freeuni.android.delegator.R.dimen.user_image_size);
	
		//Starting Download contacts 
		SharedPreferences sharedPref = cntxt.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
		
		
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
}
