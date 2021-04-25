package diet;

import java.util.Collection;
import java.util.TreeMap;

public class RecipeDirectory {

	private static RecipeDirectory instance;
	private TreeMap<String,NutritionalElement> directory;
	
	private RecipeDirectory() {
		directory = new TreeMap<String,NutritionalElement>();
	}
	
	static public RecipeDirectory getInstance() {
		if(instance == null)
			instance = new RecipeDirectory();
		
		return instance;
	}
	
	public boolean addRecipe(Recipe r) {
		if(directory.containsKey(r.getName())) {
			return false;
		} else {
			directory.put(r.getName(), r);
			return true;
		}
	}
	
	public Collection<NutritionalElement> recipes(){
		return directory.values();
	}
	
	public Recipe getRecipe(String name) {
		return (Recipe) directory.get(name);
	}

}
