package freeuni.android.delegator.model;

import java.util.Calendar;
import java.util.List;

public class Task {
	
	// Public constants
	
	
	//Private Variables
	private int taskID;
	private User reporter;
	private boolean assigneeType;
	private List<User> assignees;
	private Group assigneeGroup;
	private TaskStatus status;
	private int priority;
	private Calendar startDate; //Use Date;Calendar;GregorianCalendar
	private Calendar deadLine;
	private int completionPercent;
	private String title;
	private String description;
	private List<TaskCategory> categories;
	private List<String> tags;
	private Calendar reminder;
	private String voiceMessagePath; // TODO
	private Work work; 
	private List<Integer> subtaskIDs;
	private List<Comment> commments;
	
	
	
}
