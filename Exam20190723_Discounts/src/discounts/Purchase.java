package discounts;

import java.util.Map;

public class Purchase {
	
	private Integer purchaseCode;
	private Integer cardId;
	private Map<String, Integer> products;
	
	public Purchase(Integer purchaseCode, Integer cardId, Map<String, Integer> products) {
		this.purchaseCode = purchaseCode;
		this.cardId = cardId;
		this.products = products;
		
	}

	public Integer getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(Integer purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Map<String, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}
		
}
