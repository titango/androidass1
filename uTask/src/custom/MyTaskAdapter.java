package custom;

import java.util.ArrayList;

import Assignment.utask.R;
import Model.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyTaskAdapter extends ArrayAdapter<Task> {

	private Context context;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	TextView viewDate, viewTitle, viewPriority, viewStatus;

	public MyTaskAdapter(Context context, int textViewResourceId,
			ArrayList<Task> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.tasks = objects;
	}

	public int getCount() {
		return this.tasks.size();
	}

	public Task getItem(int index) {
		return this.tasks.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.task_list_item, parent, false);
		}

		Task task = getItem(position);
		viewDate = (TextView) row.findViewById(R.id.task_list_item_date);
		viewTitle = (TextView) row.findViewById(R.id.task_list_item_title);
		viewPriority = (TextView) row.findViewById(R.id.task_list_item_priority);
		viewStatus = (TextView) row.findViewById(R.id.task_list_item_status);
		
		String[] moDate = task.getDueDate().split("/");
		String fiDate = moDate[0] + "/" + moDate[1] + "\n" + moDate[2]; 
		viewDate.setText(fiDate);
		viewTitle.setText(task.getTitle());
		viewPriority.setText(task.getPriority());
		viewStatus.setText(task.getStatus());
		
		return row;
	}

}
