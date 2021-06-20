package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Vaccines {

    public final static int CURRENT_YEAR = java.time.LocalDate.now().getYear();
    
    Map<String, Person> persons = new TreeMap<>();
    Set<Integer> ageIntervals = new TreeSet<>();
    Map<String, Hub> hubs = new TreeMap<>();
    List<Integer> hours = new ArrayList<>();
    
    
    // R1
    /**
     * Add a new person to the vaccination system.
     *
     * Persons are uniquely identified by SSN (italian "codice fiscale")
     *
     * @param first first name
     * @param lastName last name
     * @param ssn italian "codice fiscale"
     * @param year birth year
     * @return {@code false} if ssn is duplicate,
     */
    public boolean addPerson(String first, String lastName, String ssn, int year) {
    	
    	if(persons.containsKey(ssn)) {
    		return false;
    	}
    	
    	persons.put(ssn, new Person(ssn, lastName, first, year));
    	
        return true;
    }

    /**
     * Count the number of people added to the system
     *
     * @return person count
     */
    public int countPeople() {
        return persons.size();
    }

    /**
     * Retrieves information about a person.
     * Information is formatted as ssn, last name, and first name
     * separate by {@code ','} (comma).
     *
     * @param ssn "codice fiscale" of person searched
     * @return info about the person
     */
    public String getPerson(String ssn) {
    	
    	Person p = persons.get(ssn);
    	
        return String.format("%s,%s,%s",p.getSsn(), p.getLast(), p.getFirst());
    }

    /**
     * Retrieves of a person given their SSN (codice fiscale).
     *
     * @param ssn "codice fiscale" of person searched
     * @return age of person (in years)
     */
    public int getAge(String ssn) {
    	
    	Person p = persons.get(ssn);
    	
        return (CURRENT_YEAR - p.getYearBirth() );
    }

    /**
     * Define the age intervals by providing the breaks between intervals.
     * The first interval always start at 0 (non included in the breaks)
     * and the last interval goes until infinity (not included in the breaks).
     * All intervals are closed on the lower boundary and open at the upper one.
     * <p>
     * For instance {@code setAgeIntervals(40,50,60)}
     * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
     *
     * @param brks the array of breaks
     */
    public void setAgeIntervals(int... brks) {
    	
    	for(int b : brks) {
    		ageIntervals.add(b);
    	}
    	
    	
    }

    /**
     * Retrieves the labels of the age intervals defined.
     *
     * Interval labels are formatted as {@code "[0,10)"},
     * if the upper limit is infinity {@code '+'} is used
     * instead of the number.
     *
     * @return labels of the age intervals
     */
    public Collection<String> getAgeIntervals() {
    	
    	List<String> coll= new ArrayList<>();
    	int precAge=0;
    	
    	for(int age : ageIntervals) {		
    	
    		coll.add(String.format("[%d,%d)", precAge, age));
    		precAge=age;
    	}
    		
    	coll.add(String.format("[%d,%c)", precAge, '+'));
    	
        return coll;
    }

    /**
     * Retrieves people in the given interval.
     *
     * The age of the person is computed by subtracting
     * the birth year from current year.
     *
     * @param interval age interval label
     * @return collection of SSN of person in the age interval
     */
    public Collection<String> getInInterval(String interval) {
    	
    	int curr= java.time.LocalDate.now().getYear();
    	
    	  StringBuilder sb = new StringBuilder(interval);
    	  
          sb.deleteCharAt(interval.length() - 1);
          sb.deleteCharAt(0);
          
          interval= sb.toString();
    	
    	String[] attribute = interval.split(",");
    	
    	
    	
    	int min= Integer.parseInt(attribute[0]);
    	int max;
    	
    	if(attribute[1].equals("+")) {
    		max=Integer.MAX_VALUE;
    	}
    	else {
    		max=Integer.parseInt(attribute[1]);
    	}
    	
        return persons.values().stream().filter(x -> (curr-x.getYearBirth())>=min && (curr-x.getYearBirth())< max)
            	.map(x -> x.getSsn()).collect(Collectors.toList());
    }

    // R2
    /**
     * Define a vaccination hub
     *
     * @param name name of the hub
     * @throws VaccineException in case of duplicate name
     */
    public void defineHub(String name) throws VaccineException {
    	
    	if(hubs.containsKey(name)) {
    		throw new VaccineException();
    	}
    	
    	hubs.put(name, new Hub(name));
    	
    }

    /**
     * Retrieves hub names
     *
     * @return hub names
     */
    public Collection<String> getHubs() {
        return hubs.keySet();
    }

    /**
     * Define the staffing of a hub in terms of
     * doctors, nurses and other personnel.
     *
     * @param name name of the hub
     * @param countDoctors number of doctors
     * @param countNurse number of nurses
     * @param other number of other personnel
     * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
     */
    public void setStaff(String name, int countDoctors, int countNurse, int other) throws VaccineException {
    	
    	if(!hubs.containsKey(name) || countDoctors<=0 || countNurse<=0 || other<=0) {
    		throw new VaccineException();
    	}
    	
    	Hub h = hubs.get(name);
    	
    	h.setCountDoctors(countDoctors);
    	h.setCountNurse(countNurse);
    	h.setOther(other);
    }

    /**
     * Estimates the hourly vaccination capacity of a hub
     *
     * The capacity is computed as the minimum among
     * 10*number_doctor, 12*number_nurses, 20*number_other
     *
     * @param hubName name of the hub
     * @return hourly vaccination capacity
     * @throws VaccineException in case of undefined or hub without staff
     */
    public int estimateHourlyCapacity(String hubName) throws VaccineException {
    	
    	if(!hubs.containsKey(hubName)) {
    		throw new VaccineException();
    	}
    	
    	Hub h = hubs.get(hubName);
    	
    	if(h.getCountDoctors()==-1) {
    		throw new VaccineException();
    	}
    	
    	
        return h.getEstimateCapacity();
    }

    // R3
    /**
     * Load people information stored in CSV format.
     *
     * The header must start with {@code "SSN,LAST,FIRST"}.
     * All lines must have at least three elements.
     *
     * In case of error in a person line the line is skipped.
     *
     * @param people {@code Reader} for the CSV content
     * @return number of correctly added people
     * @throws IOException in case of IO error
     * @throws VaccineException in case of error in the header
     */
    public long loadPeople(Reader people) throws IOException, VaccineException {
        // Hint:
        BufferedReader br = new BufferedReader(people);
        String line;
		String[] attribute;
		int count=0;
		
		line=br.readLine();
		attribute=line.split(",");
		
		if(attribute.length!=4 || !attribute[0].equals("SSN") || !attribute[1].equals("LAST")
				|| !attribute[2].equals("FIRST") || !attribute[3].equals("YEAR")) {
			throw new VaccineException();
		}
		
        try {
			
			while( (line=br.readLine()) != null) {
	
				attribute=line.split(",");
				
				if(attribute.length==4 && !persons.containsKey(attribute[0])) {
					
					int yearBirth= Integer.parseInt(attribute[3]);
					count++;
					persons.put(attribute[0], new Person(attribute[0], attribute[1], attribute[2], yearBirth));
				}
			}
			
			br.close();
	        return count;
        }
		catch(IOException e) {
			throw e;
		}
        
    }

    // R4
    /**
     * Define the amount of working hours for the days of the week.
     *
     * Exactly 7 elements are expected, where the first one correspond to Monday.
     *
     * @param h workings hours for the 7 days.
     * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
     */
    public void setHours(int... h) throws VaccineException {
    	if(h.length!=7) {
    		throw new VaccineException();
    	}
    	
    	for(int hh : h) {
    		if(hh>12) {
        		throw new VaccineException();
    		}
    	}
    	
    	for(int hh: h) {
    		hours.add(hh);
    	}
    	
    	
    }

    /**
     * Returns the list of standard time slots for all the days of the week.
     *
     * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
     * they cover the number of working hours defined through method {@link #setHours}.
     * <p>
     * Times are formatted as {@code "09:00"} with both minuts and hours on two
     * digits filled with leading 0.
     * <p>
     * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
     *
     * @return the list hours for each day of the week
     */
    public List<List<String>> getHours() {
    	
    	List<List<String>> hourssss = new ArrayList<>();
    	
    	for(int h : hours) {
    		List<String> hourss= new ArrayList<>();
    		
    		for(int hh=9; hh< 9+h; hh++) {
    			for(int mm=0; mm<60; mm=mm+15) {
    				hourss.add(String.format("%02d:%02d", hh, mm));
    			}
    		}
    		
    		hourssss.add(hourss);
    	}
    	
    	
        return hourssss;
    }

    /**
     * Compute the available vaccination slots for a given hub on a given day of the week
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the hourly capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public int getDailyAvailable(String hubName, int day) {
    	int hour = hours.get(day);
    	
    	Hub h= hubs.get(hubName);
    	
        return h.getEstimateCapacity()*hour;
    }

    /**
     * Compute the available vaccination slots for each hub and for each day of the week
     * <p>
     * The method returns a map that associates the hub names (keys) to the lists
     * of number of available hours for the 7 days.
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public Map<String, List<Integer>> getAvailable() {
    	Map<String, List<Integer>> y = null;
    	
    	/*y = hubs.values().stream().collect(
    			Collectors.toMap(
    					h -> h.getName(), 
    					Collectors.mapping(Collectors.toList(),for(int i=0; i<7; i++) {}))
    			);*/
    	
    	
        return y;
    }

    /**
     * Computes the general allocation plan a hub on a given day.
     * Starting with the oldest age intervals 40%
     * of available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is the list of SSNs (codice fiscale) of the
     * persons allocated to that day
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     *
     * @param hubName name of the hub
     * @param day day of week index (0 = Monday)
     * @return the list of daily allocations
     */
    public List<String> allocate(String hubName, int day) {
        return null;
    }

    /**
     * Removes all people from allocation lists and
     * clears their allocation status
     */
    public void clearAllocation() {
    }

    /**
     * Computes the general allocation plan for the week.
     * For every day, starting with the oldest age intervals
     * 40% available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is a list with 7 elements, one
     * for every day of the week, each element is a map that
     * links the name of each hub to the list of SSNs (codice fiscale)
     * of the persons allocated to that day in that hub
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     * but the same invocation (after {@link #clearAllocation}) must return the same
     * allocation.
     *
     * @return the list of daily allocations
     */
    public List<Map<String, List<String>>> weekAllocate() {
        return null;
    }

    // R5
    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system
     *
     * @return proportion of allocated people
     */
    public double propAllocated() {
        return -1.0;
    }

    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system, divided by age interval.
     * <p>
     * The map associates the age interval label
     * to the proportion of allocates people in that interval
     *
     * @return proportion of allocated people by age interval
     */
    public Map<String, Double> propAllocatedAge() {
        return null;
    }

    /**
     * Retrieves the distribution of allocated persons
     * among the different age intervals.
     * <p>
     * For each age intervals the map reports the
     * proportion of allocated persons in the corresponding
     * interval w.r.t the total number of allocated persons
     *
     * @return
     */
    public Map<String, Double> distributionAllocated() {
        return null;
    }

    // R6
    /**
     * Defines a listener for the file loading method.
     * The {@ accept()} method of the listener is called
     * passing the line number and the offending line.
     * <p>
     * Lines start at 1 with the header line.
     *
     * @param lsnr the listener for load errors
     */
    public void setLoadListener(BiConsumer<Integer, String> lsnr) {
    }
}
