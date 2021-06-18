package reseller;
import java.util.*;
import java.util.stream.Collectors;

import reseller.Order.State;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class Reseller {
	Map<String, Product> products = new TreeMap<String, Product>();
	Map<String, Supplier> suppliers= new TreeMap<String, Supplier>();
	List<Order> customerOrders = new ArrayList<Order>();
	List<Order> supplierOrders = new ArrayList<Order>();

	public String getCOrders(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getStock(String productName) {
		Product p= products.get(productName);
		
		if(p==null)
			return null;
		
		return p.getUnit();
	}

	public int  getCustomerNeeds(String productName) {
		// TODO Auto-generated method stub
		Product p= products.get(productName);
		
		List<Integer> xxx = customerOrders.stream().filter(x -> x.getProduct()==p && x.getState()==State.p)
		.map( x -> x.getN())
		.collect(Collectors.toList());
		
		int n=0;
		
		for(Integer x : xxx) {
			n= n+ x;
		}
		
		return n;
	}

	public int getExpectedSupplies(String productName) {
		// TODO Auto-generated method stub
Product p= products.get(productName);
		
		List<Integer> xxx = supplierOrders.stream().filter(x -> x.getProduct()==p && x.getState()==State.p)
		.map( x -> x.getN())
		.collect(Collectors.toList());
		
		int n=0;
		
		for(Integer x : xxx) {
			n= n+ x;
		}
		
		return n;
	}

	public void setStock(int i, String string, String string2, String string3, String string4, String string5,
			String string6) {
		
		products.put(string, new Product(string, i));
		products.put(string2, new Product(string2, i));
		products.put(string3, new Product(string3, i));
		products.put(string4, new Product(string4, i));
		products.put(string5, new Product(string5, i));
		products.put(string6, new Product(string6, i));
		
	}

	public void addSupplier(String supplierName, String... productNames) {
		Supplier x;
		
		if(!suppliers.containsKey(supplierName)) {
			suppliers.put(supplierName, new Supplier(supplierName));
		}
		x=suppliers.get(supplierName);
		
		for(String productName : productNames) {
			x.getProducts().add(products.get(productName));
			products.get(productName).getSuppliers().add(x);
		}
		
	}

	public List<String> getProductsWithoutSuppliers() {
		
		return products.values().stream()
				.filter(x -> x.getSuppliers().size()==0)
				.map(x -> x.getName())
				.collect(Collectors.toList());
	}

	public List<String> getSupplierNames(String string) {
		Product p= products.get(string);
		
		return suppliers.values().stream()
				.filter(x -> x.getProducts().contains(p))
				.map(x -> x.getName())
				.collect(Collectors.toList());
	}

	public void enterCOrder(String string, String string2, int i) {
		// TODO Auto-generated method stub
		
		Order x= new Order(string, products.get(string2), i);
		
		if( suppliers.containsKey(string)) {
			supplierOrders.add(x);
		}else {
			customerOrders.add(x);
		}
		
		
		if(getExpectedSupplies(string2)==0) {
			if(products.get(string2).getUnit()>=i) {
				x.setState(State.f);
				products.get(string2).setUnit(products.get(string2).getUnit() - i);
			}else {
				
			}
		}
		
		
		
			
	}

	public void delivery(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public SortedMap<String, Long> nOrdersPerCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	public SortedMap<String, Integer> ratioSOrdersCOrdersPerProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSOrders(String productName) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
