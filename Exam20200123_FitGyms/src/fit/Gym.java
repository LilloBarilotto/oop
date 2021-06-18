package fit;

import java.util.Map;
import java.util.TreeMap;

public class Gym {
	
	private String name;
	private Map<Integer, Lesson[]> days;
	
	public Gym(String name) {
		
		this.name=name;
		this.days= new TreeMap<Integer, Lesson[]>();
		
		for(int i=1; i<=7; i++) {
			days.put(i, new Lesson[13]);
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Lesson[]> getDays() {
		return days;
	}

	public void setDays(Map<Integer, Lesson[]> days) {
		this.days = days;
	}
	
	
	
}
