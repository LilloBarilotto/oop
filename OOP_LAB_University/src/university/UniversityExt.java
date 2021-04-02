package university;

import java.util.logging.Logger;

/**
 * This class is an extended version of the {@Link University} class.
 * 
 *
 */
public class UniversityExt extends University {
	public static final int N_STUD_PRIZE=3;
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}

	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		if(this.checkID(studentId) || this.checkCODE(courseID)) {
			System.out.println("Error with studentID or courseID");
			return;
		}
		
		if(grade<18) {
			System.out.println("Error wtf man come on minimum is 18");
			return;
		}
		
		super.student[studentId-FIRST_STUDENT].exam(grade, courseID);
		super.course[courseID-FIRST_COURSE].exam(grade, studentId);
		
		logger.info("Student "+ studentId +" took an exam in course "+ courseID +" with grade "+ grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		float res;
		res=this.student[studentId-FIRST_STUDENT].studentAvg();
		
		if(res==0)
			return "Student "+ studentId +" hasn't taken any exams";
			
		return "Student "+ studentId +" : "+ res;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		float res;
		String title;
		
		res=this.course[courseId-FIRST_COURSE].courseAvg();
		title= this.course[courseId-FIRST_COURSE].getTitle();
		
		if(res==0)
			return "No student has taken the exam in "+ title;
			
		return "The average for the course "+title+" is: "+res;
	}
	
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info of the best three students.
	 */
	public String topThreeStudents() {
		float[] score;
		Student[] studentPrize;
		String result="";
		
		int effectiveCourse;
			
		score=new float[N_STUD_PRIZE + 1];
		studentPrize= new Student[N_STUD_PRIZE + 1];
		
		for(int i=0; i< this.effectiveStudent; i++) {
			studentPrize[0]=this.student[i];
			effectiveCourse=studentPrize[0].getEffectiveCourse();
			
			if(effectiveCourse==0) {
				effectiveCourse=1;
			}
			
			score[0]= studentPrize[0].studentAvg() + studentPrize[0].getEffectiveExam()*10/effectiveCourse;			
			
			for(int j=1; j<=N_STUD_PRIZE && (studentPrize[j]==null || score[j-1]>score[j]); j++) {
				Student tmp_stud=studentPrize[j-1];
				float tmp_score=score[j-1];
				
				studentPrize[j-1]=studentPrize[j];
				score[j-1]=score[j];
				
				score[j]=tmp_score;
				studentPrize[j]=tmp_stud;
			}
		}
		
		for(int i=N_STUD_PRIZE; i>0; i--) {
			if(score[i]!=0) {
				result+= studentPrize[i].getFirst() + " " + studentPrize[i].getLast() +" : "+ score[i]+"\n";
			}
		}
		
		return result;
	}
	
	
	
	//LOG(?)
	@Override
	public int enroll(String first, String last){
		int id =super.enroll(first, last);
		
		logger.info("New student enrolled: "+ this.student[id-FIRST_STUDENT]);
		return id;
	}
	
	@Override
	public int activate(String title, String teacher){
		int code = super.activate(title, teacher);
		
		logger.info("New course activated: "+this.course[code-FIRST_COURSE]);
		
		return code;
	}
	
	@Override
	public void register(int studentID, int courseCode){
		super.register(studentID, courseCode);
		
		logger.info("Student "+ studentID +" signed up for course "+ courseCode);
	}
}
