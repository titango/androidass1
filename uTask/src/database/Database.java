package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

	private static SQLiteDatabase myDb;
	private static Context con;

	private static final String TASK_TABLE = "taskTable";
	private static final String TASK_ID = "taskID";
	private static final String GROUP_NAME = "groupName";
	private static final String PRIORITY = "priority";
	private static final String COLLABORATORS = "collaborators";
	private static final String DUEDATE = "dueDate";
	private static final String NOTE = "note";

	private static final String TASK_GROUP_TABLE = "taskGroupTable";
	private static final String GROUP_ID = "groupID";

	public Database(Context context) {
		con = context;
	}

	public static void createDatabase() {
		myDb = con.openOrCreateDatabase("utask.db", Context.MODE_PRIVATE, null);

		String sqlDataTask = "create table if not exists " + TASK_TABLE + " ("
				+ TASK_ID + " integer primary key autoincrement, " + GROUP_NAME
				+ " text, " + PRIORITY + " text, " + COLLABORATORS + " text , "
				+ DUEDATE + " text, " + NOTE + " text);";

		String sqlDataGroupTask = "create table if not exists "
				+ TASK_GROUP_TABLE + " (" + GROUP_ID
				+ " integer primary key autoincrement, " + GROUP_NAME
				+ " text not null);";

		myDb.execSQL(sqlDataTask);
		myDb.execSQL(sqlDataGroupTask);

	}

	public static void addToTaskTable(String groupName, String priority,
			String colla, String dueDate, String note) {
		ContentValues newValues = new ContentValues();
		newValues.put(GROUP_NAME, groupName);
		newValues.put(PRIORITY, priority);
		newValues.put(COLLABORATORS, colla);
		newValues.put(DUEDATE, dueDate);
		newValues.put(NOTE, note);
		myDb.insert("taskTable", null, newValues);
	}

	public static void addToGroupTaskTable(String groupName) {
		ContentValues newValues = new ContentValues();
		newValues.put(GROUP_NAME, groupName);
		myDb.insert("taskGroupTable", null, newValues);
	}

	public static void deleteFromTaskTable() {
		// myDb.delete("taskTable", "taskID = 2", null);
	}

	public static void deleteFormGroupTaskTable() {

	}

}
