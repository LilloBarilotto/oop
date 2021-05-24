package clinic;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
	private String first;
	private String last;
	private String ssn;
	private int docID;
	private String specialization;
	private List<Patient> patients;
	
	public Doctor(String first, String last, String ssn, int docID, String specialization){
		this.first=first;
		this.last=last;
		this.ssn=ssn;
		this.specialization=specialization;
		this.docID=docID;
		this.patients= new ArrayList<Patient>();
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


	public int getDocID() {
		return docID;
	}


	public String getSpecialization() {
		return specialization;
	}

	
	public String getSurnameAndName () {
		return last + " " + first;
	}

	@Override
	public String toString() {
		return "" + last  + " " +  first + " (" + ssn + ") [" + docID
				+ "]: " + specialization;
	}
	
	
	public void addPatient(Patient p) {
		patients.add(p);
	}
	
	public List<Patient> getPatients(){
		List<Patient> x = new ArrayList<Patient>();
		x.addAll(patients);
		return x;
	}
	
	
}
