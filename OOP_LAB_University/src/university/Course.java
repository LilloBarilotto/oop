package university;

public class Course {
	private static final int MAX_ATTENDEES=100;
	
	
	private String teacher;
	private String title;
	private int code;
	private int effectiveStud;
	private Student[] student;
	
	Course(String title, String name, int code){
		this.title=title;
		this.teacher=name;
		this.code=code;
		this.student=new Student[MAX_ATTENDEES];
		this.effectiveStud=0;
	}
	
	public String getInfo() {
		return code+","+title+","+teacher+"\n";
	}
	
	public void addStudent(Student newStudent) {
		this.student[effectiveStud++]=newStudent;
	}

	public String listAttend() {
		String aLotOfInfo="";
		Student stud;
		int i;
		
		for(i=0; i<effectiveStud; i++) {
			stud=student[i];
			aLotOfInfo+=stud.getInfo();
		}
		return aLotOfInfo;
	}
	
	public int getCode() {
		return this.code;
	}
	
}