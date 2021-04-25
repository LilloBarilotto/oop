package diet;

import java.util.Collection;
import java.util.TreeMap;

final public class RawMaterialDirectory {
	private static RawMaterialDirectory instance;
	private TreeMap<String,NutritionalElement> directory;
	
	private RawMaterialDirectory() {
		directory = new TreeMap<String,NutritionalElement>();
	}
	
	static public RawMaterialDirectory getInstance() {
		if(instance == null)
			instance = new RawMaterialDirectory();
		
		return instance;
	}
	
	public boolean addRawMaterial(RawMaterial r) {
		if(directory.containsKey(r.getName())) {
			return false;
		} else {
			directory.put(r.getName(), r);
			return true;
		}
	}
	
	public Collection<NutritionalElement> rawMaterials(){
		return directory.values();
	}
	
	public NutritionalElement getRawMaterial(String name) {
		return directory.get(name);
	}

}