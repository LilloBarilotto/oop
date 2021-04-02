package university;

public class Course {
	private static final int MAX_ATTENDEES=100;
	
	
	private String teacher;
	private String title;
	private int code;
	
	//University
	private int effectiveStud;
	private Student[] student;
	
	//UniversityExt
	private int[] exam;
	
	Course(String title, String name, int code){
		this.title=title;
		this.teacher=name;
		this.code=code;
		this.student=new Student[MAX_ATTENDEES];
		this.effectiveStud=0;
		
		this.exam= new int[MAX_ATTENDEES];
	}
	
	//GETTER
	public String getTitle() {
		return this.title;
	}
	
	public int getCode() {
		return this.code;
	}
	
	//PRINT
	@Override
	public String toString() {
		return code+","+title+","+teacher+"\n";
	}
	
	public String getInfo() {
		return code+","+title+","+teacher+"\n";
	}
	
	
	//UNIVERSITY
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
	
	
	//UniversityExt
	//R5
		public void exam(int vote, int ID) {
			int i;
			
			for(i=0; i<effectiveStud && this.student[i].getId()!=ID; i++);
					
			if(i==effectiveStud) {
				System.err.println("Errore non esiste lo studente per questo corso");
				return;
			}
			
			this.exam[i]=vote;
		}
		
		public float courseAvg(){
			float media;
			int i, count;
			
			for(i=0, media=0, count=0; i<effectiveStud; i++) {
				if(this.exam[i]>0) {
					media+=this.exam[i];
					count++;
				}
			}
			
			if(count==0)
				return 0;
			
			return media/count;
		}	
}