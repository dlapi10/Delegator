package freeuni.android.delegator.ui.model;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import freeuni.android.delegator.model.Group;

public class GroupListAdapter extends BaseAdapter{
	private ArrayList<Group> groups;
	private LayoutInflater inflater;

	public GroupListAdapter(LayoutInflater inflater, ArrayList<Group> items){
		this.groups = items;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() {
		return groups.size();
	}

	@Override
	public Object getItem(int index) {
		return groups.get(index);
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
