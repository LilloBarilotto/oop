package diet;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	  private final String name;
	  private double calories;
	  private double proteins;
	  private double carbs;
	  private double fat;
	  
	  private LinkedList<NutritionalElement> allElement;
	  private LinkedList<Double> allElementQuantity;
	  
	  private final TreeMap<String,NutritionalElement> recipeDirectory;
	  private final TreeMap<String,NutritionalElement> productDirectory;
	  
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	  
	public Menu(String name, TreeMap<String,NutritionalElement> rDir,  TreeMap<String,NutritionalElement> pDir) {
		this.name=name;
		
		this.calories=0;
		this.proteins=0;
		this.carbs=0;
		this.fat=0;
		
		allElement = new LinkedList<NutritionalElement>();
		allElementQuantity= new LinkedList<Double>();
		
		recipeDirectory = rDir;
		productDirectory = pDir;
	}
	
	
	public Menu addRecipe(String recipe, double quantity) {
		NutritionalElement r = recipeDirectory.get(recipe);
		
		allElement.add(r);
		allElementQuantity.add(quantity);
		
		this.calories+=	( r.getCalories() *quantity/100);
		this.proteins+=	( r.getProteins()*quantity/100);
		this.carbs+=	( r.getCarbs()*quantity/100);
		this.fat+=		( r.getFat()*quantity/100);
		
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		NutritionalElement p = productDirectory.get(product);
		
		allElement.add(p);
		allElementQuantity.add(0.0);
		
		this.calories+=p.getCalories();
		this.proteins+=p.getProteins();
		this.carbs+=p.getCarbs();
		this.fat+=p.getFat();
		
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return this.calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return this.proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return this.carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return this.fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
