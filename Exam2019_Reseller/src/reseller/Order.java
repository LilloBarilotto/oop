package reseller;

class Order {
	public enum State{
		p, f
	}
	
	private String customer;
	private Product product;
	private Integer n;

	private State state;
	
	public Order(String customer, Product product, Integer n) {
		this.customer=customer;
		this.product=product;
		this.n=n;
		
		this.state=State.p;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
