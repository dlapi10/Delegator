package freeuni.android.delegator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.Task;

public class TaskActivity extends SuperActivity{
	
	public static final String EXTRA_TASK_ID = "freeuni.android.delegator.ui.TaskActivity.NEW_TASK";
	
	
	private Task thisTask;
	
	/**
	* Handling Incoming Intent;
	*/
	private void handleIncomingIntent(){
		Intent intent = getIntent();
	 	if(intent!=null){
	 		int taskID = intent.getIntExtra(EXTRA_TASK_ID,-1);
	 		if(taskID!=-1){
	 			thisTask = App.getDb().getTask(taskID);
	 			this.setTitle(thisTask.getTitle());
	 		}else{ 
	 			thisTask = new Task();
	 			thisTask.setTaskID(App.getDb().addTask(thisTask));
	 			this.setTitle(getResources().getString(R.string.task_default_title));
	 		}
	 		intent.removeExtra(EXTRA_TASK_ID);
	 	}
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIncomingIntent();
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.task);
		stub.inflate();
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
