package freeuni.android.delegator.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.User;
import freeuni.android.delegator.ui.model.TaskListAdapter;

public class HomeActivity extends SuperActivity{

	//Private constants
	private static final String LOG_MESSAGE = "HOME";


	//Private variables
	private ListView taskListView;
	private ListAdapter taskListAdapter;
	private ArrayList<Task> tasks;
	private User visibleUser = null;


	/*
	 * Activity LifeCycle and helper Methods, dedicated to this cycle
	 */

	/**
	 * Handling Incoming Intent;
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null){
			String name = intent.getStringExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME);
			if(name!=null)
				visibleUser = App.getDb().getUser(name);
			intent.removeExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME);
		}
	}


	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_MESSAGE,"onCreate");
		handleIncomingIntent();
		if(visibleUser==null || visibleUser.getUserName().equals(userName)){
			this.setTitle(getResources().getString(R.string.navigation_home));
		}else{
			this.setTitle(visibleUser.getUserName()+"'s tasks");
		} 
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.tasks_list);
		stub.inflate();

		retrieveTasks();
		setupList();

	}


	/**
	 * Retrieving tasks from database
	 */
	private void retrieveTasks(){
		DBManager db = App.getDb();
		if(visibleUser==null){
			visibleUser = App.getDb().getUser(userName);
		}
		tasks = (ArrayList<Task>) db.getTasksForAssignee(visibleUser);
	}

	/**
	 * Setup for List
	 */
	private void setupList(){
		if(tasks!=null){
			taskListView = (ListView)findViewById(R.id.task_list);
			taskListAdapter = new TaskListAdapter(getLayoutInflater(),tasks);
			taskListView.setAdapter(taskListAdapter);
			taskListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent taskIntent = new Intent(getBaseContext(), TaskActivity.class);
					taskIntent.putExtra(TaskActivity.EXTRA_TASK_ID, tasks.get(position).getTaskID());
					startActivity(taskIntent);
				}
			});
		}
	}


	/**
	 * On resume
	 */
	@Override
	protected void onResume() {
		Log.i(LOG_MESSAGE,"onResume");
		super.onResume();
	}

	/**
	 * On Pause
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onPause");
		super.onPause();
	}

	/**
	 * 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onStart");
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(LOG_MESSAGE,"onStop");
		super.onStop();
	}

	/**
	 * Menu for the Home activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tasks_menu, menu); // Adding menu items to the super activity menu
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Adding task after onclick
	 * @param item
	 */
	public void addNewTask(MenuItem item){
		Intent taskIntent = new Intent(this, TaskActivity.class);
		taskIntent.putExtra(TaskActivity.EXTRA_ASSIGNEE, visibleUser.getUserName());
		Log.i(LOG_MESSAGE, visibleUser.getUserName());
		startActivity(taskIntent);
	}

}
