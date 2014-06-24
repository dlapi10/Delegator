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
					"PRIMARY KEY ("+ UTG_USER_NAME +","+UTG_GROUP_ID +"))";
	
	
	/*
	 * Subordinates
	 */
	public static final String TABLE_SUBORDINATES = "SUBORDINATES";
	public static final String SUB_MANAGER = "MANAGER";
	public static final String SUB_SUBORDINATE = "SUBORDINATE";
	
	private static final String TABLE_SUB_CREATE = 
			"CREATE TABLE " + TABLE_SUBORDINATES + " (" +
					SUB_MANAGER + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					SUB_SUBORDINATE + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					"PRIMARY KEY ("+ SUB_MANAGER +","+SUB_SUBORDINATE +"))";
	
	
	/*
	 * Tasks
	 */
	public static final String TABLE_TASKS = "TASKS";
	public static final String TSK_ID = "ID";
	public static final String TSK_REPORTER = "REPORTER";
	public static final String TSK_ASSIGNEE = "ASSIGNEE";
	public static final String TSK_STATUS = "REPORTER";
	public static final String TSK_DESCRIPTION = "REPORTER";
	public static final String TSK_PRIORITY = "REPORTER";
	public static final String TSK_COMPLETION = "REPORTER";
	public static final String TSK_START_DATE = "REPORTER";
	public static final String TSK_DEADLINE = "REPORTER";
	public static final String TSK_TITLE = "REPORTER";
	
	private static final String TABLE_TASKS_CREATE = 
			"CREATE TABLE " + TABLE_SUBORDINATES + " (" +
					SUB_MANAGER + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					SUB_SUBORDINATE + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					"PRIMARY KEY ("+ SUB_MANAGER +","+SUB_SUBORDINATE +"))";
	
	/*
	 * TaskCategory
	 */
	
	public static final String TABLE_TASK_CATEGORIES = "TASK_CATEGORIES";
	public static final String TCAT_CATEGORY = "CATEGORY";
	public static final String TCAT_COLOR = "COLOR";
	
	private static final String TABLE_TCAT_CREATE = 
			"CREATE TABLE " + TABLE_TASK_CATEGORIES + " (" +
					TCAT_CATEGORY + " TEXT PRIMARY KEY, "+
					TCAT_COLOR + " TEXT)";
	
	/*
	 * TaskCategory to task
	 */
	public static final String TABLE_TASK_CAT_TO_TASK = "TASK_CAT_TO_TASK";
	public static final String TCTT_CATEGORY = "CATEGORY";
	public static final String TCTT_TASK_ID = "TASK_ID";
	
	private static final String TABLE_TCTT_CREATE = 
			"CREATE TABLE " + TABLE_TASK_CAT_TO_TASK + " (" +
					TCTT_CATEGORY + " TEXT REFERENCES " + TABLE_TASK_CATEGORIES +"("+TCAT_CATEGORY+"), "+
					TCTT_TASK_ID + " INTEGER REFERENCES " + TABLE_TASKS +"("+TSK_ID+"), "+
					"PRIMARY KEY ("+ TCTT_CATEGORY +","+TCTT_TASK_ID +"))";
	
	
	// Implementation

	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_USR_CREATE);
		db.execSQL(TABLE_GROUPS_CREATE);
		db.execSQL(TABLE_USER_TO_GROUPS_CREATE);
		db.execSQL(TABLE_SUB_CREATE);
		db.execSQL(TABLE_TCAT_CREATE);
		db.execSQL(TABLE_TASKS_CREATE);
		db.execSQL(TABLE_TCTT_CREATE);
		Log.i(LOG_TAG, "tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_GROUPS);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_USER_TO_GROUPS);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_SUBORDINATES);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_TASK_CATEGORIES);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_TASKS_CREATE);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_TASK_CAT_TO_TASK);
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
		c.close();
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
		c.close();
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
	
	/**
	 * Get groups by ID
	 * @param id
	 * @return
	 */
	public Group getGroup(int id){
		Group group = new Group(id);
		String query = "SELECT * FROM "+TABLE_GROUPS+" WHERE "+GRP_ID+" = "+id;
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		group.setGroupName(c.getString(c.getColumnIndex(GRP_NAME)));
		query = "SELECT * FROM "+TABLE_USER_TO_GROUPS+" WHERE "+UTG_GROUP_ID+"="+id;
		c = db.rawQuery(query, null);
		ArrayList<User> users = new ArrayList<User>();
		if(c.moveToFirst()){
			do{
				users.add(getUser(c.getString(c.getColumnIndex(UTG_USER_NAME))));
			}while(c.moveToNext());
		}
		c.close();
		return group;
	}
	
	/**
	 * Get groups for user
	 * @param user
	 * @return
	 */
	public List<Group> getUsersGroups(User user){
		ArrayList<Group> userGroups = new ArrayList<Group>();
		String query = "SELECT "+GRP_ID+" FROM "+TABLE_GROUPS+" WHERE "+GRP_OWNER +" = "+ user.getUserName();
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				userGroups.add(getGroup(c.getInt(c.getColumnIndex(GRP_ID))));
			}while(c.moveToNext());
		}
		c.close();
		return userGroups;
	}
	
	/**
	 * Sets subordinate to user
	 * @param manager 
	 * @param subordinate 
	 */
	public void setSubortinate(User manager, User subordinate){
		ContentValues values = new ContentValues();
		values.put(SUB_MANAGER, manager.getUserName());
		values.put(SUB_SUBORDINATE, subordinate.getUserName());
		db.insert(TABLE_SUBORDINATES, null, values);
	}
	
	/**
	 * Returns all users which are subordinates of given manager
	 * @param manager
	 * @return
	 */
	public List<User> getSubordinatesForManager(User manager){
		ArrayList<User> subordinates = new ArrayList<User>();
		String query = "SELECT "+SUB_SUBORDINATE+" FROM "+TABLE_SUBORDINATES+" WHERE "+SUB_MANAGER +" = "+ manager.getUserName();
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				subordinates.add(getUser((c.getString(c.getColumnIndex(SUB_SUBORDINATE)))));
			}while(c.moveToNext());
		}
		c.close();
		return subordinates;
	}
	

}
