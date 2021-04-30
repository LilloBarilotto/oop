package diet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
	private User user;
	private Restaurant restaurant;
	private String hourWish;
	private String hourDelivery;
	
	private OrderStatus orderStatus;
	private PaymentMethod paymentMethod;
	
	/*	FIRST TRY
		private ArrayList<Menu> menuList;
		private ArrayList<Integer> quantityList;
	*/
	
	private TreeMap<Menu,Integer> menuquantityList;
	
	public Order(User user, Restaurant restaurant, String hourWish) {
		this.user=user;
		this.restaurant=restaurant;
		this.hourWish=hourWish;
		
		this.orderStatus= OrderStatus.ORDERED;
		this.paymentMethod= PaymentMethod.CASH;
	
	/*	FIRST TRY
		menuList= new ArrayList<Menu>();
		quantityList = new ArrayList<Integer>();
	*/
		menuquantityList = new TreeMap<Menu, Integer>(Comparator.comparing(Menu::getName));
			
		String[] hour = restaurant.getHours();
		
		int i, oki;
		for(i=0, oki=0; i<hour.length && hourWish.compareTo(hour[i])>=0; i=i+2) {
			if( hourWish.compareTo(hour[i+1])<0) {
				hourDelivery=hourWish;
				oki=1;
				break;
			}
		}
		
		if(oki==0) {
			hourDelivery=hour[(i)%hour.length];
		}
		
	}
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
		
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.paymentMethod=method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.orderStatus=newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return this.orderStatus;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		Menu m= restaurant.getMenu(menu);
		
		if(menuquantityList.containsKey(m)) {
			menuquantityList.replace(m, quantity);
		}
		else {
			menuquantityList.put(m, quantity);
		}
		
		/*FIRST TRY
		if(menuList.contains(m)) {
			quantityList.set(menuList.indexOf(m), quantity);
		}
		else {
			menuList.add(m);
			quantityList.add(quantity);
		}
		*/
		
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuffer str= new StringBuffer();
		
		str.append(restaurant.getName());
		str.append(", ");
		str.append(user);
		str.append(" : (" + hourDelivery + "):\n");
		
		/*FIRST TRY
		Iterator<Menu> ne = menuList.iterator();
		Iterator<Integer> d = quantityList.iterator();
		
		while(ne.hasNext() && d.hasNext()){
			
			Menu menu= ne.next();
			Integer quantity =  d.next();
			
			str.append("\t"+ menu.getName()+ "->"+ quantity +"\n");
		}
		*/
		
		for(Map.Entry<Menu,Integer> entry : menuquantityList.entrySet()) {
			Menu menu= entry.getKey();
			int quantity= entry.getValue();
			
			str.append("\t"+ menu.getName()+ "->"+ quantity +"\n");
		}
		
		return str.toString();
	}	
}