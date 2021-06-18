package sports;
import java.util.*;
import java.util.stream.Collectors;

 
/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
	
	Set<String> activitiesSet = new TreeSet<String>();
	Map<String, List<String>> categories= new TreeMap<String, List<String>>();
	
	
	Map<String, Product> products = new TreeMap<String, Product>();
	
	
    //R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
    	
    	if(activities.length==0) {
    		throw new SportsException("No activity");
    	}
    	
    	for(String x : activities) {
    		activitiesSet.add(x);
    	}
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        return activitiesSet.stream().sorted().collect(Collectors.toList());
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    		
    	for(String x : linkedActivities) {
    		if(activitiesSet.contains(x)==false) {
    			throw new SportsException("Activity does not exist");
    		}
    	}
    	
    	List<String> l = new ArrayList<String>();
    	
    	categories.put(name, l);
    	
    	for(String x : linkedActivities) {
    		l.add(x);
    	}
    
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return categories.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
        return categories.entrySet().stream().filter(x -> x.getValue().contains(activity))
        		.map(x -> x.getKey())
        		.collect(Collectors.toList());
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	if(products.containsKey(name)) {
    		throw new SportsException("Duplicate name of product");
    	}
    	
    	products.put(name, new Product(name, categoryName, activityName));
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
        return products.values().stream().filter(x -> x.getCategory().equals(categoryName))
        		.map(x -> x.getName())
        		.sorted()
        		.collect(Collectors.toList());
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
        return products.values().stream().filter(x -> x.getActivity().equals(activityName))
        		.map(x -> x.getName())
        		.sorted()
        		.collect(Collectors.toList());
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
    	
    	List<String> l = new ArrayList<String>();
    	for(String x : categoryNames) {
    		l.add(x);
    	}
    	
        return products.values().stream().filter(x -> x.getActivity().equals(activityName) && l.contains(x.getCategory()))
        		.map(x -> x.getName())
        		.sorted()
        		.collect(Collectors.toList());
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    
    	if(numStars<0 || numStars>5) {
    		throw new SportsException("numStars is not correct");
    	}
    
    	products.get(productName).getRatings().add(new Rating(productName, userName, numStars, comment));
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
        return products.get(productName).getRatings().stream()
        		.sorted(Comparator.comparing(Rating::getNumStars).reversed())
        		.map(x -> String.format("%d : %s",x.getNumStars(), x.getComment()))
        		.collect(Collectors.toList());
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) { 			     
         return products.get(productName).getRatings().stream()
         		.mapToInt(x -> x.getNumStars())
         		.average().orElse(0);
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
       return products.values().stream().filter(x -> x.getRatings().size()!=0)
    		   .flatMapToInt(x -> x.getRatings().stream().mapToInt(e -> e.getNumStars()))
    		   .average().orElse(0);
    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
        
    	SortedMap<String, Double> xxx = new TreeMap<String, Double>();
    	
    	for(String x  : activitiesSet) {
    		Double res= averageStarsPerActivity(x);
    		
    		if(res!=-1) {
    			xxx.put(x, res);
    		}
    	}
    	
        return xxx;
    }
    
    //MY FUNCT EXT TO SOLVE A LOT OF PROBLEM BECAUSE I'M STUPID
    public double averageStarsPerActivity(String activity) {
    	
    	List<Product> yy = products.values().stream().filter(x -> x.getRatings().size()!=0)
        		.filter(x -> x.getActivity().equals(activity))
        		.collect(Collectors.toList());
    	
    	if(yy.size()==0) {
    		return -1;
    	}
    	
    	return products.values().stream().filter(x -> x.getRatings().size()!=0)
    		.filter(x -> x.getActivity().equals(activity))
    		.flatMapToInt( x-> x.getRatings().stream().mapToInt(e -> e.numStars))
    		.average().orElse(0);
    }
    

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
    	
        List<String> productsWithinRatings=
        		products.entrySet().stream()
        		.filter(x -> x.getValue().getRatings().size()!=0)
        		.map(x -> x.getKey())
        		.collect(Collectors.toList());
        
        return productsWithinRatings.stream()
        		.sorted()
        		.collect(Collectors.groupingBy(
        				x -> (Double)getStarsOfProduct(x),
        				() -> new TreeMap<Double, List<String>>(Collections.reverseOrder()),
        				Collectors.toList()
        				)
        		);
    }

}