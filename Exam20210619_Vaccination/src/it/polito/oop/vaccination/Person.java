package it.polito.oop.vaccination;

public class Person {
	
	private String ssn;
	private String last;
	private String first;
	private Integer yearBirth;
	
	
	public Person(String ssn, String last, String first, Integer yearBirth) {
		this.ssn = ssn;
		this.last = last;
		this.first = first;
		this.yearBirth = yearBirth;
	}


	public String getSsn() {
		return ssn;
	}


	public void setSsn(String ssn) {
		this.ssn = ssn;
	}


	public String getLast() {
		return last;
	}


	public void setLast(String last) {
		this.last = last;
	}


	public String getFirst() {
		return first;
	}


	public void setFirst(String first) {
		this.first = first;
	}


	public Integer getYearBirth() {
		return yearBirth;
	}


	public void setYearBirth(Integer yearBirth) {
		this.yearBirth = yearBirth;
	}
	
	

	
	
}
