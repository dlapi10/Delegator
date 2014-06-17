package freeuni.android.delegator.ui.model;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import freeuni.android.delegator.model.Task;

public class TaskListAdapter extends BaseAdapter{
	private ArrayList<Task> tasks;
	private LayoutInflater inflater;
	
	public TaskListAdapter(LayoutInflater inflater, ArrayList<Task> items) {
		this.tasks = items;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
