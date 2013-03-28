package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

	private static SQLiteDatabase myDb;
	private static Context con;

	private static final String TASK_TABLE = "taskTable";

	public Database(Context context) {
		con = context;
	}

	public static void createDatabase() {
		myDb = con.openOrCreateDatabase("utask.db", Context.MODE_PRIVATE, null);

		String sqlDataTask = "create table if not exists "
				+ TASK_TABLE
				+ " (_taskID integer primary key autoincrement, "
				+ "groupID integer, priority varchar(6), groups text not null, collaborators text not null, dueDate text not null, "
				+ "note text not null)";
		myDb.execSQL(sqlDataTask);

	}
}
