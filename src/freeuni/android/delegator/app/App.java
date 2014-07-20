package freeuni.android.delegator.app;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import freeuni.android.delegator.R;
import freeuni.android.delegator.communicator.DatabaseCommunicator;
import freeuni.android.delegator.communicator.DatabaseCommunicatorDB;
import freeuni.android.delegator.communicator.FakeCommunicator;
import freeuni.android.delegator.communicator.NetworkCommunicator;
import freeuni.android.delegator.communicator.ServerCommunicator;
import freeuni.android.delegator.communicator.TaskEvent;
import freeuni.android.delegator.communicator.TaskEventListener;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.test.FillBase;
import freeuni.android.delegator.ui.HomeActivity;

public class App extends Application implements TaskEventListener{
	private static int avatarDimension;
	private static String preferenceFile;
	private static NetworkCommunicator communicator;
	private static Activity currentActivity = null;
	private static Context cntxt;
	private static DatabaseCommunicator db;
	private static ServerCommunicator serverCommunicator;
	private static TaskEvent taskEvent;
	
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
		cntxt = getApplicationContext(); 
		communicator = new FakeCommunicator();
		serverCommunicator = new ServerCommunicator();
		serverCommunicator.initialize();
		db = new DatabaseCommunicatorDB();
		db.initialize();
		fillForTest();
		avatarDimension = getResources().getDimensionPixelSize(freeuni.android.delegator.R.dimen.user_image_size);
		taskEvent = new TaskEvent();
		taskEvent.addTaskEventListener(this);
	}

	public static Context getAppContext(){
		return cntxt;
	}

	private void fillForTest(){
		SharedPreferences sharedPref = cntxt.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
		if(sharedPref.getBoolean(IS_FIRST_INIT, true)){ //Checking if this is first run of the app on device
			FillBase.fillBase();
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(IS_FIRST_INIT, false);
			editor.commit();
		}
		serverCommunicator.synchronizeWithServer();
	}

	/**
	 * @return the db
	 */
	public static DatabaseCommunicator getDb() {
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

	@Override
	public void onNewTaskAssigned(Task task) {
		int notificationId = 001;
		// Build intent for notification content
		Intent viewIntent = new Intent(this, HomeActivity.class);
		viewIntent.putExtra("NEW_TASK", task.getTaskID());
		PendingIntent viewPendingIntent =
		        PendingIntent.getActivity(this, 0, viewIntent, 0);

		NotificationCompat.Builder notificationBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_new_task_notification)
		        .setContentTitle("New Task")
		        .setContentText("You've been assigned to "+task.getTitle() + " by " + task.getReporter())
		        .setContentIntent(viewPendingIntent)
		        .setPriority(1);

		// Get an instance of the NotificationManager service
		NotificationManager notificationManager =
				(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

		// Build the notification and issues it with notification manager.
		notificationManager.notify(notificationId, notificationBuilder.build());
		Log.i("APP", "Displayed new task notification");
	}

	/**
	 * returning sevrercommunicator
	 * @return
	 */
	public static ServerCommunicator getServerCommunicator(){
		return serverCommunicator;
	}
	
	/**
	 * returning taskeven
	 * @return
	 */
	public static ServerCommunicator getTaskEvent(){
		return serverCommunicator;
	}
	
}