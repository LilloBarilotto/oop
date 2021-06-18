package fit;

public class Lesson {
	private String name;
	private String[] instructors;
	private int maxattendees;
	
	public Lesson(String name, String[] instructors, int maxattendees) {
		this.name=name;
		this.instructors=instructors;
		this.maxattendees=maxattendees;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getInstructors() {
		return instructors;
	}

	public void setInstructors(String[] instructors) {
		this.instructors = instructors;
	}

	public int getMaxattendees() {
		return maxattendees;
	}

	public void setMaxattendees(int maxattendees) {
		this.maxattendees = maxattendees;
	}

}
