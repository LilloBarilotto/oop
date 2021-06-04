package managingProperties;

import java.util.ArrayList;

public class Building {
	private String id;
	private Integer numberApartments;
	private Owner[] owners;
	
	
	public Building(String id, Integer numberApartments) {
		this.id=id;
		this.numberApartments= numberApartments;
		this.owners= new Owner[numberApartments];
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getNumberApartments() {
		return numberApartments;
	}


	public void setNumberApartments(Integer numberApartments) {
		this.numberApartments = numberApartments;
	}


	public Owner[] getOwners() {
		return owners;
	}


	public void setOwners(Owner[] owners) {
		this.owners = owners;
	}
	
	
	
}
