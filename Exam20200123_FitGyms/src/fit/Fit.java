package fit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Fit {
    
    public static int MONDAY    = 1;
    public static int TUESDAY   = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY  = 4;
    public static int FRIDAY    = 5;
    public static int SATURDAY  = 6;
    public static int SUNDAY    = 7;
   
    
    Map<String, Gym> gyms = new TreeMap<String, Gym>();
    Map<Integer, String> customers = new TreeMap<Integer, String>();
    List<Reservation> reservations = new ArrayList<Reservation>();
    List<LessonGiven> lessonGivens = new ArrayList<LessonGiven>();
    
	public Fit() {
		
	}
	// R1 
	
	public void addGymn (String name) throws FitException {
		if(gyms.containsKey(name)) {
			throw new FitException();
		}
		
		gyms.put(name, new Gym(name));
	}
	
	public int getNumGymns() {
		return gyms.size();
	}
	
	//R2

	public void addLessons (String gymnname, 
	                        String activity, 
	                        int maxattendees, 
	                        String slots, 
	                        String ...allowedinstructors) throws FitException{
		if(!gyms.containsKey(gymnname)) {
			throw new FitException();
		}
		
		String[] aLotOfSlot = slots.split(",");
		
		for(String les : aLotOfSlot) {
			String[] daySlot = les.split("\\.");
			
			Integer day = Integer.parseInt(daySlot[0]);
			Integer slot = Integer.parseInt(daySlot[1]);
			
			if(day<1 || day>7 || slot<8 || slot>20) {
				throw new FitException();
			}
			
			Lesson[] lessons = gyms.get(gymnname).getDays().get(day);
			
			if(lessons[slot-8]!=null) {
				throw new FitException();
			}
		}
		
		Lesson x = new Lesson(activity, allowedinstructors, maxattendees);
		
		
		for(String les : aLotOfSlot) {
			String[] daySlot = les.split("\\.");
			
			Integer day = Integer.parseInt(daySlot[0]);
			Integer slot = Integer.parseInt(daySlot[1]);
			
			Lesson[] lessons = gyms.get(gymnname).getDays().get(day);
			
			lessons[slot-8]=x;
		}
		
	}
	
	//R3
	public int addCustomer(String name) {
		int res = customers.size() + 1;
		
		customers.put(res, name);
		
		return res;
	}
	
	public String getCustomer (int customerid) throws FitException{
		
		if(!customers.containsKey(customerid)) {
			throw new FitException();
		}
		
	    return customers.get(customerid);
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymnname, int day, int slot) throws FitException{
		if(!customers.containsKey(customerid)) {
			throw new FitException();
		}
		
		if(!gyms.containsKey(gymnname)) {
			throw new FitException();
		}
		
		if(day<1 || day>7 || slot<8 || slot>20) {
			throw new FitException();
		}
		
		Lesson[] lessons = gyms.get(gymnname).getDays().get(day);
		
		if(lessons[slot-8]==null) {
			throw new FitException();
		}
		
		Lesson l = lessons[slot-8];	
		
		if(reservations.stream().anyMatch(x-> x.getCustomerId()==customerid && x.getDay()==day && x.getSlot()==slot && x.getLesson()==l)) {
			throw new FitException();
		}
		
		int count = (int)reservations.stream().filter(x -> x.getDay()==day && x.getSlot()==slot && x.getGymName().equals(gymnname))
							.count();
		
		if(l.getMaxattendees()==count) {
			throw new FitException();
		}
		
		reservations.add(new Reservation(customerid, gymnname, day, slot, l));
		
	}
	
	public int getNumLessons(int customerid) {
		return (int)reservations.stream().filter(x -> x.getCustomerId()==customerid).count();
	}
	
	
	//R5
	
	public void addLessonGiven (String gymnname, int day, int slot, String instructor) throws FitException{
		if(!gyms.containsKey(gymnname)) {
			throw new FitException();
		}
		
		if(day<1 || day>7 || slot<8 || slot>20) {
			throw new FitException();
		}
		
		Lesson[] lessons = gyms.get(gymnname).getDays().get(day);
		
		if(lessons[slot-8]==null) {
			throw new FitException();
		}
		
		Lesson l = lessons[slot-8];
		
		List<String> instructorsList = new ArrayList<String>();
		
		for(String ins : l.getInstructors()) {
			instructorsList.add(ins);
		}
		
		if(!instructorsList.contains(instructor)) {
			throw new FitException();
		}
		
		lessonGivens.add(new LessonGiven(gymnname, day, slot, instructor));
	}
	
	public int getNumLessonsGiven (String gymnname, String instructor) throws FitException {
		
		if(!gyms.containsKey(gymnname)) {
			throw new FitException();
		}
		
	    return (int) lessonGivens.stream().filter(x -> x.getGymName().equals(gymnname) && x.getInstructor().equals(instructor))
	    			.count();
	}
	//R6
	
	public String mostActiveGymn() {
		
		Gym gymOp=  gyms.values().stream().max(Comparator.comparingLong(x-> x.getDays().values().stream().flatMap(e -> Stream.of(e)).filter(u -> u!=null).count())).get();
			
		return gymOp.getName();
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {		
		
		Map<String, Integer> result =
				gyms.values().stream().collect(
				Collectors.toMap(
						x -> x.getName(),
						x -> (int)x.getDays().values().stream().flatMap(e -> Stream.of(e)).filter(u -> u!=null).count()
						)
				);
		
		return result;
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymnname) throws FitException{
		if(!gyms.containsKey(gymnname)) {
			throw new FitException();
		}
		
		Stream<Reservation> reservationsSpecial =
					reservations.stream().filter(x -> x.getGymName().equals(gymnname));
												
		Map<String, Long> daySlotCounter = reservationsSpecial.collect(
				Collectors.groupingBy(
						x-> String.format("%d.%d", x.getDay(), x.getSlot()),
						Collectors.counting()
						)
				);
		
		SortedMap<Integer, List<String>> result = daySlotCounter.entrySet().stream().collect(
					Collectors.groupingBy(
							e -> (int)(long)e.getValue(),
							() -> new TreeMap<Integer, List<String>>(),
							Collectors.mapping(e -> e.getKey(), Collectors.toList())
							)
				);
		
		List<String> SlotWithZero = new ArrayList<String>();
		
		for(int i=1; i<=7; i++) {
			for(int j=8; j<=20; j++) {
				int ii=i;
				int jj=j;
				if( reservations.stream().filter(x -> x.getGymName().equals(gymnname) && x.getDay()==ii && x.getSlot()==jj).count()==0.0) {
					SlotWithZero.add(String.format("%d.%d", i, j));
				}
			}
		}
		
		result.put(0, SlotWithZero);
		
		return result;
	}

}
