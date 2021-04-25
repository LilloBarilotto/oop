package diet;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	  private final String name;
	  private double calories;
	  private double proteins;
	  private double carbs;
	  private double fat;
	  private double grams; 
	  
	  private LinkedList<NutritionalElement> ingredients;
	  private LinkedList<Double> ingrGrams;
	
	  public Recipe(String name){
		this.name=name;
		this.calories=0;
		this.proteins=0;
		this.carbs=0;
		this.fat=0;
		this.grams=0;
		
		ingrGrams 	= new LinkedList<Double>();
		ingredients = new LinkedList<NutritionalElement>();
	}


	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement ingr = RawMaterialDirectory.getInstance().getRawMaterial(material);
		
		this.calories+=	( ingr.getCalories()*quantity/100);
		this.proteins+=	(ingr.getProteins()*quantity/100);
		this.carbs+=	(ingr.getCarbs()*quantity/100);
		this.fat+=		(ingr.getFat()*quantity/100);
		
		this.grams+=quantity;
		
		ingredients.add(ingr);
		ingrGrams.add(quantity);
		
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return this.calories*100/this.grams;
	}

	@Override
	public double getProteins() {
		return this.proteins*100/this.grams;
	}

	@Override
	public double getCarbs() {
		return this.carbs*100/this.grams;
	}

	@Override
	public double getFat() {
		return this.fat*100/this.grams;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuffer str= new StringBuffer();
		Iterator<NutritionalElement> ne = ingredients.iterator();
		Iterator<Double> d = ingrGrams.iterator();
		
		while(ne.hasNext() && d.hasNext()){
			
			NutritionalElement ingr= ne.next();
			Double quantity =  d.next();
			
			str.append(ingr.getName()+ " : "+ quantity +"\n");
		}
		
		return str.toString();
	}
}
