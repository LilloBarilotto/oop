package discounts;

public class Product {

	private String categoryId;
	private String productId;
	private double price;
	
	public Product(String categoryId, String productId, double price) {
		this.categoryId = categoryId;
		this.productId = productId;
		this.price = price;
	}
	
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
