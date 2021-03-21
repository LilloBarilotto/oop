package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	public static final int MAX_STUDENT=1000;
	public static final int MAX_COURSE=50;
	public static final int FIRST_STUDENT=10000;
	public static final int FIRST_COURSE=10;
	
	//UNIVERSITY
	private String name;
	
	//RECTOR
	private String first;
	private String last;
	
	//COURSES AND STUDENTS
	private Student[] student;
	private int effectiveStudent;
	
	private Course[] course;
	private int effectiveCourse;
	
	public University(String name){
		this.name=name;
		this.student= new Student[MAX_STUDENT];
		this.course=new Course[MAX_COURSE];
		
		this.effectiveStudent=0;
		this.effectiveCourse=0;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setRector(String first, String last){
		this.first=first;
		this.last=last;
	}
	
	public String getRector(){
		return first+" "+last;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	
	
	public boolean checkID(int id) {
		if(id < FIRST_STUDENT || id >= this.effectiveStudent + FIRST_STUDENT)
			return true;
		return false;
	}
	public boolean checkCODE(int code) {
		if(code < FIRST_COURSE || code >= this.effectiveCourse + FIRST_COURSE)
			return true;
		return false;
	}
	
	public int enroll(String first, String last){
		if(this.effectiveStudent==MAX_STUDENT) {
			System.out.println("Sorry but we don't have enough space for a new student, ripperoni.");
			return -1;
		}
		
		Student stud = new Student(first, last, this.effectiveStudent+FIRST_STUDENT);
		this.student[this.effectiveStudent++]=stud;
		
		return stud.getId();
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		if(this.checkID(id))
			return "Sorry but this student doesn't exist";
		
		return this.student[id-FIRST_STUDENT].getInfo();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		if(this.effectiveCourse== MAX_COURSE) {
			System.out.println("Sorry but we don't have enough space for a new student, ripperoni.");
			return -1;
		}
		
		Course cour= new Course(title, teacher, this.effectiveCourse + FIRST_COURSE);
		this.course[this.effectiveCourse++]=cour;
		return cour.getCode();
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		if(this.checkCODE(code))
			return "Sorry but these course doesn't exist";
		
		return this.course[code-FIRST_COURSE].getInfo();
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//TODO: to be implemented
		if(this.checkID(studentID) || this.checkCODE(courseCode)) {
			System.out.println("You write a wrong code or id");
			return;
		}
		
		this.course[courseCode-FIRST_COURSE].addStudent(this.student[studentID-FIRST_STUDENT]);
		this.student[studentID-FIRST_STUDENT].addCourse(this.course[courseCode-FIRST_COURSE]);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int code){
		//TODO: to be implemented		
		return this.course[code-FIRST_COURSE].listAttend();
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		return this.student[studentID-FIRST_STUDENT].studyPlan();
	}

}
