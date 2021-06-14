package timesheet;

import java.util.Map;
import java.util.TreeMap;

public class Project {

	private String name; 
	private Integer maxHours;
	private Map<String, Boolean> activities= new TreeMap<String, Boolean>(); //<activity, isclosed>
	
	public Project(String name, Integer maxHours) {
		this.name=name;
		this.maxHours=maxHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Boolean> getActivities() {
		return activities;
	}

	public void setActivities(Map<String, Boolean> activities) {
		this.activities = activities;
	}

	public Integer getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(Integer maxHours) {
		this.maxHours = maxHours;
	}
	
	
	
}
