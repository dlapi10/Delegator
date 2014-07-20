package freeuni.android.delegator.communicator;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.User;

public class ServerCommunicator implements DatabaseCommunicator, OnServerMessageReceived{
	//Constants; Headers
	public static final String MSG_SYNC_ALL="SYNC_ALL";
	public static final String MSG_ADD_TASK="ADD_TASK";
	public static final String MSG_ADD_GROUP="ADD_GROUP";
	
	//Private variables
	private ServerClient client;	
	private Thread clientThread;
	private ArrayList<SyncWithServerListeners> listeners;
//	private int returnedTaskID = 0;
//	private int returnedGroupID = 0;
//	
	
	/**
	 * Closes client thread
	 */
	public void closeServerCommunicator(){
		client.stopClient();
	}
	
	/**
	 * Synchronizes local database to the server
	 */
	public void synchronizeWithServer(){
		client.sendMessage(MSG_SYNC_ALL,null);
	}
	
	/**
	 * Adding listener of sync
	 * @param listener
	 */
	public void addSyncListener(SyncWithServerListeners listener){
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	/**
	 * removing sync listener
	 * @param listener
	 */
	public void deleteSyncListener(SyncWithServerListeners listener){
		if(listeners.contains(listener))
			listeners.remove(listener);
	}
	
	@Override
	public void initialize() {
		//clientExternalIP = Processing.getLocalIpAddress();
		listeners = new ArrayList<SyncWithServerListeners>();
		//Clientis gashveba
		client = new ServerClient();
		client.addListeners(this);
		clientThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				client.runClient();
			}
			
		});
		clientThread.start();
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

	/**
	 * Adding task to the server
	 */
	@Override
	public int addTask(final Task task) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Gson gson = new GsonBuilder().create();
				String message = gson.toJson(task);
				client.sendMessage(MSG_ADD_TASK, message);
			}
		});
		t.start();
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

	@Override
	public void messageReceived(String header, String message) {
		// TODO Auto-generated method stub
		if(header.equals(MSG_ADD_TASK)){
		//	returnedTaskID = Integer.parseInt(message);
		}else if(header.equals(MSG_SYNC_ALL)){
			//TODO DB changes
			for(int i=0;i<listeners.size();i++){
				listeners.get(i).synced();
			}
		}else if(header.equals(MSG_ADD_GROUP)){
			
		}
	}

}
