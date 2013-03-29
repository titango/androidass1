package activities;

import Assignment.utask.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import database.Database;

public class MainScreen extends Activity {
	
	//Properties
	Database database = new Database(this);
	
	Spinner groupSpin, sortSpin;
	ArrayAdapter<String> spinAdapt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		Database.createDatabase();
		
		groupSpin = (Spinner) findViewById(R.id.groupSpin);
		sortSpin = (Spinner) findViewById(R.id.sortSpin);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

}
