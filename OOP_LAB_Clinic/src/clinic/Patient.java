package clinic;

public class Patient {
	private String first;
	private String last;
	private String ssn;
	private Doctor doctor;

	
	public Patient(String first, String last, String ssn) {
		this.first=first;
		this.last=last;
		this.ssn=ssn;
	}


	public String getFirst() {
		return first;
	}


	public String getLast() {
		return last;
	}


	public String getSsn() {
		return ssn;
	}
	
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	
	public void setDoctor(Doctor doctor) {
		this.doctor=doctor;
	}
	
	
	@Override
	public String toString() {
		return "" + last  + " " +  first + " (" + ssn + ")";
	}



}
