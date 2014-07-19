package freeuni.android.delegator.model;

import java.util.List;

import freeuni.android.delegator.ui.UserSettings;

import android.graphics.Bitmap;

public class User {
	
	// Private variables
	private String userName;
	private String firstName;
	private String lastName;
	private Bitmap avatar;
	private String password;
	private String phoneNumber;
	private List<User> subordinates;
	private List<Group> ownedGroups;
	private UserSettings settings;
	private List<Request> requests;
	
	
	/**
	 * @param userID
	 * @param userName
	 */
	public User(String userName) {
		super();
		this.userName = userName;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @return the avatar
	 */
	public Bitmap getAvatar() {
		return avatar;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @return the subordinates
	 */
	public List<User> getSubordinates() {
		return subordinates;
	}
	/**
	 * @return the ownedGroups
	 */
	public List<Group> getOwnedGroups() {
		return ownedGroups;
	}
	/**
	 * @return the settings
	 */
	public UserSettings getSettings() {
		return settings;
	}
	/**
	 * @return the requests
	 */
	public List<Request> getRequests() {
		return requests;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @param subordinates the subordinates to set
	 */
	public void setSubordinates(List<User> subordinates) {
		this.subordinates = subordinates;
	}
	/**
	 * @param ownedGroups the ownedGroups to set
	 */
	public void setOwnedGroups(List<Group> ownedGroups) {
		this.ownedGroups = ownedGroups;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(UserSettings settings) {
		this.settings = settings;
	}
	/**
	 * @param requests the requests to set
	 */
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	
	
	
	
	
	
}
