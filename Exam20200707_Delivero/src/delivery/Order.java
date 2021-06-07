package delivery;

import java.util.Map;
import java.util.TreeMap;

public class Order {

	public Integer orderNumber;
	public String customerName;
	String restaurantName;
	int deliveryTime;
	int deliveryDistance;
	Map<String, Integer> dishes;
	int assigned;
	
	public Order(Integer orderNumber,String dishName[], int quantity[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance ) {
		this.orderNumber=orderNumber;
		this.customerName=customerName;
		this.restaurantName= restaurantName;
		this.deliveryDistance=deliveryDistance;
		this.deliveryTime= deliveryTime;
		this.assigned=0;
		
		dishes= new TreeMap<String, Integer>();
		
		int x= 0;
		
		for(String dish : dishName) {
			dishes.put(dish, quantity[x]);
			x++;
		}
	}
}
