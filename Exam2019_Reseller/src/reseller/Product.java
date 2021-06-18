package reseller;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class Product {
	
	private String name;
	private Integer unit;
	
	private List<Order> customerOrders;
	private List<Order> supplierOrders;
	private List<Supplier> suppliers;
	
	
	public Product(String name, Integer unit) {
		this.name=name;
		this.unit=unit;
		
		this.customerOrders= new ArrayList<Order>();
		this.supplierOrders= new ArrayList<Order>();
	}

	public String getName() {
		return name;
	}

	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit=unit;
	}

	public List<Order> getCustomerOrders() {
		return customerOrders;
	}

	public List<Order> getSupplierOrders() {
		return supplierOrders;
	}
	
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	
	
}
