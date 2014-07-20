package freeuni.android.delegator.communicator;

import java.util.ArrayList;

public class TaskEvent {
	public static ArrayList<TaskEventListener> listeners = new ArrayList<>();
	public void addTaskEventListener(TaskEventListener listener){
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeTaskEventListener(TaskEventListener listener){
		if(listeners.contains(listener))
			listeners.remove(listener);
	}
}
