package timesheet;

public class Report {
	public String workerID;
	public String projectName;
	public String activityName;
	public int day;
	public int workedHours;
	
	public Report(String workerID, String projectName, String activityName, int day, int workedHours) {
		// TODO Auto-generated constructor stub
		this.workerID=workerID;
		this.projectName=projectName;
		this.activityName=activityName;
		this.day=day;
		this.workedHours=workedHours;
	}

}
