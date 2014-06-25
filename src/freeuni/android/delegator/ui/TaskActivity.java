package freeuni.android.delegator.ui;

import freeuni.android.delegator.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TaskActivity extends SuperActivity{
	
	public static final String EXTRA_TASK_ID = "freeuni.android.delegator.ui.TaskActivity.NEW_TASK";

	/**
	* Handling Incoming Intent;
	*/
	private void handleIncomingIntent(){
		Intent intent = getIntent();
	 	if(intent!=null){
	 		String name = intent.getStringExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME);
	 		if(name!=null)
	 			//visibleUser = new User(name);
	 		intent.removeExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME);
	 	}
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIncomingIntent();
	}
	
	/**
	 * Delete task
	 * @param item
	 */
	public void deleteTask(MenuItem item){
		//TODO
	}
	
	/**
	 * Save task
	 * @param item
	 */
	public void saveTask(MenuItem item){
		//TODO
	}
	
	/**
	 * Menu for the Task activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_menu, menu); // Adding menu items to the super activity menu
		return super.onCreateOptionsMenu(menu);
	}
}
