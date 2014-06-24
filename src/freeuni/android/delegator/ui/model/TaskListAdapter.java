package freeuni.android.delegator.ui.model;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
		return tasks.size();
	}

	@Override
	public Object getItem(int index) {
		return tasks.get(index);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskHolder taskHolder;
		if(convertView == null){
			convertView = inflater.inflate(freeuni.android.delegator.R.layout.task_list_item, null,false);
			taskHolder = new TaskHolder();
			taskHolder.assigneeAssigner  = (ImageView)convertView.findViewById(freeuni.android.delegator.R.id.assignee_assigner);
			taskHolder.taskName = (TextView)convertView.findViewById(freeuni.android.delegator.R.id.task_name);
			taskHolder.taskStatus = (TextView) convertView.findViewById(freeuni.android.delegator.R.id.task_status);
			taskHolder.taskPriority = (TextView) convertView.findViewById(freeuni.android.delegator.R.id.task_priority);
			convertView.setTag(taskHolder);
		}else {
			taskHolder = (TaskHolder) convertView.getTag();
		}
		Task task = tasks.get(position);
		if(task.getReporter().getAvatar()==null){ // TODO not only reporter but assignee
			taskHolder.assigneeAssigner.setImageResource(freeuni.android.delegator.R.drawable.default_user_image);
		}else{
			taskHolder.assigneeAssigner.setImageBitmap(task.getReporter().getAvatar()); // TODO Which one, reporter or assignee?
		}
		taskHolder.taskName.setText(task.getTitle());
		taskHolder.taskStatus.setText(task.getStatus().getStatusName());
		taskHolder.taskPriority.setText(Integer.toString(task.getPriority()));
		return convertView;
	}


	private static class TaskHolder{
		ImageView assigneeAssigner;//Depends which list is used
		TextView taskName;
		TextView taskStatus;
		TextView taskPriority;
	}
}
