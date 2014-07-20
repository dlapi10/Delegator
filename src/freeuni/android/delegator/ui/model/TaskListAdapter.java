package freeuni.android.delegator.ui.model;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import freeuni.android.delegator.model.Task;

public class TaskListAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Task> tasks;
	private ArrayList<Task> originalTasks;
	private LayoutInflater inflater;
	private TaskListFilter filter;

	public TaskListAdapter(LayoutInflater inflater, ArrayList<Task> items) {
		this.tasks = items;
		this.inflater = inflater;
		originalTasks = new ArrayList<>();
		this.originalTasks.addAll(tasks);
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
		if (convertView == null) {
			convertView = inflater.inflate(
					freeuni.android.delegator.R.layout.task_list_item, null,
					false);
			taskHolder = new TaskHolder();
			taskHolder.assigneeAssigner = (ImageView) convertView
					.findViewById(freeuni.android.delegator.R.id.assignee_assigner);
			taskHolder.taskName = (TextView) convertView
					.findViewById(freeuni.android.delegator.R.id.task_name);
			taskHolder.taskStatus = (TextView) convertView
					.findViewById(freeuni.android.delegator.R.id.task_status);
			taskHolder.taskPriority = (TextView) convertView
					.findViewById(freeuni.android.delegator.R.id.task_priority);
			convertView.setTag(taskHolder);
		} else {
			taskHolder = (TaskHolder) convertView.getTag();
		}
		Task task = tasks.get(position);
		if (task.getReporter().getAvatar() == null) { // TODO not only reporter
														// but assignee
			taskHolder.assigneeAssigner
					.setImageResource(freeuni.android.delegator.R.drawable.default_user_image);
		} else {
			taskHolder.assigneeAssigner.setImageBitmap(task.getReporter()
					.getAvatar()); // TODO Which one, reporter or assignee?
		}
		taskHolder.taskName.setText(task.getTitle());
		taskHolder.taskStatus.setText(task.getStatus().getStatusName());
		taskHolder.taskPriority.setText(Integer.toString(task.getPriority()));
		return convertView;
	}

	private static class TaskHolder {
		ImageView assigneeAssigner;// Depends which list is used
		TextView taskName;
		TextView taskStatus;
		TextView taskPriority;
	}

	private class TaskListFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				ArrayList<Task> filteredItems = new ArrayList<Task>();
				for (Task item : originalTasks) {
					if (item.getDescription().toLowerCase()
							.contains(constraint)
							|| item.getTitle().toLowerCase()
									.contains(constraint)) {
						filteredItems.add(item);
					}
				}
				result.values = filteredItems;
				result.count = filteredItems.size();
			} else {
				synchronized (this) {
					result.values = originalTasks;
					result.count = originalTasks.size();
				}
			}
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			tasks = (ArrayList<Task>) results.values;
			notifyDataSetChanged();
		}
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new TaskListFilter();
		}
		return filter;
	}
}
