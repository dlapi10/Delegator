package freeuni.android.delegator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import freeuni.android.delegator.app.App;
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
		Log.i(LOG_TAG, "tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_USERS);
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
	
	
}
