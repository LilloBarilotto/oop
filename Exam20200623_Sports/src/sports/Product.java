package sports;

import java.util.ArrayList;
import java.util.List;

public class Product {

	public String name;
	public String category;
	public String activity;
	public List<Rating> ratings;
	
	public Product(String name, String category, String activity) {
		this.name=name;
		this.category=category;
		this.activity=activity;
		this.ratings= new ArrayList<Rating>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	

}
