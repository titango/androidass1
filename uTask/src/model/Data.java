package Model;

import java.util.ArrayList;

public class Data {

	// Properties
	public static ArrayList<GroupTask> groupList = new ArrayList<GroupTask>();
	public static ArrayList<Task> taskList = new ArrayList<Task>();

	public static ArrayList<GroupTask> getGroupList() {
		return groupList;
	}

	public static void setGroupList(ArrayList<GroupTask> groupList) {
		Data.groupList = groupList;
	}

	public static ArrayList<Task> getTaskList() {
		return taskList;
	}

	public static void setTaskList(ArrayList<Task> taskList) {
		Data.taskList = taskList;
	}
	
	public static void refreshData(){
		Data.groupList.clear();
		Data.taskList.clear();
	}

	
}
