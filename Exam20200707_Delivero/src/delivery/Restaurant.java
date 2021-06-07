package delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Restaurant {
	
	public String name;
	public String category;
	public Map<String, Float> dishes;
	
	List<Integer> ratings;
	
	
	public Restaurant(String name, String category) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.category=category;
		this.dishes= new TreeMap<String, Float>();
		this.ratings= new ArrayList<Integer>();
	}

}
