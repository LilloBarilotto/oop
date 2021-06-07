package delivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Delivery {
	
	Set<String> categories = new TreeSet<String>();
	Map<String,Restaurant> restaurants = new TreeMap<String,Restaurant>();
	
	Map<Integer, Order> orders= new TreeMap<Integer, Order>();
	Integer numberOrders=0;
	
	// R1
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(categories.add(category)==false) {
			throw new DeliveryException();
		}
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return categories.stream().collect(Collectors.toList());
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		
		if(categories.contains(category)==false) {
			throw new DeliveryException();
		}
		
		restaurants.put(name, new Restaurant(name, category));
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
        return    restaurants.values().stream()
        				.filter(r -> r.category.equals(category))
        				.map(r -> r.name)
        				.collect(Collectors.toList());
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		
		if(restaurants.get(restaurantName).dishes.containsKey(name)) {
			throw new DeliveryException();
		}
		
		restaurants.get(restaurantName).dishes.put(name, price);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String, List<String>> xxx =	
				restaurants.values().stream()
				.collect(Collectors.toMap(
							r -> r.name,
							r -> r.dishes.entrySet().stream().filter( e -> e.getValue()>=minPrice && e.getValue()<=maxPrice).map(x -> x.getKey()).collect(Collectors.toList())
						)
				);
		
		return xxx.entrySet().stream().filter(x -> x.getValue().size()!=0)
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		
		
		if(restaurants.containsKey(restaurantName)==false) {
			return new ArrayList<String>();
		}
		
		return restaurants.get(restaurantName).dishes.keySet().stream()
			.sorted()
			.collect(Collectors.toList());
		
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
        
        List<Restaurant> restByCateg = restaurants.values().stream()
        		.filter( r-> r.category.equals(category))
        		.collect(Collectors.toList());
        
        if( restByCateg.size()==0) {
			return new ArrayList<String>(); 
        }
        
        return restByCateg.stream().flatMap( r-> r.dishes.keySet().stream())
        			.collect(Collectors.toList());
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
	    
		numberOrders++;
		orders.put(numberOrders, new Order(numberOrders, dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance));
		
		return numberOrders;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
        List<Order> ordersNotAssigned = 
        		orders.values().stream().
        		filter(x -> x.deliveryTime==deliveryTime && x.deliveryDistance<=maxDistance && x.assigned==0)
        		.sorted(Comparator.comparing(x -> x.orderNumber))
        		.collect(Collectors.toList());
        
        int count=0;
        List<Integer> res = new ArrayList<Integer>();
        
        
        for(Order order : ordersNotAssigned) {
        	order.assigned=1;
        	res.add(order.orderNumber);
        	count++;
        	
        	if(count==maxOrders) {
        		break;
        	}
        }

        return res;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		
		 List<Order> ordersNotAssigned = 
	        		orders.values().stream().filter(x -> x.assigned==0)
	        		.collect(Collectors.toList());
		
        return ordersNotAssigned.size();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
	
		if(rating>=0 && rating<=5) {
			restaurants.get(restaurantName).ratings.add(rating);
		}
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
		
		Comparator<Restaurant> RestaurantAverageComparator
	      = Comparator.comparingDouble( (Restaurant x) -> x.ratings.stream().mapToInt(Integer::intValue).average().getAsDouble()).reversed();
		
        return restaurants.values().stream()
        		.filter(x -> x.ratings.size()!=0)
        		.sorted(RestaurantAverageComparator)
        		.map( x -> x.name)
        		.collect(Collectors.toList());
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
			Map<String, Long> categ= 
					orders.values().stream()
					.collect(Collectors.groupingBy(
							(Order o) -> restaurants.get(o.restaurantName).category,
							Collectors.counting()));
			
			for(String category : categories) {
				if(!categ.containsKey(category)) {
					categ.put(category, (long) 0);
				}
			}
						
        return categ.entrySet().stream().
        	    sorted(Entry.comparingByValue()).
        	    collect(Collectors.toMap(Entry::getKey, Entry::getValue,
        	                             (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
			
		return	restaurantsAverageRating().get(0);
	}
}
