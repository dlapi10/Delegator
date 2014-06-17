package freeuni.android.delegator.ui.model;

import java.util.ArrayList;
import freeuni.android.delegator.model.User;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
