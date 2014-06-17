package freeuni.android.delegator.ui;

import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.ui.model.TaskListAdapter;

public class HomeActivity extends SuperActivity{

	//Private constants
	private static final String LOG_MESSAGE = "HOME";


	//Private variables
	private ListView taskListView;
	private ListAdapter taskListAdapter;
	private ArrayList<Task> tasks;
	

	/*
	 * Activity LifeCycle and helper Methods, dedicated to this cycle
	 */

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_MESSAGE,"onCreate");
		this.setTitle(getResources().getString(R.string.navigation_home));
		
		retrieveTasks();
		setupList();
		
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.tasks_list);
		stub.inflate();
		
	}
	
	
	/**
	 * Retrieving tasks from database
	 */
	private void retrieveTasks(){
		//TODO
	}
	
	/**
	 * Setup for List
	 */
	private void setupList(){
		taskListView = (ListView)findViewById(R.id.task_list);
		taskListAdapter = new TaskListAdapter(getLayoutInflater(),tasks);
		taskListView.setAdapter(taskListAdapter);
		taskListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			}
		});
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
		//getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
