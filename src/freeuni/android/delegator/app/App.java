package freeuni.android.delegator.app;

import android.app.Activity;
import android.app.Application;
import freeuni.android.delegator.communicator.FakeCommunicator;
import freeuni.android.delegator.communicator.NetworkCommunicator;

public class App extends Application{
	private static int avatarDimension;
	private static String preferenceFile;
	private static NetworkCommunicator communicator;
	private static Activity currentActivity = null;
	
	//Private variables
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		preferenceFile = getString(freeuni.android.delegator.R.string.preference_file_key);
		initApp();
	}
	
	/**
	 * Initializing application
	 */
	private void initApp() {
		communicator = new FakeCommunicator();
		
		avatarDimension = getResources().getDimensionPixelSize(freeuni.android.delegator.R.dimen.user_image_size);
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
