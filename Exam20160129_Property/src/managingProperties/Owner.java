package managingProperties;

import java.util.ArrayList;
import java.util.List;

public class Owner {
	public String id;
	public List<String> apartments;
	
	public Owner(String id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.apartments= new ArrayList<String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getApartments() {
		return apartments;
	}

	public void setApartments(List<String> apartments) {
		this.apartments = apartments;
	}
	

}
