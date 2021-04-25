package diet;

import java.util.Collection;
import java.util.TreeMap;

public class ProductDirectory {
	private static ProductDirectory instance;
	private TreeMap<String,NutritionalElement> directory;
	
	private ProductDirectory() {
		directory = new TreeMap<String,NutritionalElement>();
	}
	
	static public ProductDirectory getInstance() {
		if(instance == null)
			instance = new ProductDirectory();
		
		return instance;
	}
	
	public boolean addProduct(Product p) {
		if(directory.containsKey(p.getName())) {
			return false;
		} else {
			directory.put(p.getName(), p);
			return true;
		}
	}
	
	public Collection<NutritionalElement> products(){
		return directory.values();
	}
	
	public NutritionalElement getProduct(String name) {
		return directory.get(name);
	}

}
