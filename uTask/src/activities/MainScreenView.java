package activities;

import java.util.ArrayList;

import Assignment.utask.R;
import Model.Data;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import database.Database;

public class MainScreenView extends Activity {
	
	/*---Properties---*/
	Database database = new Database(this);
	
	//All input tools
	Spinner groupSpin, sortSpin;
	ListView listView;
	ImageButton taskBut, groupBut;
	
	ArrayAdapter<String> spinGroupAdapt, sortAdapt;
	ArrayList<String> groupName = new ArrayList<String>();
	ArrayList<String> sortData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		//Create or Open database and load into Model
		Database.createDatabase();
		Database.loadToData();
		
		//Create 2 spinners
		groupSpin = (Spinner) findViewById(R.id.groupSpin);
		sortSpin = (Spinner) findViewById(R.id.sortSpin);
		
		//Create 2 ImageButton
		taskBut = (ImageButton) findViewById(R.id.addTaskButton);
		groupBut = (ImageButton) findViewById(R.id.addGroupTaskButton);
		
		//Add the total group to the groupName arraylist and setAdapter
		groupName = refreshGroupName();
		spinGroupAdapt = new ArrayAdapter<String>(this,R.layout.myspinner_text_view, groupName);
		spinGroupAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupSpin.setAdapter(spinGroupAdapt);
		
		//Add the options of sorting to sortData and set Adapter
		sortData.add("Date");
		sortData.add("Priority");
		sortData.add("Order");
		sortAdapt = new ArrayAdapter<String>(this, R.layout.myspinner_text_view, sortData);
		sortAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortSpin.setAdapter(sortAdapt);
		
		//Add controller
		
		taskBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	
	//This will refresh the item once the group is added
	public ArrayList<String> refreshGroupName(){
		ArrayList<String> groupName = new ArrayList<String>();
		for(int i = 0; i < Data.getGroupList().size(); i++){
			groupName.add(Data.getGroupList().get(i).getGroupName());
		}
		
		return groupName;
	}

}
