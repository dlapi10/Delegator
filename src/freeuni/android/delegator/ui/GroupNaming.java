package freeuni.android.delegator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import freeuni.android.delegator.R;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.Group;

public class GroupNaming extends SuperActivity{

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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIncomingIntent();
		//Setting layout to the stub
		ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
		stub.setLayoutResource(R.layout.group_naming);
		stub.inflate();
	}
	
	/**
	 * Submitting group name
	 * @param v
	 */
	public void submitName(View v){
		TextView nameView = (TextView)findViewById(R.id.group_name);
		String name = nameView.getText().toString();
		if(name!=null){
			group.setGroupName(name);
			App.getDb().updateGroupName(group);
		}
		Intent groupIntent = new Intent(this, GroupsActivity.class);
		startActivity(groupIntent);
	}
	
	/**
	 * Menu for the Subordinates activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
}
