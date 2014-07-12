package freeuni.android.delegator.ui.model;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.User;

public class UserListAdapter extends BaseAdapter{
	private ArrayList<User> users;
	private LayoutInflater inflater;

	public UserListAdapter(LayoutInflater inflater, ArrayList<User> items){
		this.users = items;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int index) {
		return users.get(index);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("USerListAdapter", "getView started");
		UserHolder userHolder;
		if(convertView == null){
			convertView = inflater.inflate(freeuni.android.delegator.R.layout.subordinate_list_item, null,false);
			userHolder = new UserHolder();
			userHolder.userName = (TextView)convertView.findViewById(freeuni.android.delegator.R.id.user_name);
			userHolder.avatar = (ImageView)convertView.findViewById(freeuni.android.delegator.R.id.subordinate_avatar);
			userHolder.currentTaskCount = (TextView) convertView.findViewById(freeuni.android.delegator.R.id.current_tasks);
			convertView.setTag(userHolder);
		}else {
			userHolder = (UserHolder) convertView.getTag();
		}
		User subordinate = users.get(position);
		if(subordinate.getAvatar()==null){
			userHolder.avatar.setImageResource(freeuni.android.delegator.R.drawable.default_user_image);
		}else{
			userHolder.avatar.setImageBitmap(subordinate.getAvatar());
		}
		userHolder.userName.setText(subordinate.getUserName());
		userHolder.currentTaskCount.setText( Integer.toString( App.getDb().getCurrentTaskCountForUser(subordinate) ) );
		System.out.println("Before Set Long Clickable:"+convertView.isLongClickable());
		System.out.println("Before is it Clickable?"+convertView.isClickable());
//		convertView.setLongClickable(true);
//		convertView.setClickable(true);
//		System.out.println("After Set Long Clickable: "+convertView.isLongClickable());
//		System.out.println("Afer Set: is Clickable: "+convertView.isClickable());
		return convertView;
	}

	private static class UserHolder{
		ImageView avatar;
		TextView userName;
		TextView currentTaskCount;
	}

}
