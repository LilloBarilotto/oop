package timesheet;

public class Report {

	private String workerID;
	private String projectName;
	private String activityName;
	private Integer day;
	private Integer workedHours;
	
	public Report(String workerID, String projectName, String activityName, Integer day, Integer workedHours) {
		this.workerID = workerID;
		this.projectName = projectName;
		this.activityName = activityName;
		this.day = day;
		this.workedHours = workedHours;
	}

	public String getWorkerID() {
		return workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(Integer workedHours) {
		this.workedHours = workedHours;
	}
	
	
	@Override
	public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }

	        if (obj.getClass() != this.getClass()) {
	            return false;
	        }
	        
	        Report x = (Report)obj;
	        
	        if(this.projectName.equals(x.getProjectName()) && this.activityName.equals(x.getActivityName())) {
	        	return true;
	        }
	        
	        return false;
	   }
	
}
