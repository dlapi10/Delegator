package freeuni.android.delegator.model;

import android.graphics.Color;

public class TaskStatus {

	// Public constants
	public static final String statuses[] = new String[]{"Not Started","In Progress","Waiting for someone Else","Completed","Canceled"};
	
	
	//Private Variables
	private String status;
	private Color statusColor;
	
	/**
	 * Constructor with just status 
	 * @param status
	 */
	public TaskStatus(String status){
		this.status = status;
	}
	
	/**
	 * @param status
	 * @param statusColor
	 */
	public TaskStatus(String status, Color statusColor) {
		this.status = status;
		this.statusColor = statusColor;
	}
	
	/**
	 * @return the status
	 */
	public String getStatusName() {
		return status;
	}
	/**
	 * @return the statusColor
	 */
	public Color getStatusColor() {
		return statusColor;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param statusColor the statusColor to set
	 */
	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}
	
	/**
	 * Check if status is current or not (Canceled and Closed tasks are not current);
	 * @param status
	 * @return
	 */
	public static boolean isCurrentStatus(String status){
		if(status.equals(statuses[4]) || status.equals(statuses[3])){
			return false;
		}else{
			return true;
		}
	}
	
	
}
