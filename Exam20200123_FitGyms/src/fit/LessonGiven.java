package fit;

public class LessonGiven {
	
	private String  gymName;
	private Integer day;
	private Integer slot;
	private String instructor;
	
	public LessonGiven(String gymName, Integer day, Integer slot, String instructor) {
		this.gymName = gymName;
		this.day = day;
		this.slot = slot;
		this.instructor = instructor;
	}
	
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getSlot() {
		return slot;
	}
	public void setSlot(Integer slot) {
		this.slot = slot;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
}
