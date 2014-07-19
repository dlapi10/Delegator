package freeuni.android.delegator.communicator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.User;

public interface DatabaseCommunicator {
	public void initialize();
	public void addUser(User user);
	public String updateUser(User user);
	public User getUser(String userName);
	public int addGroup(Group group, User user);
	public void updateGroupName(Group group);
	public void addUserToGroup(Group group, User user);
	public Group getGroup(int id);
	public List<Group> getUsersGroups(User user);
	public void setSubortinate(User manager, User subordinate);
	public List<User> getSubordinatesForManager(User manager);
	public int addTask(Task task);
	public void updateTask(Task task);
	public void deleteTask(Task task);
	public Task getTask(int id);
//	public static Calendar DateToCalendar(Date date);
	public List<Task> getTasksForReporter(User reporter);
	public List<Task> getTasksForAssignee(User assignee);
	public int getCurrentTaskCountForUser(User user);
}
