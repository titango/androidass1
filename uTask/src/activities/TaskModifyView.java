package activities;

import Assignment.utask.R;
import android.app.Activity;
import android.os.Bundle;

public class TaskModifyView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_task);
	}
}