package timesheet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Timesheet {
	Set<Integer> holydays = new TreeSet<Integer>();
	int block= 0;
	Map<String, Integer> projects = new TreeMap<String, Integer>();
	Integer firstWeekDay = 1;
	
		//Activity, Project
	Map<String, Set<String>> openedActivities = new TreeMap<String, Set<String>>();
	Map<String, Set<String>> closedActivities = new TreeMap<String, Set<String>>();
	
	Integer countProfile=0; 
	Integer countWorker=0;
	Integer countReport=0;
	
	Map<String, int[]> profiles 	  = new TreeMap<String, int[]>();
	Map<String, Worker>		   workers		  = new TreeMap<String, Worker>();
	Map<Integer,Report> reports			= new TreeMap<Integer, Report>();
	
	
	// R1
	public void setHolidays(int... holidays) {
		
		if(block!=1) {
			for(Integer x : holidays) {
				if(x>0 && x<=365) {
					this.holydays.add(x);
				}
			}
			block=1;
		}
		
	}
	
	public boolean isHoliday(int day) {
		return holydays.contains(day);
	}
	
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
	
		if(weekDay<0 || weekDay>6) {
			throw new TimesheetException();
		}
		
		this.firstWeekDay=weekDay;
	}
	
	public int getWeekDay(int day) throws TimesheetException {
	    
		if(day<1) {
			throw new TimesheetException();
		}
		
		return -1;
	}
	
	// R2
	public void createProject(String projectName, int maxHours) throws TimesheetException {
		
		if(maxHours<0) {
			throw new TimesheetException();
		}
		
		if(!projects.containsKey(projectName)) {
			openedActivities.put(projectName, new TreeSet<String>());
			closedActivities.put(projectName, new TreeSet<String>());
		}
		
		projects.put(projectName, maxHours);
	}
	
	public List<String> getProjects() {
		
		return	projects.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.map(x -> (String) String.format("%s: %d", x.getKey(), x.getValue()))
			.collect(Collectors.toList());
	}
	
	public void createActivity(String projectName, String activityName) throws TimesheetException {
		
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		
		if(!openedActivities.containsKey(projectName)) {
			
		}
		
		openedActivities.get(projectName).add(activityName);
	
	}
	
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
		if(!projects.containsKey(projectName) || !openedActivities.get(projectName).contains(activityName)) {
			throw new TimesheetException();
		}
		
		openedActivities.get(projectName).remove(activityName);
		closedActivities.get(projectName).add(activityName);
		
	}
	
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
        
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		return openedActivities.get(projectName).stream()
		.sorted()
		.collect(Collectors.toList());
	}

	// R3
	public String createProfile(int... workHours) throws TimesheetException {
		
		if(workHours.length!=7) {
			throw new TimesheetException();
		}
		
		String x = countProfile.toString();
		countProfile++;
		profiles.put(x, workHours.clone());
		
		
        return x;
	}
	
	public String getProfile(String profileID) throws TimesheetException {
		
		if(!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}
		
		int[] v = profiles.get(profileID);
		
        return String.format("Sun: %d; Mon: %d; Tue: %d; Wed: %d; Thu: %d; Fri: %d; Sat: %d",
        		v[0], v[1], v[2], v[3], v[4], v[5], v[6]);
	}
	
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
        
		if(!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}
		
		String x = countWorker.toString();
		countWorker++;
		
		workers.put(x, new Worker(name, surname, x, profileID));
		
		return x;
	}
	
	public String getWorker(String workerID) throws TimesheetException {
		if(!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}
		
		Worker x= workers.get(workerID);
		
        return String.format("%s %s (%s)", x.name, x.surname, getProfile(x.profileID));
	}
	
	// R4
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
	
		if(!workers.containsKey(workerID) || day<0 || isHoliday(day)|| workedHours<0
				|| workedHours> profiles.get(workers.get(workerID).profileID)[getWeekDay(day)]
						|| !projects.containsKey(projectName) || !openedActivities.get(projectName).contains(activityName)
						||  closedActivities.get(projectName).contains(activityName)
						/*|| •le ore totali del progetto non superino il valore massimo ammesso???? */) {
			throw new TimesheetException();
		}
			
		reports.put(countWorker, new Report(workerID, projectName, activityName, day, workedHours));
	
	}
	
	public int getProjectHours(String projectName) throws TimesheetException {
		if(!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		return reports.entrySet().stream().filter(x -> x.getValue().projectName.contentEquals(projectName))
			.map(x -> x.getValue().workedHours)
			.collect(Collectors.summingInt(x-> x));
		
	}
	
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
        return reports.entrySet().stream().filter(x -> x.getValue().workerID.equals(workerID) && x.getValue().day==day)
        		.collect(Collectors.summingInt(x -> x.getValue().workedHours));
	}
	
	// R5
	public Map<String, Integer> countActivitiesPerWorker() {
        return reports.entrySet().stream()
        		.collect(Collectors.groupingBy(x -> x.getValue().workerID,  
        						Collectors.summingInt(null)));
	}
	
	public Map<String, Integer> getRemainingHoursPerProject() {
        return null;
	}
	
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
        return null;
	}
}
