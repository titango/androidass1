package activities;

import java.util.ArrayList;

import Assignment.utask.R;
import Model.Data;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import custom.MyEditText;

public class TaskModifyView extends Activity {

	// Properties
	private static final int CONTACT_PICKING = 1;

	MyEditText editTitle, editNote;
	Spinner editPriority, editGroup;
	DatePicker editDate;
	Button editColla;
	TextView showColla;
	ImageButton saveBut, deleteBut;

	ArrayList<String> priorityOption, groupOption;
	ArrayList<String> contactList = new ArrayList<String>();
	ArrayAdapter<String> priorityAdapt, groupAdapt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_task);

		// Settings for Properties
		settingForProperties();

		// Set actions
		allAction();

		Bundle received = this.getIntent().getExtras();

		// Check if the intent is type 'edit' .If it's type 'add' then do
		// nothing
		if (received.getString("type").equals("edit")) {

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// Get the contact email and add to arraylist contactList
		switch (requestCode) {
		case (CONTACT_PICKING):
			if (resultCode == Activity.RESULT_OK) {
				Uri intentData = data.getData();
				Cursor tempCur = managedQuery(intentData, null, null, null, null);
				if (tempCur.moveToFirst()) {
					String tempData = tempCur.getString(tempCur .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					contactList.add(tempData);
					
					String showList = "";
					for (int i = 0; i < contactList.size(); i++) {
						if (i == 0) {
							showList = showList + contactList.get(i);
						} else {
							showList = showList + "," + contactList.get(i);
						}
					}
					
					showColla.setText(showList);
				}
			}
			break;
		}

	}

	private void settingForProperties() {
		// Inflate to XML file
		editTitle = (MyEditText) findViewById(R.id.title_right_bottom_panel_add_edit);
		editPriority = (Spinner) findViewById(R.id.priority_right_bottom_panel_add_edit);
		editDate = (DatePicker) findViewById(R.id.date_right_bottom_panel_add_edit);
		editGroup = (Spinner) findViewById(R.id.group_right_bottom_panel_add_edit);
		editColla = (Button) findViewById(R.id.contactbutton_right_bottom_panel_add_edit);
		showColla = (TextView) findViewById(R.id.chooseContact_right_bottom_panel_add_edit);
		editNote = (MyEditText) findViewById(R.id.note_right_bottom_panel_add_edit);
		saveBut = (ImageButton) findViewById(R.id.saveTaskButton_add_edit);
		deleteBut = (ImageButton) findViewById(R.id.deleteTaskButton_add_edit);

		// Settings for properties
		/* Priority */
		priorityOption = new ArrayList<String>();
		priorityOption.add("High");
		priorityOption.add("Medium");
		priorityOption.add("Low");
		priorityAdapt = new ArrayAdapter<String>(this,
				R.layout.myspinner_text_view, priorityOption);
		priorityAdapt
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		editPriority.setAdapter(priorityAdapt);

		/* Group */
		groupOption = new ArrayList<String>();
		for (int i = 0; i < Data.getGroupList().size(); i++) {
			groupOption.add(Data.getGroupList().get(i).getGroupName());
		}
		groupAdapt = new ArrayAdapter<String>(this,
				R.layout.myspinner_text_view, groupOption);
		groupAdapt
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		editGroup.setAdapter(groupAdapt);
	}
	
	//All actions when do something
	private void allAction(){
		editColla.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);

				startActivityForResult(intent, CONTACT_PICKING);
			}
		});
		
		saveBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}
}
