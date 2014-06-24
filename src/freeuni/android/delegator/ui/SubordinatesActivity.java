package freeuni.android.delegator.ui;

import java.util.ArrayList;
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
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.User;
import freeuni.android.delegator.ui.model.UserListAdapter;

public class SubordinatesActivity extends SuperActivity {
	//Private constants
	private static final String LOG_MESSAGE = "Subordinates";

	//Private variables
	private ListView subordinateListView;
	private ListAdapter subordinateListAdapter;
	private ArrayList<User> subordinates;

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_MESSAGE,"onCreate");
		this.setTitle(getResources().getString(R.string.navigation_subordinates));

		retrieveSubordinates();
		setupList();

		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.subordinates_list);
		stub.inflate();
	}


	/**
	 * Retrieving tasks from database
	 */
	private void retrieveSubordinates(){
		DBManager db = App.getDb();
		subordinates = (ArrayList<User>) db.getSubordinatesForManager(db.getUser(userName));
	}

	/**
	 * Setup for List
	 */
	private void setupList(){
		subordinateListView = (ListView)findViewById(R.id.task_list);
		subordinateListAdapter = new UserListAdapter(getLayoutInflater(),subordinates);
		subordinateListView.setAdapter(subordinateListAdapter);
		subordinateListView.setOnItemClickListener(new OnItemClickListener() {
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
}
