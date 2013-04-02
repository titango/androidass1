package custom;

import java.util.ArrayList;

import database.Database;

import Assignment.utask.R;
import Model.Data;
import Model.GroupTask;
import activities.GroupView;
import activities.MainScreenView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyGroupTaskAdapter extends ArrayAdapter<GroupTask>{

	private Context context;
	private ArrayList<GroupTask> taskGroup = new ArrayList<GroupTask>();
	TextView viewGroupName;
	ImageButton deleteGroupName;

	public MyGroupTaskAdapter(Context context, int textViewResourceId,
			ArrayList<GroupTask> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.taskGroup = objects;
	}
	
	public int getCount() {
		return this.taskGroup.size();
	}

	public GroupTask getItem(int index) {
		return this.taskGroup.get(index);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.group_list_item, parent, false);
		}

		GroupTask taskGroup = getItem(position);
		viewGroupName = (TextView) row.findViewById(R.id.group_list_item_groupName);
		deleteGroupName = (ImageButton) row.findViewById(R.id.group_list_item_delete);
		
		viewGroupName.setText(taskGroup.getGroupName());
		deleteGroupName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(GroupTask gt : Data.getGroupList()){
					if(gt.getGroupName().equals(viewGroupName.getText().toString())){
						Database.deleteFromGroupTaskTable(gt.getId());
						Log.i("The ID" , gt.getId());
						Data.getGroupList().remove(gt);
						break;
					}
				}
				GroupView.getUnique().getGroupAdapt().notifyDataSetChanged();
				MainScreenView.getUnique().refreshGroupName();
				MainScreenView.getUnique().getSpinGroupAdapt().notifyDataSetChanged();
			}
		});
		
		
		return row;
	}
}