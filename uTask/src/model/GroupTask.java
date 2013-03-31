package Model;

public class GroupTask {

	private String id = "";
	private String groupName = "";

	public static String autoGroupGenerateId() {
		String autogenerated;
		int highestInt = 0;

		if (Data.getGroupList().size() != 0) {

			int lastPosition = Data.getGroupList().size() - 1;

			highestInt = Integer.parseInt(Data.getGroupList().get(lastPosition)
					.getId().substring(1));
		}

		autogenerated = "G" + ++highestInt;
		return autogenerated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
