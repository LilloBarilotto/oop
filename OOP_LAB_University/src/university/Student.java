package university;

public class Student {
	private static final int MAX_STUD_COURSE=25;
	
	private String first;
	private String last;
	private int id;
	
	
	//University
	private Course[] course;
	private int effectiveCourse;
	
	//UniversityExt
	private int[] exam;
	private int effectiveExam;
	
	Student(String first, String last, int id){
		this.first=first;
		this.last=last;
		this.id=id;	
		
		this.course=new Course[MAX_STUD_COURSE];
		this.effectiveCourse=0;
		
		this.exam= new int[MAX_STUD_COURSE];
		this.effectiveExam=0;
	}
	
	//GETTERS AND SETTERS
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getEffectiveCourse() {
		return this.effectiveCourse;
	}
	
	public String getInfo() {
		return id+" "+ first + " " + last +"\n";
	}
	
	@Override
	public String toString() {
		return id+", "+ first + " " + last;
	}
	
	
	//University
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
	
	
	//OKY DOKY NEW LAB UniversityExt
	
	//R5
	public void exam(int vote, int ID) {
		int i;
		
		for(i=0; i<effectiveCourse && this.course[i].getCode()!=ID; i++);
				
		if(i==effectiveCourse) {
			System.err.println("Errore non esiste il corso per questo studente");
			return;
		}
		
		this.exam[i]=vote;
		this.effectiveExam++;
	}
	
	public float studentAvg(){
		float media;
		int i, count;
		
		for(i=0, media=0, count=0; i<effectiveCourse; i++) {
			if(this.exam[i]>0) { 
				media+=this.exam[i];
				count++;
			}
		}
		
		if(count==0)
			return 0;
		
		return media/count;
	}
	
	public int getEffectiveExam() {
		return this.effectiveExam;
	}
	
}
