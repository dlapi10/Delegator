package freeuni.android.delegator.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.communicator.DatabaseCommunicator;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.ui.model.GroupListAdapter;

public class GroupsActivity extends SuperActivity{

	//Private constants
	private static final String LOG_MESSAGE = "Groups";

	//Private variables
	private ListView groupListView;
	private ListAdapter groupListAdapter;
	private ArrayList<Group> groups;

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_MESSAGE,"onCreate");
		this.setTitle(getResources().getString(R.string.navigation_groups));
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.groups_list);
		stub.inflate();
		retrieveGroups();
		setupList();
	}


	/**
	 * Retrieving groups from database
	 */
	private void retrieveGroups(){
		DatabaseCommunicator db = App.getDb();
		groups = (ArrayList<Group>) db.getUsersGroups(db.getUser(userName));
	}

	/**
	 * Setup for List
	 */
	private void setupList(){
		groupListView = (ListView)findViewById(R.id.group_list);
		groupListAdapter = new GroupListAdapter(getLayoutInflater(),groups);
		groupListView.setAdapter(groupListAdapter);
		groupListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent subordinatesIntent = new Intent(getBaseContext(), SubordinatesActivity.class);
				subordinatesIntent.putExtra(INTENT_EXTRA_MESSAGE_KEY_GROUP_ID, groups.get(position).getGroupID());
				startActivity(subordinatesIntent);
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
}
