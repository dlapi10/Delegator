package freeuni.android.delegator.model;

import java.util.Calendar;

import com.google.gson.annotations.SerializedName;

public class Comment {

	//Private Variables
	@SerializedName ("commentID")
	private int commentID;
	
	@SerializedName ("CommentAuthor")
	private User CommentAuthor;
	
	@SerializedName ("postDate")
	private Calendar postDate; 
	
	@SerializedName ("comment")
	private String comment;
	
	
	/**
	 * @param commentID
	 * @param commentAuthor
	 * @param postDate
	 * @param comment
	 */
	public Comment(int commentID, User commentAuthor, Calendar postDate,
			String comment) {
		super();
		this.commentID = commentID;
		CommentAuthor = commentAuthor;
		this.postDate = postDate;
		this.comment = comment;
	}
	
	
	
	/**
	 * @param commentID
	 */
	public Comment(int commentID) {
		super();
		this.commentID = commentID;
	}

	/**
	 * @return the commentID
	 */
	public int getCommentID() {
		return commentID;
	}
	/**
	 * @return the commentAuthor
	 */
	public User getCommentAuthor() {
		return CommentAuthor;
	}
	/**
	 * @return the postDate
	 */
	public Calendar getPostDate() {
		return postDate;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param commentID the commentID to set
	 */
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	/**
	 * @param commentAuthor the commentAuthor to set
	 */
	public void setCommentAuthor(User commentAuthor) {
		CommentAuthor = commentAuthor;
	}
	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Calendar postDate) {
		this.postDate = postDate;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
