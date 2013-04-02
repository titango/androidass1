package activities;

import java.util.ArrayList;

import Assignment.utask.R;
import Model.Data;
import Model.Task;
import database.Database;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import custom.MyEditText;

public class TaskModifyView extends Activity {

	// Properties
	private static final int CONTACT_PICKING = 1;

	private String itemId;

	MyEditText editTitle, editNote;
	Spinner editPriority, editGroup;
	DatePicker editDate;
	Button editColla;
	TextView showColla;
	ImageButton saveBut, deleteBut;
	CheckBox checkStatus;
	Bundle received;

	ArrayList<String> priorityOption, groupOption;
	ArrayList<String> contactList = new ArrayList<String>();
	ArrayAdapter<String> priorityAdapt, groupAdapt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_task);

		received = this.getIntent().getExtras();

		// Settings for Properties
		settingForProperties();

		// Set actions
		allAction();

		// Check if the intent is type 'edit' .If it's type 'add' then do
		// nothing. If type edit then do something
		if (received.getString("type").equals("edit")) {
			itemId = received.getString("id");
			String iTitle = received.getString("title");
			String iPriority = received.getString("priority");
			String iDate = received.getString("date");
			String iGroup = received.getString("group");
			String iColla = received.getString("colla");
			String iNote = received.getString("note");
			String iStatus = received.getString("status");

			this.editTitle.setText(iTitle);
			if (iPriority.equals("High")) {
				this.editPriority.setSelection(0,true);
			} else if (iPriority.equals("Medium")) {
				this.editPriority.setSelection(1,true);
			} else if (iPriority.equals("Low")) {
				this.editPriority.setSelection(2,true);
			}

			String[] splitDate = iDate.split("/");
			int tempMonth = Integer.parseInt(splitDate[1]) - 1;
			this.editDate.updateDate(Integer.parseInt(splitDate[2]), tempMonth,
					Integer.parseInt(splitDate[0]));

			for (int i = 0; i < groupOption.size(); i++) {
				if (iGroup.equals(groupOption.get(i))) {
					this.editGroup.setSelection(i);
					break;
				}
			}

			this.showColla.setText(iColla);
			this.editNote.setText(iNote);

			if (iStatus.equals("Done")) {
				checkStatus.setChecked(true);
			} else {
				checkStatus.setChecked(false);
			}

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
				Cursor tempCur = managedQuery(intentData, null, null, null,
						null);
				if (tempCur.moveToFirst()) {
					String tempData = tempCur
							.getString(tempCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
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
		contactList = new ArrayList<String>();
		
		editTitle = (MyEditText) findViewById(R.id.title_right_bottom_panel_add_edit);
		editPriority = (Spinner) findViewById(R.id.priority_right_bottom_panel_add_edit);
		editDate = (DatePicker) findViewById(R.id.date_right_bottom_panel_add_edit);
		editGroup = (Spinner) findViewById(R.id.group_right_bottom_panel_add_edit);
		editColla = (Button) findViewById(R.id.contactbutton_right_bottom_panel_add_edit);
		showColla = (TextView) findViewById(R.id.chooseContact_right_bottom_panel_add_edit);
		editNote = (MyEditText) findViewById(R.id.note_right_bottom_panel_add_edit);
		saveBut = (ImageButton) findViewById(R.id.saveTaskButton_add_edit);
		deleteBut = (ImageButton) findViewById(R.id.deleteTaskButton_add_edit);
		checkStatus = (CheckBox) findViewById(R.id.checkStatus_right_bottom_panel_add_edit);

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

	// All actions when do something
	private void allAction() {
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
				ArrayList<String> newCollaArray = new ArrayList<String>();

				String newTitle = editTitle.getText().toString();
				String newPriority = (String) editPriority.getSelectedItem();
				int correctMonth = editDate.getMonth() + 1;
				String newDate = editDate.getDayOfMonth() + "/" + correctMonth
						+ "/" + editDate.getYear();
				String newGroup = (String) editGroup.getSelectedItem();
				String newColla = showColla.getText().toString();
				String newNote = editNote.getText().toString();
				String newStatus;
				if (checkStatus.isChecked()) {
					newStatus = "Done";
				} else {
					newStatus = "Undone";
				}

				String[] splitColla = newColla.split(",");
				for (int i = 0; i < splitColla.length; i++) {
					newCollaArray.add(splitColla[i]);
				}

				//
				// Log.i("show Title", newTitle);
				// Log.i("show Priority", newPriority);
				// Log.i("show Date", newDate);
				// Log.i("show Group", newGroup);
				// Log.i("show Colla", newColla);
				// Log.i("show Status", newStatus);

				if (received.getString("type").equals("add")) {
					// Create new Task and add to Data

					Database.addToTaskTable(newTitle, newGroup, newPriority,
							newDate, newStatus, newNote, newCollaArray);

					// Back to MainScreen
					finish();
				} else if (received.getString("type").equals("edit")) {

					Database.updateToTaskTable(itemId, newTitle, newGroup,
							newPriority, newDate, newStatus, newNote,
							newCollaArray);

					// Back to MainScreen
					finish();
				}

				if (editGroup != null && editGroup.getSelectedItem() != null) {
					Intent newData = new Intent();
					newData.putExtra("selectedGroup", editGroup
							.getSelectedItem().toString());
					if (getParent() == null) {
						setResult(Activity.RESULT_OK, newData);
					} else {
						getParent().setResult(Activity.RESULT_OK, newData);
					}
				}

			}
		});

		deleteBut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (received.getString("type").equals("add")) {
					finish();
				} else if (received.getString("type").equals("edit")) {
					ArrayList<String> newCollaArray = new ArrayList<String>();

					String newTitle = editTitle.getText().toString();
					String newPriority = (String) editPriority
							.getSelectedItem();
					int correctMonth = editDate.getMonth() + 1;
					String newDate = editDate.getDayOfMonth() + "/"
							+ correctMonth + "/" + editDate.getYear();
					String newGroup = (String) editGroup.getSelectedItem();
					String newColla = showColla.getText().toString();
					String newNote = editNote.getText().toString();
					String newStatus;
					if (checkStatus.isChecked()) {
						newStatus = "Done";
					} else {
						newStatus = "Undone";
					}

					String[] splitColla = newColla.split(",");
					for (int i = 0; i < splitColla.length; i++) {
						newCollaArray.add(splitColla[i]);
					}

					Database.deleteFromTaskTable(itemId);

					// Back to main
					finish();
				}
			}
		});
	}
}
