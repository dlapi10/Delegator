package freeuni.android.delegator.communicator;

import freeuni.android.delegator.model.Task;

public interface TaskEventListener {
	public void onNewTaskAssigned(Task task);
}
