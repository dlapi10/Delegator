package freeuni.android.delegator.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.communicator.DatabaseCommunicator;
import freeuni.android.delegator.communicator.SyncWithServerListeners;
import freeuni.android.delegator.communicator.TaskEventListener;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.TaskStatus;
import freeuni.android.delegator.model.User;
import freeuni.android.delegator.ui.model.TaskListAdapter;

public class HomeActivity extends SuperActivity implements TaskEventListener, SyncWithServerListeners{

	//Private constants
	private static final String LOG_MESSAGE = "HOME";
	private static final String VISIBLE_USER_KEY = "VISIBLE_USER";


	//Private variables
	private ListView taskListView;
	private TaskListAdapter taskListAdapter;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private User visibleUser = null;
	private Menu menu;


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

	//Saving information
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		savedInstanceState.putString(VISIBLE_USER_KEY, visibleUser.getUserName());
		super.onSaveInstanceState(savedInstanceState);
	}


	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			visibleUser = App.getDb().getUser(savedInstanceState.getString(VISIBLE_USER_KEY));
		}
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
		
		//Listeners
		App.getServerCommunicator().addSyncListener(this);
		App.getTaskEvent().addSyncListener(this);
		
		Log.i(LOG_MESSAGE, "Creation Done");
	}


	/**
	 * Retrieving tasks from database
	 */
	private void retrieveTasks(){
		DatabaseCommunicator db = App.getDb();
		if(visibleUser==null){
			visibleUser = App.getDb().getUser(userName);
		}
		tasks.clear(); //It should be same object to notify data set changed
		tasks.addAll((ArrayList<Task>) db.getTasksForAssignee(visibleUser));
		Log.i(LOG_MESSAGE, "Retrieving done");
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
					Log.i(LOG_MESSAGE,"task # "+tasks.get(position).getTaskID());
					taskIntent.putExtra(TaskActivity.EXTRA_TASK_ID, tasks.get(position).getTaskID());
					taskIntent.putExtra(TaskActivity.EXTRA_ASSIGNEE, visibleUser.getUserName());
					startActivity(taskIntent);
				}
			});
			taskListView.setTextFilterEnabled(true);
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
		this.menu = menu;
		
		 MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				onQueryTextChange(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (TextUtils.isEmpty(newText)) {
					taskListView.clearTextFilter();
				} else {
					taskListView.setFilterText(newText.toString());
					((TaskListAdapter) taskListAdapter).getFilter().filter(newText.toString());
				}
				return true;
			}
		});
		
		Log.i(LOG_MESSAGE,"Options menu done");
		hideClosedTasks(null);
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

	/**
	 * Shows all tasks, hidden or not
	 * @param item
	 */
	public void showAllTasks(MenuItem item){
		retrieveTasks();
		((TaskListAdapter) taskListAdapter).notifyDataSetChanged();
		item.setEnabled(false);
		MenuItem it = menu.findItem(R.id.hide_closed_tasks);
		it.setEnabled(true);
	}

	/**
	 * Hides Closed and 
	 * @param item
	 */
	public void hideClosedTasks(MenuItem item){
		removeClosedTasks();
		((TaskListAdapter) taskListAdapter).notifyDataSetChanged();
		if(item!=null)
			item.setEnabled(false);
		MenuItem it = menu.findItem(R.id.show_all_tasks);
		it.setEnabled(true);
	}

	/**
	 * Sorting 
	 * @param item
	 */
	public void sort(MenuItem item){
		Log.i(LOG_MESSAGE, item.getTitle().toString());
		Log.i(LOG_MESSAGE, ""+getResources().getString(R.id.sort_by_priority));
		Log.i(LOG_MESSAGE, ""+item.getTitle().toString().equals(getResources().getString(R.id.sort_by_priority)));
		if(item.getTitle().toString().equals(getResources().getString(R.id.sort_by_priority))){
			Collections.sort(tasks,new TaskComparatorByPriority());
		}else{
			Collections.sort(tasks, new TaskComparatorByDue());
		}
		((TaskListAdapter) taskListAdapter).notifyDataSetChanged();
	}


	/**
	 * Removes closed tasks from List
	 */
	private void removeClosedTasks(){
		for(int i=0;i<tasks.size();i++){
			if(!TaskStatus.isCurrentStatus(tasks.get(i).getStatus().getStatusName())){
				tasks.remove(i);
				i--;
			}
		}
	}

	/**
	 * Shows my tasks if don't see them
	 */
	@Override
	public void goHome(View v) {
		if(visibleUser.getUserName().equals(userName))
			super.goHome(v);
		else{
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Comparator for sorting Tasks by priority
	 * @author Admin
	 */
	class TaskComparatorByPriority implements Comparator<Task>{

		@Override
		public int compare(Task t1, Task t2) {
			if(t2.getPriority() < t1.getPriority()){
				return 1;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Comparator for sorting Tasks by due dates
	 * @author Admin
	 */
	class TaskComparatorByDue implements Comparator<Task>{

		@Override
		public int compare(Task t1, Task t2) {
			if(t1.getDeadLine()==null)
				return -1;
			if(t2.getDeadLine()==null || t2.getDeadLine().before(t1.getDeadLine())){
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public void onNewTaskAssigned(Task task) {
		taskListAdapter.notifyDataSetChanged();
	}

	@Override
	public void synced() {
		taskListAdapter.notifyDataSetChanged();
	}
	
	/**
	 * REFRESH	
	 */
	public void onRefresh(MenuItem item) {
		App.getServerCommunicator().synchronizeWithServer();
		
	}

}
