package university;

public class Student {
	private static final int MAX_STUD_COURSE=25;
	
	//NAME
	private String first;
	private String last;
	
	//ID
	private int id;
	
	//COURSE
	private Course[] course;
	private int effectiveCourse;
	
	Student(String first, String last, int id){
		this.first=first;
		this.last=last;
		this.id=id;	
		this.course=new Course[MAX_STUD_COURSE];
		this.effectiveCourse=0;
	}
	
	//OR OVERRIDE WITH toString()
	public String getInfo() {
		return id+" "+ first + " " + last +"\n";
	}
	
	public void addCourse(Course newCourse) {
		this.course[effectiveCourse++]=newCourse;
	}

	
	public String studyPlan() {
		String aLotOfInfo="";
		int i;
		Course cour;
		
		for(i=0; i<effectiveCourse; i++) {
			cour=course[i];
			aLotOfInfo+=cour.getInfo();
		}		
		return aLotOfInfo;
	}
	
	public int getId() {
		return this.id;
	}
}
