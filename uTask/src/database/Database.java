package database;

import java.util.ArrayList;

import Model.Data;
import Model.GroupTask;
import Model.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database {

	// Properties
	private static SQLiteDatabase myDb;
	private static Context con;

	private static final String TASK_TABLE = "taskTable";
	private static final String TASK_ID = "taskID";
	private static final String TITLE = "title";
	private static final String GROUP_NAME = "groupName";
	private static final String PRIORITY = "priority";
	private static final String COLLABORATORS = "collaborators";
	private static final String DUEDATE = "dueDate";
	private static final String STATUS = "status";
	private static final String NOTE = "note";

	private static final String TASK_GROUP_TABLE = "taskGroupTable";
	private static final String GROUP_ID = "groupID";

	// Constructor passing context to use functions
	public Database(Context context) {
		con = context;
	}

	// Create or Open SQlite Database
	public static void createDatabase() {
		myDb = con.openOrCreateDatabase("utask.db", Context.MODE_PRIVATE, null);

		String sqlDataTask = "create table if not exists " + TASK_TABLE + " ("
				+ TASK_ID + " text primary key, " + TITLE + " text, "
				+ GROUP_NAME + " text, " + PRIORITY + " text, " + COLLABORATORS
				+ " text , " + DUEDATE + " text, " + STATUS + " text, " + NOTE
				+ " text);";

		String sqlDataGroupTask = "create table if not exists "
				+ TASK_GROUP_TABLE + " (" + GROUP_ID + " text primary key, "
				+ GROUP_NAME + " text not null);";

		myDb.execSQL(sqlDataTask);
		myDb.execSQL(sqlDataGroupTask);
	}

	// Add values to Table "taskTable"
	public static void addToTaskTable(String title, String groupName,
			String priority, String dueDate, String status, String note,
			ArrayList<String> colla) {

		// Add to SQLiteDatabase
		String tempColla = "";
		String newID = Task.autoTaskGenerateId();

		ContentValues newValues = new ContentValues();
		newValues.put(TASK_ID, newID);
		newValues.put(TITLE, title);
		newValues.put(GROUP_NAME, groupName);
		newValues.put(PRIORITY, priority);
		newValues.put(DUEDATE, dueDate);
		newValues.put(STATUS, status);
		newValues.put(NOTE, note);

		for (int i = 0; i < colla.size(); i++) {
			if (i == 0) {
				tempColla = tempColla + colla.get(i);
			} else {
				tempColla = tempColla + "," + colla.get(i);
			}
		}

		newValues.put(COLLABORATORS, tempColla);
		myDb.insert(TASK_TABLE, null, newValues);

		// Add to Data class (model)
		Task newTask = new Task();
		newTask.setId(newID);
		newTask.setGroupName(groupName);
		newTask.setTitle(title);
		newTask.setPriority(priority);
		newTask.setDueDate(dueDate);
		newTask.setStatus(status);
		newTask.setNote(note);
		newTask.setCollaborators(colla);
		Data.getTaskList().add(newTask);
	}

	// Add values to table "groupTaskTable" and Model
	public static void addToGroupTaskTable(String groupName) {
		ContentValues newValues = new ContentValues();

		// Add to SQLiteDatabase
		String newID = GroupTask.autoGroupGenerateId();
		newValues.put(GROUP_ID, newID);
		newValues.put(GROUP_NAME, groupName);
		myDb.insert(TASK_GROUP_TABLE, null, newValues);

		// Add to Data class (model)
		GroupTask newGroup = new GroupTask();
		newGroup.setId(newID);
		newGroup.setGroupName(groupName);
		Data.getGroupList().add(newGroup);
	}

	// Update values to table taskTable
	public static void updateToTaskTable(String Id, String title, String groupName,
			String priority, String dueDate, String status, String note,
			ArrayList<String> colla) {
		
		String tempColla = "";

		ContentValues newValues = new ContentValues();
		newValues.put(TITLE, title);
		newValues.put(GROUP_NAME, groupName);
		newValues.put(PRIORITY, priority);
		newValues.put(DUEDATE, dueDate);
		newValues.put(STATUS, status);
		newValues.put(NOTE, note);

		for (int i = 0; i < colla.size(); i++) {
			if (i == 0) {
				tempColla = tempColla + colla.get(i);
			} else {
				tempColla = tempColla + "," + colla.get(i);
			}
		}

		newValues.put(COLLABORATORS, tempColla);
		myDb.update(TASK_TABLE, newValues,TASK_ID + " = '" + Id + "'",null);

		// Add to Data class (model)
		for(int i = 0; i < Data.getTaskList().size(); i++){
			if(Data.getTaskList().get(i).getId().equals(Id)){
				Data.getTaskList().get(i);
				Log.i("Inside id", Data.getTaskList().get(i).getId());
				Data.getTaskList().get(i).setGroupName(groupName);
				Data.getTaskList().get(i).setTitle(title);
				Data.getTaskList().get(i).setPriority(priority);
				Data.getTaskList().get(i).setDueDate(dueDate);
				Data.getTaskList().get(i).setStatus(status);
				Data.getTaskList().get(i).setNote(note);
				Data.getTaskList().get(i).setCollaborators(colla);
				break;
			}
		}
		
		
	}

	// Delete values from taskTable
	public static void deleteFromTaskTable(String id) {
		// myDb.delete(TASK_TABLE, TASK_ID + " = " + id, null);
		myDb.execSQL("Delete from " + TASK_TABLE + " where " + TASK_ID + " = '"
				+ id + "'");
	}

	// Delete values from groupTaskTable
	public static void deleteFromGroupTaskTable(String id) {
		// myDb.delete(TASK_GROUP_TABLE, GROUP_ID + " = " + id , null);
		myDb.execSQL("Delete from " + TASK_GROUP_TABLE + " where " + GROUP_ID
				+ " = '" + id + "'");
	}

	// Load data into Data model class
	public static void loadToData() {
		// Create cursor for querying data from Sqlite database
		Cursor curTask = myDb.query(TASK_TABLE, new String[] { TASK_ID, TITLE,
				GROUP_NAME, PRIORITY, COLLABORATORS, DUEDATE, STATUS, NOTE },
				null, null, null, null, TASK_ID);

		Cursor curGroup = myDb.query(TASK_GROUP_TABLE, new String[] { GROUP_ID,
				GROUP_NAME }, null, null, null, null, GROUP_ID);

		// Temp variables use to get values from Collaborators
		String tempColla;
		String[] splitColla;
		ArrayList<String> collaList = new ArrayList<String>();

		// Loop to add Task into Data
		if (curTask.moveToFirst()) {
			do {
				Task tempTask = new Task();
				tempTask.setId(curTask.getString(curTask
						.getColumnIndexOrThrow(TASK_ID)));
				tempTask.setTitle(curTask.getString(curTask
						.getColumnIndexOrThrow(TITLE)));
				tempTask.setGroupName(curTask.getString(curTask
						.getColumnIndexOrThrow(GROUP_NAME)));
				tempTask.setPriority(curTask.getString(curTask
						.getColumnIndexOrThrow(PRIORITY)));

				tempColla = curTask.getString(curTask
						.getColumnIndexOrThrow(COLLABORATORS));
				splitColla = tempColla.split(",");
				for (int i = 0; i < splitColla.length; i++) {
					collaList.add(splitColla[i]);
				}
				tempTask.setCollaborators(collaList);

				tempTask.setDueDate(curTask.getString(curTask
						.getColumnIndexOrThrow(DUEDATE)));
				tempTask.setStatus(curTask.getString(curTask
						.getColumnIndexOrThrow(STATUS)));
				tempTask.setNote(curTask.getString(curTask
						.getColumnIndexOrThrow(NOTE)));

				// Add to Data taskList
				Data.getTaskList().add(tempTask);
			} while (curTask.moveToNext());
		}

		// Loop to add Group into Data
		if (curGroup.moveToFirst()) {
			do {
				GroupTask tempGroup = new GroupTask();
				tempGroup.setId(curGroup.getString(curGroup
						.getColumnIndexOrThrow(GROUP_ID)));
				tempGroup.setGroupName(curGroup.getString(curGroup
						.getColumnIndexOrThrow(GROUP_NAME)));

				// Add to Data groupList
				Data.getGroupList().add(tempGroup);

			} while (curGroup.moveToNext());
		}

		if (curTask != null && curTask.isClosed()) {
			curTask.close();
		}

		if (curGroup != null && curGroup.isClosed()) {
			curGroup.close();
		}
	}

}
