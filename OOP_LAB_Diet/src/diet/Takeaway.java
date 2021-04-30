package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	private TreeMap<String, Restaurant> restaurant = new TreeMap<>();
	private List<User> user = new ArrayList<User>();
		
	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		restaurant.put(r.getName(), r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		Collection<Restaurant> arrayRestaurant= restaurant.values();
		Collection<String> arrayStringRestaurant= new ArrayList<>();
		
		for(Restaurant r : arrayRestaurant) {
			arrayStringRestaurant.add(r.getName());
		}
		
		return arrayStringRestaurant;
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u= new User(firstName, lastName, email, phoneNumber);
		user.add(u);
		
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		Collections.sort(user, Comparator.comparing(User::getLastName).thenComparing(User::getFirstName));
		return user;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Restaurant r = restaurant.get(restaurantName);
		StringBuffer str= new StringBuffer();
		
		str.append(String.format("%02d", h) + ":" + String.format("%02d", m));
		
		Order o= new Order(user, r, str.toString());
		r.addOrder(o);
		
		return o;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		Collection<Restaurant> openedRestaurant= new ArrayList<Restaurant>();
		
		for(Map.Entry<String,Restaurant> entry : restaurant.entrySet()) {
			
			Restaurant r= entry.getValue();
			String[] hour= r.getHours();
			
			for(int i=0; i< hour.length; i=i+2) {
				
				if(time.compareTo(hour[i])>=0 && time.compareTo(hour[i+1])<0) {
					openedRestaurant.add(r);
					break;
				}
			}
		}
		
		return openedRestaurant;
	}

	
	
}
