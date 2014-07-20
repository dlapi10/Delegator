package freeuni.android.delegator.model;

import com.google.gson.annotations.SerializedName;


/**
 * Work is the work component on which team is currently working. maybe it can be a project.
 * @author Admin
 *
 */
public class Work {

	//Private variables
	@SerializedName ("workID")
	private int workID;
	
	@SerializedName ("name")
	private String name;
	
	
	
	/**
	 * @param workID
	 * @param name
	 */
	public Work(int workID, String name) {
		super();
		this.workID = workID;
		this.name = name;
	}
	
	
	/**
	 * @return the workID
	 */
	public int getWorkID() {
		return workID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param workID the workID to set
	 */
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
