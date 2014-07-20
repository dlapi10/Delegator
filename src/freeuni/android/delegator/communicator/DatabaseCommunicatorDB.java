package freeuni.android.delegator.communicator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import freeuni.android.delegator.app.App;
import freeuni.android.delegator.db.DBManager;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.User;

public class DatabaseCommunicatorDB implements DatabaseCommunicator{
	private static DBManager db;

	@Override
	public void initialize() {
		db = new DBManager(App.getAppContext());
		
	}

	@Override
	public void addUser(User user) {
		db.addUser(user);
	}

	@Override
	public String updateUser(User user) {
		return db.updateUser(user);
	}

	@Override
	public User getUser(String userName) {
		return db.getUser(userName);
	}

	@Override
	public int addGroup(Group group, User user) {
		return db.addGroup(group, user);
	}

	@Override
	public void updateGroupName(Group group) {
		db.updateGroupName(group);
	}

	@Override
	public void addUserToGroup(Group group, User user) {
		db.addUserToGroup(group, user);
	}

	@Override
	public Group getGroup(int id) {
		return getGroup(id);
	}

	@Override
	public List<Group> getUsersGroups(User user) {
		return getUsersGroups(user);
	}

	@Override
	public void setSubortinate(User manager, User subordinate) {
		db.setSubortinate(manager, subordinate);
	}

	@Override
	public List<User> getSubordinatesForManager(User manager) {
		return db.getSubordinatesForManager(manager);
	}

	@Override
	public int addTask(Task task) {
		return db.addTask(task);
	}

	@Override
	public void updateTask(Task task) {
		db.updateTask(task);
	}

	@Override
	public void deleteTask(Task task) {
		db.deleteTask(task);
	}

	@Override
	public Task getTask(int id) {
		return db.getTask(id);
	}

	@Override
	public List<Task> getTasksForReporter(User reporter) {
		return db.getTasksForReporter(reporter);
	}

	@Override
	public List<Task> getTasksForAssignee(User assignee) {
		return db.getTasksForAssignee(assignee);
	}

	@Override
	public int getCurrentTaskCountForUser(User user) {
		return db.getCurrentTaskCountForUser(user);
	}

}
