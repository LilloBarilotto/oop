package fit;

public class Reservation {

	private Integer customerId;
	private String gymName;
	private Integer day;
	private Integer slot;
	private Lesson lesson;

	public Reservation(Integer customerId, String gymName, Integer day, Integer slot, Lesson lesson) {
		this.customerId = customerId;
		this.gymName = gymName;
		this.day = day;
		this.slot = slot;
		this.lesson=lesson;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	
}
