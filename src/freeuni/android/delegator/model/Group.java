package freeuni.android.delegator.model;

import java.util.List;

import android.graphics.Bitmap;

public class Group {

	//Private variables
	private int groupID;
	private List<User> group;
	private Bitmap groupImage;
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
	
	
}
