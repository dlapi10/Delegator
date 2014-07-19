package freeuni.android.delegator.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.communicator.DatabaseCommunicator;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.User;
import freeuni.android.delegator.ui.model.UserListAdapter;

public class SubordinatesActivity extends SuperActivity {
	//Private constants
	private static final String LOG_MESSAGE = "Subordinates";
	private static final String POTENTIAL_GROUP_LIST = "POTENTIAL_GROUP_LIST";

	//Private variables
	private ListView subordinateListView;
	private ListAdapter subordinateListAdapter;
	private ArrayList<User> subordinates;
	private ArrayList<Integer> potentialGroup;

	private Group group;

	/**
	 * Retrieving group ID
	 */
	private void handleIncomingIntent(){
		Intent intent = getIntent();
		if(intent!=null){
			int groupID = intent.getIntExtra(INTENT_EXTRA_MESSAGE_KEY_GROUP_ID,0);
			if(groupID!=0){
				group = App.getDb().getGroup(groupID);
			}
			intent.removeExtra(INTENT_EXTRA_MESSAGE_KEY_GROUP_ID);
		}
	}

	//Saving information
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		savedInstanceState.putIntegerArrayList(POTENTIAL_GROUP_LIST, potentialGroup);
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * Method called after creating of activity.
	 * Sets content up.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIncomingIntent();
		potentialGroup = new ArrayList<Integer>();
		Log.i(LOG_MESSAGE,"onCreate");
		this.setTitle(getResources().getString(R.string.navigation_subordinates));
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.subordinates_list);
		stub.inflate();

		retrieveSubordinates();
		setupList();
		if(savedInstanceState!=null){
			potentialGroup = savedInstanceState.getIntegerArrayList(POTENTIAL_GROUP_LIST);
			for(int i=0;i<potentialGroup.size();i++){
				View v = subordinateListAdapter.getView(potentialGroup.get(i), null, subordinateListView);
				if(v!=null)
					v.setBackgroundColor(getResources().getColor(R.color.blue));
			}
		}
	}


	/**
	 * Retrieving tasks from database
	 */
	private void retrieveSubordinates(){
		DatabaseCommunicator db = App.getDb();
		Log.i(LOG_MESSAGE, "starting sub down for manager"+userName);
		if(group!=null){
			subordinates = (ArrayList<User>) group.getGroup();
		}else{
			subordinates = (ArrayList<User>) db.getSubordinatesForManager(db.getUser(userName));
		}
		Log.i(LOG_MESSAGE, "retrieving done, users"+subordinates.size());
	}

	/**
	 * Setup for List
	 */
	private void setupList(){
		if(subordinates!=null){
			subordinateListView = (ListView)findViewById(R.id.subordinate_list);
			subordinateListAdapter = new UserListAdapter(getLayoutInflater(),subordinates);
			subordinateListView.setAdapter(subordinateListAdapter);
			subordinateListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent homeIntent = new Intent(getBaseContext(), HomeActivity.class);
					homeIntent.putExtra(INTENT_EXTRA_MESSAGE_KEY_USER_NAME, subordinates.get(position).getUserName());
					startActivity(homeIntent);
				}
			});
			System.out.println("On item Click "+subordinateListView.isClickable());
			subordinateListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					if(!potentialGroup.contains(position)){ 
						view.findViewById(R.id.colored_layout).setBackgroundColor(getResources().getColor(R.color.blue));
						potentialGroup.add(position);
					}else{
						view.findViewById(R.id.colored_layout).setBackgroundColor(getResources().getColor(R.color.light_grey));
						potentialGroup.remove(Integer.valueOf(position));
					}
					return true;
				}
			});
			System.out.println("ON Item Long Clickable "+subordinateListView.isLongClickable());
			
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
	 * Menu for the Subordinates activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.subordinates_menu, menu); // Adding menu items to the super activity menu
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Create group from given subordinates
	 * @param item
	 */
	public void createGroup(MenuItem item){
		if(potentialGroup.size()<=0)
			return;
		Group newGroup = new Group();
		newGroup.setGroupID(App.getDb().addGroup(newGroup, App.getDb().getUser(userName)));
		ArrayList<User> usersToAdd = new ArrayList<>();
		for(int i=0;i<potentialGroup.size();i++){
			User usr = subordinates.get(potentialGroup.get(i));
			usersToAdd.add(usr);
			App.getDb().addUserToGroup(newGroup, usr);
		}
		newGroup.setGroup(usersToAdd);
		Intent groupNaming = new Intent(getBaseContext(), GroupNaming.class);
		groupNaming.putExtra(INTENT_EXTRA_MESSAGE_KEY_GROUP_ID, newGroup.getGroupID());
		startActivity(groupNaming);
		Log.i(LOG_MESSAGE, "start group naming activity");
	}

	/**
	 * Call to subordinate
	 * @param v
	 */
	public void call(View v){
		int position = subordinateListView.getPositionForView(v);
		User callUser = subordinates.get(position);
		String phoneNumber = callUser.getPhoneNumber();
		if(phoneNumber!=null){
			Intent dial = new Intent (Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber)); 
			startActivity(dial);
		}else{
			Toast.makeText(this, getResources().getString(R.string.invalid_number), Toast.LENGTH_SHORT).show();
		}
	}

	
	/**
	 * Shows subordinates
	 * @param v
	 */
	@Override
	public void showSubordinates(View v){
		if(group==null)
			super.showSubordinates(v);
		else{
			Intent intent = new Intent(this, SubordinatesActivity.class);
			startActivity(intent);
		}
	}
}
