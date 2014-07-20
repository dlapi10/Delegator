package freeuni.android.delegator.model;

import com.google.gson.annotations.SerializedName;

import android.graphics.Color;

public class TaskCategory {
	
	//Private variables
	@SerializedName ("categoryName")
	private String categoryName;
	
	@SerializedName ("categoryColor")
	private Color categoryColor;
	
	
	/**
	 * @param categoryName
	 * @param categoryColor
	 */
	public TaskCategory(String categoryName, Color categoryColor) {
		super();
		this.categoryName = categoryName;
		this.categoryColor = categoryColor;
	}
	
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @return the categoryColor
	 */
	public Color getCategoryColor() {
		return categoryColor;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @param categoryColor the categoryColor to set
	 */
	public void setCategoryColor(Color categoryColor) {
		this.categoryColor = categoryColor;
	}
	
	
	
}
