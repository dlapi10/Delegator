package freeuni.android.delegator.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import freeuni.android.delegator.app.App;
import freeuni.android.delegator.helpers.Processing;
import freeuni.android.delegator.model.Group;
import freeuni.android.delegator.model.Task;
import freeuni.android.delegator.model.TaskStatus;
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
	public static final String USR_PASS = "PASS";
	public static final String USR_PHONE = "PHONE";
	public static final String USR_IMAGE = "IMAGE";

	private static final String TABLE_USR_CREATE = 
			"CREATE TABLE " + TABLE_USERS + " (" +
					USR_USER_NAME + " TEXT PRIMARY KEY, "+
					USR_PASS +" TEXT,"+
					USR_PHONE +" TEXT,"+
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
					GRP_OWNER + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+") )";

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
	public static final String TSK_STATUS = "STATUS";
	public static final String TSK_DESCRIPTION = "DESCRIPTION";
	public static final String TSK_PRIORITY = "PRIORITY";
	public static final String TSK_COMPLETION = "COMPLETEION";
	public static final String TSK_START_DATE = "START_DATE";
	public static final String TSK_DEADLINE = "DEADLINE";
	public static final String TSK_TITLE = "TITLE";

	private static final String TABLE_TASKS_CREATE = 
			"CREATE TABLE " + TABLE_TASKS + " (" +
					TSK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
					TSK_REPORTER + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					TSK_ASSIGNEE + " TEXT REFERENCES " + TABLE_USERS +"("+USR_USER_NAME+"), "+
					TSK_STATUS + " TEXT, "+
					TSK_DESCRIPTION + " TEXT, "+
					TSK_PRIORITY + " INTEGER, "+
					TSK_COMPLETION + " INTEGER, "+
					TSK_START_DATE + " TEXT, "+
					TSK_DEADLINE + " TEXT, "+
					TSK_TITLE + " TEXT )";

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
		Log.i(LOG_TAG, TABLE_USR_CREATE);
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
		values.put(USR_PHONE, user.getPhoneNumber());
		values.put(USR_IMAGE, Processing.bitmapToByteArray(user.getAvatar()));
		values.put(USR_PASS, user.getPassword());
		db.insert(TABLE_USERS, null, values);
		Log.i(LOG_TAG, "User added");
	}
	
	/**
	 * Update user
	 * @param user
	 */
	
	public String updateUser(User user){
		String result="";
		ContentValues values = new ContentValues();
		values.put(USR_PASS, user.getPassword());
		values.put(USR_PHONE, user.getPhoneNumber());
		values.put(USR_IMAGE, Processing.bitmapToByteArray(user.getAvatar()));
		
		long id;

		db.beginTransaction();
		id = db.update(TABLE_USERS, values, USR_USER_NAME + " = '"+ user.getUserName()+"'", null);
		db.setTransactionSuccessful();
		db.endTransaction();
		if (id != -1) {
			result = "Profile has been successfully updated.";
			Log.i(LOG_TAG, result);
		} else{
			result = "Error while updating profile";
			Log.e(LOG_TAG, result);
		}
		return result;
	}

	/**
	 * Get User
	 * @param userID
	 * @return
	 */
	public User getUser(String userName){
		String query="SELECT * FROM "+TABLE_USERS+" WHERE "+USR_USER_NAME+" = '"+userName+"'";
		Log.i(LOG_TAG, query);
		Cursor c = db.rawQuery(query, null);
		User user = null;
		if(c.moveToFirst()){
			byte[] image = c.getBlob(c.getColumnIndex(USR_IMAGE));
			user = new User(userName);
			user.setAvatar(Processing.byteArrayToBitmap(image, App.getAvatarDimension(), App.getAvatarDimension()));
			user.setPassword(c.getString(c.getColumnIndex(USR_PASS)));
			user.setPhoneNumber(c.getString(c.getColumnIndex(USR_PHONE)));
			c.close();
		}
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
		String query="SELECT MAX("+GRP_ID+") AS Maximum FROM "+TABLE_GROUPS+"";
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		int grpID = c.getInt(c.getColumnIndex("Maximum"));
		c.close();
		return grpID;
	}

	/**
	 * Updates task
	 * @param task
	 * @return task id
	 */
	public void updateGroupName(Group group){
		String where = GRP_ID+"=" + group.getGroupID();
		ContentValues values = new ContentValues();
		values.put(GRP_NAME, group.getGroupName());
		db.update(TABLE_GROUPS, values, where, null);
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
		group.setGroup(users);
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
		String query = "SELECT "+GRP_ID+" FROM "+TABLE_GROUPS+" WHERE "+GRP_OWNER +" = '"+ user.getUserName()+"'";
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
		String query = "SELECT "+SUB_SUBORDINATE+" FROM "+TABLE_SUBORDINATES+" WHERE "+SUB_MANAGER +" = '"+ manager.getUserName()+"'";
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				subordinates.add(getUser((c.getString(c.getColumnIndex(SUB_SUBORDINATE)))));
			}while(c.moveToNext());
		}
		c.close();
		return subordinates;
	}

	/**
	 * Preparing content values for task
	 * @param task
	 * @return
	 */
	private ContentValues prepareValues(Task task){
		ContentValues values = new ContentValues();
		if(task.getReporter()!=null && task.getReporter().getUserName()!=null) 
			values.put(TSK_REPORTER, task.getReporter().getUserName());
		if(task.getAssignee()!=null && task.getAssignee().getUserName()!=null) 
			values.put(TSK_ASSIGNEE, task.getAssignee().getUserName());
		if(task.getStatus()!=null && task.getStatus().getStatusName()!=null) 
			values.put(TSK_STATUS, task.getStatus().getStatusName());
		if(task.getDescription()!=null) 
			values.put(TSK_DESCRIPTION, task.getDescription());
		values.put(TSK_PRIORITY, task.getPriority());
		values.put(TSK_COMPLETION, task.getCompletionPercent());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",java.util.Locale.getDefault());
		String date;
		if(task.getStartDate()!=null){
			date = formatter.format(task.getStartDate().getTime());
			values.put(TSK_START_DATE, date);
		}
		if(task.getDeadLine()!=null) {
			date = formatter.format(task.getDeadLine().getTime());
			values.put(TSK_DEADLINE, date);
		}
		if(task.getTitle()!=null && task.getTitle().toString()!=null) values.put(TSK_TITLE, task.getTitle().toString());
		return values;
	}
	
	/**
	 * Adds task to database
	 * @param task
	 * @return task id
	 */
	public int addTask(Task task){
		db.insert(TABLE_TASKS, null, prepareValues(task));
		Log.i(LOG_TAG, "new task inserted");
		String query="SELECT MAX("+TSK_ID+") AS Maximum FROM "+TABLE_TASKS+"";
		Cursor c = db.rawQuery(query, null);
		int tskID=-1;
		if(c.moveToFirst())
			tskID = c.getInt(c.getColumnIndex("Maximum"));
		c.close();
		return tskID;
	}

	/**
	 * Updates task
	 * @param task
	 * @return task id
	 */
	public void updateTask(Task task){
		String where = TSK_ID+"=" + task.getTaskID();
		db.update(TABLE_TASKS, prepareValues(task), where, null);
	}

	/**
	 * Deleting task
	 * @param task
	 */
	public void deleteTask(Task task){
		db.execSQL("DELETE FROM "+TABLE_TASKS+" WHERE "+TSK_ID+"="+task.getTaskID());
		Log.i(LOG_TAG, "Task "+task.getTaskID()+" deleted");
	}

	/**
	 * Returns task with given id
	 * @param id
	 * @return
	 */
	public Task getTask(int id){
		Task task = new Task(id);
		String query = "SELECT * FROM "+TABLE_TASKS+" WHERE "+TSK_ID +" = "+ id;
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			task.setAssignee(getUser(c.getString(c.getColumnIndex(TSK_ASSIGNEE))));
			task.setReporter(getUser(c.getString(c.getColumnIndex(TSK_REPORTER))));
			task.setStatus(new TaskStatus(c.getString(c.getColumnIndex(TSK_STATUS))));
			task.setDescription(c.getString(c.getColumnIndex(TSK_DESCRIPTION)));
			task.setPriority(c.getInt(c.getColumnIndex(TSK_PRIORITY)));
			task.setCompletionPercent(c.getInt(c.getColumnIndex(TSK_COMPLETION)));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",java.util.Locale.getDefault());
			try {
				if(c.getString(c.getColumnIndex(TSK_START_DATE))!=null)
					task.setStartDate(DateToCalendar((Date)formatter.parse(c.getString(c.getColumnIndex(TSK_START_DATE)))));
				if(c.getString(c.getColumnIndex(TSK_DEADLINE))!=null)
					task.setDeadLine(DateToCalendar((Date)formatter.parse(c.getString(c.getColumnIndex(TSK_DEADLINE)))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			task.setTitle(c.getString(c.getColumnIndex(TSK_TITLE)));
		}else{
			return null;
		}
		c.close();
		return task;
	}

	/**
	 * Date format to Calendar
	 * @param date
	 * @return
	 */
	public static Calendar DateToCalendar(Date date){ 
		if(date==null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}


	/** 
	 * Returns all tasks for given reporter
	 * @param reporter
	 * @return
	 */
	public List<Task> getTasksForReporter(User reporter){
		ArrayList<Task> tasks = new ArrayList<Task>();
		String query = "SELECT * FROM "+TABLE_TASKS+" WHERE "+TSK_REPORTER +" = '"+ reporter.getUserName()+"'";
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				tasks.add(getTask(c.getInt(c.getColumnIndex(TSK_ID))));
			}while(c.moveToNext());
		}
		c.close();
		return tasks;
	}

	/**
	 * Returns all tasks for given assignee
	 * @param assignee
	 * @return
	 */
	public List<Task> getTasksForAssignee(User assignee){
		ArrayList<Task> tasks = new ArrayList<Task>();
		String query = "SELECT * FROM "+TABLE_TASKS+" WHERE "+TSK_ASSIGNEE +" = '"+ assignee.getUserName()+"'";
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				tasks.add(getTask(c.getInt(c.getColumnIndex(TSK_ID))));
			}while(c.moveToNext());
		}
		c.close();
		return tasks;
	}


	/**
	 * Gets number of current tasks for user
	 * @param user
	 * @return
	 */
	public int getCurrentTaskCountForUser(User user){
		int answer = 0;
		String query = "SELECT * FROM "+TABLE_TASKS+" WHERE "+TSK_ASSIGNEE +" = '"+ user.getUserName()+"'";
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				if(TaskStatus.isCurrentStatus(c.getString(c.getColumnIndex(TSK_STATUS)))){
					answer++;
				}
			}while(c.moveToNext());
		}
		c.close();
		return answer;
	}
}
