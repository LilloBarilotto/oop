package discounts;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Discounts {
	private Map<Integer, String> cards= new TreeMap<Integer, String>();
	private Map<String, Product> products = new TreeMap<String, Product>();
	private Map<String, Integer> discounts = new TreeMap<String, Integer>();
	private Map<Integer, Purchase> purchases = new TreeMap<Integer, Purchase>();
	
	//R1
	public int issueCard(String name) {
		
		int x = cards.size() + 1;
		cards.put(x, name);
		
	    return x;
	}
	
    public String cardHolder(int cardN) {
        return cards.get(cardN);
    }
    

	public int nOfCards() {
	       return cards.size();
	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {
		
		if(products.containsKey(productId)) {
			throw new DiscountsException();
		}
		
		products.put(productId, new Product(categoryId, productId, price));
	}
	
	public double getPrice(String productId) 
			throws DiscountsException {
		
		if(!products.containsKey(productId)) {
			throw new DiscountsException();
		}
		
        return products.get(productId).getPrice();
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
         OptionalDouble res =
        		 products.values().stream().filter(x -> x.getCategoryId().equals(categoryId))
        		.mapToDouble(x -> x.getPrice()).average();
	
         if(res.isEmpty()) {
        	 throw new DiscountsException();
         }
         
         return  (int)res.getAsDouble();
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		
		long counter = products.values().stream().filter(x -> x.getCategoryId().equals(categoryId)).count();
		if(counter== 0.0 || percentage<0 || percentage>50) {
       	 	throw new DiscountsException();
		}
		
		discounts.put(categoryId, percentage);
	}

	public int getDiscount(String categoryId) {
        if(discounts.containsKey(categoryId)) {
        	return discounts.get(categoryId);
        }
        return 0;
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
		
		for(String item : items) {
			String[] it = item.split(":");
			
			if(!products.containsKey(it[0])) {
				throw new DiscountsException();
			};
		}
		
		Map<String, Integer> p = new TreeMap<String, Integer>();
		for(String item : items) {
			String[] it = item.split(":");
			
			p.put(it[0], Integer.parseInt(it[1]));
			}
		
		Integer x  = purchases.size()+1;
		
		
		if(cardId==0) {
			purchases.put(x, new Purchase(x, null, p));

		}
		else {
			purchases.put(x, new Purchase(x, cardId, p));
		}
		
		
        return x;
	}
	
	public int addPurchase(String... items) throws DiscountsException {
        return addPurchase(0, items);
	}
	
	public double getAmount(int purchaseCode) {
		Purchase p = purchases.get(purchaseCode);
		
		double res = p.getProducts().entrySet().stream()
				.mapToDouble(x -> x.getValue()*( products.get(x.getKey()).getPrice() ) ).sum();
		
		double discount = getDiscount(purchaseCode);
		
        return (res - discount);
	}
	
	public double getDiscount(int purchaseCode)  {
		
		Purchase p = purchases.get(purchaseCode);
		
		if(p.getCardId()==null) {
			return  0.0;
		}
		
		double discount = p.getProducts().entrySet().stream()
				.mapToDouble(x -> x.getValue()*( products.get(x.getKey()).getPrice() )
								*( discounts.get(products.get(x.getKey()).getCategoryId()   )==null?0:discounts.get(products.get(x.getKey()).getCategoryId())  )/100).sum();
		
        return discount;
	}
	
	public int getNofUnits(int purchaseCode) {
		
		Purchase p = purchases.get(purchaseCode);
		
        return p.getProducts().values().stream().mapToInt(x -> x).sum();
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
        
		Stream<Entry<String, Integer>>  xxx =purchases.values().stream().flatMap(e -> e.getProducts().entrySet().stream());
		
		Map<String, Integer> yyy =
				xxx.collect(Collectors.groupingBy(
				e -> e.getKey(),
				Collectors.summingInt(e -> e.getValue())));
		
		
		return yyy.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).collect(
				Collectors.groupingBy(
						e -> e.getValue(),
						() -> new TreeMap<Integer, List<String>>(),
						Collectors.mapping(e -> e.getKey(), Collectors.toList())
						)
				);
		
	}
	
	public SortedMap<Integer, Integer> totalPurchasePerCard() {
		Map<Integer, Double> xxx =
				purchases.values().stream().filter(x -> x.getCardId()!=null).collect(
        		Collectors.groupingBy(
        				x ->  x.getCardId(),
        				TreeMap::new,
        				Collectors.summingDouble( x-> getAmount(x.getPurchaseCode())) 
        				)
        		);
       
       SortedMap<Integer, Integer> res = new TreeMap<Integer, Integer>();
		  xxx.entrySet().forEach(e -> res.put(e.getKey(), (int)(double)e.getValue()));
		  
		  
       return res;
        
	}
	
	public int totalPurchaseWithoutCard() {
        return (int)purchases.values().stream().
        		filter(x -> x.getCardId()==null).mapToDouble(x -> getAmount(x.getPurchaseCode())).sum();
	}
	
	public SortedMap<Integer, Integer> totalDiscountPerCard() {
		  Map<Integer, Double> xxx =
				  purchases.values().stream().filter(x -> x.getCardId()!=null).collect(
	        		Collectors.groupingBy(
	        				x -> x.getCardId(),
	        				() -> new TreeMap<Integer, Double>(),
	        				Collectors.summingDouble(x -> getDiscount(x.getPurchaseCode())) 
	        				)
	        		);
		  
		  SortedMap<Integer, Integer> res = new TreeMap<Integer, Integer>();
		  xxx.entrySet().forEach(e -> res.put(e.getKey(), (int)(double)e.getValue()));
		  		  
		  return res ;
	}

}
