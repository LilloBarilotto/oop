package timesheet;

public class Worker {
	
	private String name;
	private String surname;
	private String profileID;
	
	
	public Worker(String name, String surname, String profileID)  {
		this.name=name;
		this.surname=surname;
		this.profileID=profileID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getProfileID() {
		return profileID;
	}


	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	
	
	
}
