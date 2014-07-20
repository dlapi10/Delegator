package freeuni.android.delegator.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import android.graphics.Bitmap;

public class Group {

	//Private variables
	@SerializedName ("groupID")
	private int groupID;
	
	@SerializedName ("group")
	private List<User> group;
	
	@SerializedName ("groupImage")
	private Bitmap groupImage;
	
	@SerializedName ("groupName")
	private String groupName;
	
	
	
	/**
	 * @param groupID
	 * @param group
	 * @param groupImage
	 */
	public Group(int groupID, List<User> group, Bitmap groupImage) {
		super();
		this.groupID = groupID;
		this.group = group;
		this.groupImage = groupImage;
	}
	
	public Group(){
		
	}
	
	/**
	 * @param groupID
	 */
	public Group(int groupID) {
		super();
		this.groupID = groupID;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return the groupID
	 */
	public int getGroupID() {
		return groupID;
	}
	
	/**
	 * @return the group
	 */
	public List<User> getGroup() {
		return group;
	}
	
	/**
	 * @return the groupImage
	 */
	public Bitmap getGroupImage() {
		return groupImage;
	}
	
	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	/**
	 * @param group the group to set
	 */
	public void setGroup(List<User> group) {
		this.group = group;
	}
	
	/**
	 * @param groupImage the groupImage to set
	 */
	public void setGroupImage(Bitmap groupImage) {
		this.groupImage = groupImage;
	}
	
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
