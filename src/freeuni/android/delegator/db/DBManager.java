package freeuni.android.delegator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

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
	public static final String TABLE_USERS = "CONTACTS";
	public static final String USR_USER_ID = "ID";
	public static final String USR_USER_NAME = "USER_NAME";
	public static final String USR_IMAGE = "IMAGE";
	
	private static final String TABLE_USR_CREATE = 
			"CREATE TABLE " + TABLE_USERS + " (" +
					USR_USER_ID + " INTEGER PRIMARY KEY, "+
					USR_USER_NAME + " TEXT, "+
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

}
