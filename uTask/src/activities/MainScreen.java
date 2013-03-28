package activities;

import Assignment.utask.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import database.Database;

public class MainScreen extends Activity {
	Database database = new Database(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		Database.createDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

}