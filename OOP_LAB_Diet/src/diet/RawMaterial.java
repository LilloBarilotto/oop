package diet;

public class RawMaterial implements NutritionalElement {
	  private final String name;
	  private final double calories;
	  private final double proteins;
	  private final double carbs;
	  private final double fat;
	  
	public RawMaterial(String name,
			  double calories,
			  double proteins,
			  double carbs,
			  double fat){
		this.name=name;
		this.calories=calories;
		this.proteins=proteins;
		this.carbs=carbs;
		this.fat=fat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return this.calories;
	}

	@Override
	public double getProteins() {
		return this.proteins;
	}

	@Override
	public double getCarbs() {
		return this.carbs;
	}

	@Override
	public double getFat() {
		return this.fat;
	}

	@Override
	public boolean per100g() {
		// TODO Auto-generated method stub
		//return true;
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
