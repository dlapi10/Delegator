package freeuni.android.delegator.communicator;

import java.util.List;

import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.User;

public class ServerCommunicator implements DatabaseCommunicator{
	
	//Private variables
	private ServerClient client;
	
	@Override
	public void initialize() {
		//clientExternalIP = Processing.getLocalIpAddress();
		// TODO
		client = new ServerClient();
		client.runClient();
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addGroup(Group group, User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateGroupName(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToGroup(Group group, User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group getGroup(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getUsersGroups(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubortinate(User manager, User subordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getSubordinatesForManager(User manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addTask(Task task) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateTask(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTask(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task getTask(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasksForReporter(User reporter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasksForAssignee(User assignee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCurrentTaskCountForUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
