package freeuni.android.delegator.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Processing;
import freeuni.android.delegator.model.User;

public class DBManager extends SQLiteOpenHelper{

	//Class implementation based on Lynda.com tutorials

	//Constants
	public static final String DATABASE_NAME = "Delegator.db";
	private static final int DATABASE_VERSION = 1;
	private static final String LOG_TAG = "DBHelper";

	//Variables
	private SQLiteDatabase db;

	/*
	 * Users
	 */
	public static final String TABLE_USERS = "USERS";
	public static final String USR_USER_NAME = "USER_NAME";
	public static final String USR_IMAGE = "IMAGE";
	
	private static final String TABLE_USR_CREATE = 
			"CREATE TABLE " + TABLE_USERS + " (" +
					USR_USER_NAME + " TEXT PRIMARY KEY, "+
					USR_IMAGE + " BLOB) ";
	
	
	/*
	 * Groups
	 */
	public static final String TABLE_GROUPS = "GROUPS";
	public static final String GRP_ID = "GROUP_ID";
	public static final String GRP_NAME = "GROUP_NAME";
	public static final String GRP_OWNER = "GROUP_OWNER";
	
	private static final String TABLE_GROUPS_CREATE = 
			"CREATE TABLE " + TABLE_GROUPS + " (" +
					GRP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
					GRP_NAME + " TEXT,"+
					GRP_OWNER + "TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+") )";
	
	public static final String TABLE_USER_TO_GROUPS = "USER_TO_GROUPS";
	public static final String UTG_USER_NAME = "USER_NAME";
	public static final String UTG_GROUP_ID = "GROUP_ID";
	
	private static final String TABLE_USER_TO_GROUPS_CREATE = 
			"CREATE TABLE " + TABLE_USER_TO_GROUPS + " (" +
					UTG_USER_NAME + " TEXT, "+
					UTG_GROUP_ID + " INTEGER,"+ 
					"PRIMARY KEY ("+ UTG_USER_NAME +","+UTG_GROUP_ID +")";
	
	
	/*
	 * Subordinates
	 */
	
	/*
	 * Owned Groups
	 */
	
	/*
	 * Requests
	 */
	
	/*
	 * Comments
	 */
	
	/*
	 * Tasks
	 */
	
	/*
	 * TaskCategory
	 */

	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_USR_CREATE);
		db.execSQL(TABLE_GROUPS_CREATE);
		db.execSQL(TABLE_USER_TO_GROUPS_CREATE);
		Log.i(LOG_TAG, "tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_GROUPS);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_USER_TO_GROUPS);
		onCreate(db);
	}
	
	/**
	 * Add user
	 * @param user
	 */
	public void addUser(User user){
		ContentValues values = new ContentValues();
		values.put(USR_USER_NAME, user.getUserName());
		values.put(USR_IMAGE, Processing.bitmapToByteArray(user.getAvatar()));
		db.insert(TABLE_USERS, null, values);
		Log.i(LOG_TAG, "User added");
	}

	/**
	 * Get User
	 * @param userID
	 * @return
	 */
	public User getUser(String userName){
		String query="SELECT * FROM "+TABLE_USERS+"WHERE "+USR_USER_NAME+"="+userName+"";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		byte[] image = c.getBlob(c.getColumnIndex(USR_IMAGE));
		User user = new User(userName);
		user.setAvatar(Processing.byteArrayToBitmap(image, App.getAvatarDimension(), App.getAvatarDimension()));
		return user;
	}
	

	/**
	 * Add group for user (manager)
	 * @param group
	 * @param user group owner
	 * @return returns group ID.
	 */
	public int addGroup(Group group, User user){
		ContentValues values = new ContentValues();
		values.put(GRP_NAME, group.getGroupName());
		values.put(GRP_OWNER, user.getUserName());
		db.insert(TABLE_GROUPS, null, values);
		String query="SELECT MAX("+GRP_ID+") FROM "+TABLE_GROUPS+"";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		int grpID = c.getInt(c.getColumnIndex(GRP_ID));
		return grpID;
	}
	
	/**
	 * Adding user to group
	 * @param group
	 * @param user
	 */
	public void addUserToGroup(Group group, User user){
		ContentValues values = new ContentValues();
		values.put(UTG_GROUP_ID, group.getGroupID());
		values.put(UTG_USER_NAME, user.getUserName());
		db.insert(TABLE_USER_TO_GROUPS, null, values);
	}
	
	public Group getGroup(int id){
		Group group = null;
		return group;
	}
	
	
	public List<Group> getUsersGroups(User user){
		ArrayList<Group> userGroups = null;
		return userGroups;
	}
}
