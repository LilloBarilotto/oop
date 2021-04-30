package diet;
import diet.Order.OrderStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	private final String name;
	private final Food food;
	private String[] hour;
	
	private ArrayList<Order> order;
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name=name;
		this.food=food;
		this.order= new ArrayList<Order>();
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		hour=  hm.clone();
		Arrays.sort(hour);
	}
	
	public Menu getMenu(String name) {
		return food.getMenu(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		return food.createMenu(name);
	}
	
	public void addOrder(Order o) {
		order.add(o);
	}
	
	public String[] getHours() {
		return this.hour;
	}
	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuffer str= new StringBuffer();
		Collections.sort(order, Comparator.comparing(Order::toString));
		
		for(Order o: order) {
			OrderStatus stat= o.getStatus();
			
			if( stat== status){
				str.append(o.toString());
			}
		}
		
		return str.toString();
	}
}
