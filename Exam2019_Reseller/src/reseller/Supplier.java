package reseller;

import java.util.ArrayList;
import java.util.List;

public class Supplier {

	private String name;
	private List<Product> products;
	
	public Supplier(String name) {
		this.name=name;
		this.products= new ArrayList<Product>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	
	
}
