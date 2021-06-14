package timesheet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Timesheet {
	
	Set<Integer> holidays = new TreeSet<Integer>();
	int block=0;
	Map<Integer, Integer> daysForWeek = new TreeMap<Integer, Integer>();
	Map<String, Project> projects = new TreeMap<String, Project>();
	Map<String, List<Integer>> profiles = new TreeMap<String, List<Integer>>();
	Map<String, Worker> workers = new TreeMap<String, Worker>();
	List<Report> reports = new ArrayList<Report>();
	
	
	//default day1=Monday
	public Timesheet() {
		for(int i=0; i<7; i++) {
			daysForWeek.put(i, (1+i)%7);
		}
	}
	
	// R1
	public void setHolidays(int... holidays) {
		
		if(block==1) {
			return ;
		}
		
		for(Integer holiday : holidays) {
			if(holiday>0) {
				this.holidays.add(holiday);
			}
		}
		
		block=1;
		
	}
	
	public boolean isHoliday(int day) {
		return holidays.contains(day);
	}
	
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
		
		if(weekDay<0 || weekDay>6) {
			throw new TimesheetException();
		}
		
		for(int i=0; i<7; i++) {
			daysForWeek.put(i, (weekDay+i)%7);
		}
		
	}
	
	public int getWeekDay(int day) throws TimesheetException {
		
		if(day<1) {
			throw new TimesheetException();
		}
		
		int x = (day-1)%7;
		
	    return daysForWeek.get(x);
	}
	
	// R2
	public void createProject(String projectName, int maxHours) throws TimesheetException {
		if(maxHours<0) {
			throw new TimesheetException();
		}
		
		if(projects.containsKey(projectName)) {
			projects.get(projectName).setMaxHours(maxHours);
		}
		else {
			projects.put(projectName, new Project(projectName, maxHours));
		}
	}
	
	public List<String> getProjects() {
		 Comparator<Project> comp = Comparator.comparingInt( (Project x) -> x.getMaxHours()).reversed().thenComparing(x -> x.getName());
	
         return  projects.values().stream().sorted(comp).map(x -> String.format("%s: %d", x.getName(), x.getMaxHours()))
              	.collect(Collectors.toList());
	}
	
	public void createActivity(String projectName, String activityName) throws TimesheetException {
		
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		projects.get(projectName).getActivities().put(activityName, false);
		
	}
	
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
		
		if(!projects.containsKey(projectName) || !projects.get(projectName).getActivities().containsKey(activityName)) {
			throw new TimesheetException();
		}
		
		projects.get(projectName).getActivities().put(activityName, true);
		
	}
	
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
        
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		return projects.get(projectName).getActivities().entrySet().stream().filter(x -> x.getValue()==false).map(x -> x.getKey())
				.sorted().collect(Collectors.toList());
	}

	// R3
	public String createProfile(int... workHours) throws TimesheetException {
		
		if(workHours.length!=7) {
			throw new TimesheetException();
		}
		
		List<Integer> listWorkHours = new ArrayList<Integer>();
		for( Integer h : workHours) {
			listWorkHours.add(h);
		}
		
		String profileID  = String.format("%d", profiles.size());
		
		profiles.put(profileID, listWorkHours);
		
        return profileID;
	}
	
	public String getProfile(String profileID) throws TimesheetException {
        
		
		if(!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}
	
		List<Integer> h = profiles.get(profileID);
		
		return  String.format("Sun: %d; Mon: %d; Tue: %d; Wed: %d; Thu: %d; Fri: %d; Sat: %d",
				h.get(0), h.get(1), h.get(2), h.get(3), h.get(4), h.get(5), h.get(6));
	}
	
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
        
		if(!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}
		
		String workerID  = String.format("%d", workers.size());
		
		workers.put(workerID, new Worker(name, surname, profileID));
				
		return workerID;
	}
	
	public String getWorker(String workerID) throws TimesheetException {
		
		if(!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}
		
		Worker w = workers.get(workerID);
		
        return String.format("%s %s (%s)", w.getName(), w.getSurname(), getProfile(w.getProfileID()));
	}
	
	// R4
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
	
		if(!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}
		if(day<1 || day>365 || isHoliday(day)) {
			throw new TimesheetException();
		}
		if(!projects.containsKey(projectName) || !projects.get(projectName).getActivities().containsKey(activityName) || projects.get(projectName).getActivities().get(activityName)==true) {
			throw new TimesheetException();
		}
		if(workedHours<0 || profiles.get(workers.get(workerID).getProfileID()).get(getWeekDay(day))< workedHours) {
			throw new TimesheetException();
		}
		
		if(reports.stream().filter(x -> x.getProjectName().equals(projectName)).mapToInt(x -> x.getWorkedHours()).sum()+workedHours > projects.get(projectName).getMaxHours()) {
			throw new TimesheetException();
		}
		
		reports.add(new Report(workerID, projectName, activityName, day, workedHours));
	}
	
	public int getProjectHours(String projectName) throws TimesheetException {
		
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
        return reports.stream().filter(x -> x.getProjectName().equals(projectName)).mapToInt(x -> x.getWorkedHours()).sum();
	}
	
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
		
		if(!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}
		
		if(day<=0) {
			throw new TimesheetException();
		}
		
        return reports.stream().filter(x -> x.getWorkerID().equals(workerID) && x.getDay().equals(day))
        		.mapToInt(x -> x.getWorkedHours()).sum();
	}
	
	// R5
	public Map<String, Integer> countActivitiesPerWorker() {
		
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Set<String> workersID = workers.keySet();
		
		for(String workerID : workersID) {
			Integer count = (int) reports.stream().filter(x -> x.getWorkedHours()>=1 && x.getWorkerID().equals(workerID))
												.distinct().count();
			
			map.put(workerID,count);
		}
		
        return map;
	}
	
	public Map<String, Integer> getRemainingHoursPerProject() {
		
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Set<String> projectsID = projects.keySet();
		
		for(String projectID : projectsID) {
			try {
				map.put(projectID, projects.get(projectID).getMaxHours() - getProjectHours(projectID));
			} catch (TimesheetException e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
		
		Map<String, Map<String, Integer>> map = new TreeMap<String, Map<String, Integer>>();
		Set<String> projectsID = projects.keySet();
		
		for(String projectID : projectsID) {
			Set<String> activities = projects.get(projectID).getActivities().keySet();
			
			Map<String, Integer> map2 = new TreeMap<String, Integer>();
			
			for(String activity : activities) {
				map2.put(activity, reports.stream().filter(x -> x.getProjectName().equals(projectID) && x.getActivityName().equals(activity)).mapToInt(x -> x.getWorkedHours()).sum() );
			}
			
			map.put(projectID, map2);
		}
		
		return map;
	}
}
