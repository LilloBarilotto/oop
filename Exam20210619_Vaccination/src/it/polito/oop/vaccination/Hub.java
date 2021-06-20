package it.polito.oop.vaccination;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Hub {

	private String name;
	private int countDoctors;
	private int countNurse;
	private int other;

	public Hub(String name) {
		this.name = name;
		this.countDoctors=-1;
		this.countNurse=-1;
		this.other=-1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountDoctors() {
		return countDoctors;
	}

	public void setCountDoctors(int countDoctors) {
		this.countDoctors = countDoctors;
	}

	public int getCountNurse() {
		return countNurse;
	}

	public void setCountNurse(int countNurse) {
		this.countNurse = countNurse;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}
	
	
	public int getEstimateCapacity() {
		int nd = countDoctors*10;
		int nn = countNurse *12;
		int no = countNurse *20;
		
		int res=Integer.min(nn, nd);
			
		res= Integer.min(no, res);
		
		return res;
	}
	
	
	
}
