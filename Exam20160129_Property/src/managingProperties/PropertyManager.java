package managingProperties;

import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyManager {
	
	Integer countRequest=0;
	
	Map<String, Building> buildings	= new TreeMap<String,Building>();
	Map<String, Owner> 	  owners	= new TreeMap<String,Owner>();
	Set<String> professions = new TreeSet<String>();
	Map<String, Professional> myProfessionals= new TreeMap<String, Professional>();
	Map<Integer,Request> requests = new TreeMap<Integer, Request>();
	
	
	/**
	 * Add a new building 
	 */
	public void addBuilding(String building, int n) throws PropertyException {
		
		if(buildings.containsKey(building) || n>100 || n<1) {
			throw new PropertyException();
		}
		
		buildings.put(building, new Building(building, n));		
	}

	public void addOwner(String owner, String... apartments) throws PropertyException {
		
		if(owners.containsKey(owner)) {
			throw new PropertyException();
		}
		
		owners.put(owner, new Owner(owner));
		Owner ow= owners.get(owner);
				
		for(String apartment : apartments) {
			String[] xx  = apartment.split(":");
			
			Building building = buildings.get(xx[0]);
			
			if(building==null) {
				throw new PropertyException();
			}
			
			Integer numberAp= Integer.parseInt(xx[1]);
			
			if(numberAp<1 || numberAp>building.getNumberApartments()) {
				throw new PropertyException();
			}
			
			Owner[] ownersOfBuilding=building.getOwners();
			
			if(ownersOfBuilding[numberAp-1]!=null) {
				throw new PropertyException();
			}
			
			ownersOfBuilding[numberAp-1]= ow;
			ow.getApartments().add(apartment);
		}
	}

	/**
	 * Returns a map ( number of apartments => list of buildings ) 
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		return	buildings.values().stream()
				.sorted(Comparator.comparing(Building::getId))
				.collect(Collectors.groupingBy(
						Building::getNumberApartments,
                        () -> new TreeMap<Integer,List<String>>(),
                        Collectors.mapping( Building::getId,Collectors.toList()))
						);
	}

	public void addProfessionals(String profession, String... professionals) throws PropertyException {
		if(professions.contains(profession)) {
			throw new PropertyException();
		}
		
		professions.add(profession);
		
		for(String professional : professionals) {
			if(myProfessionals.containsKey(professional)) {
				throw new PropertyException();
			}
			
			myProfessionals.put(professional, new Professional(professional, profession));
		}
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		return myProfessionals.values().stream()
				.sorted(Comparator.comparing(Professional::getProfession))
				.collect(Collectors.groupingBy(
								Professional::getProfession,
								TreeMap::new,
								Collectors.reducing(0, e -> 1, Integer::sum) 
							)
						);
	}

	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		
		String[] ap  = apartment.split(":");
		Building bd= buildings.get(ap[0]);
		Integer n = Integer.parseInt(ap[1]);
		Owner ow= owners.get(owner);
		
		if( ow==null || bd==null || !professions.contains(profession) || bd.getNumberApartments()<n || (bd.getOwners())[n-1]!=ow) {
			throw new PropertyException();
		}
		
		countRequest++;
		
		requests.put(countRequest, new Request(ow, apartment, profession, countRequest));
		
		return countRequest;
	}

	public void assign(int requestN, String professional) throws PropertyException {
		
		Request r = requests.get(requestN);
		Professional p = myProfessionals.get(professional);
		
		if(r==null || !r.getProfession().contentEquals(p.getProfession())  || !r.getState().equals(managingProperties.Request.State.pending) ) {
			throw new PropertyException();
		}
		
		r.setProfessional(p);
		r.setState(managingProperties.Request.State.assigned);
		
	}

	public List<Integer> getAssignedRequests() {
		return requests.values().stream().filter(x -> x.getState().equals(managingProperties.Request.State.assigned))
				.map( x -> x.getId())
				.sorted()
				.collect(Collectors.toList());
	}

	
	public void charge(int requestN, int amount) throws PropertyException {
		Request r = requests.get(requestN);
		
		if(r==null 
				|| !r.getState().equals(managingProperties.Request.State.assigned) 
				|| amount<0 || amount>1000) {
			
			throw new PropertyException();
		}
		
		r.setState(managingProperties.Request.State.completed);
		r.setCharge(amount);
		
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		
		return requests.values().stream().filter(x -> x.getState().equals(managingProperties.Request.State.completed))
				.map( x -> x.getId())
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		
		return requests.values().stream().filter(x -> x.getState().equals(managingProperties.Request.State.completed))
				.filter(x -> x.getCharge()!=0)
				.sorted(Comparator.comparing(x -> x.getOwner().getId()))
				.collect(Collectors.groupingBy(
						x -> x.getOwner().getId(), 
						TreeMap::new,
						Collectors.reducing(0, x -> x.getCharge(), Integer::sum)
						));
	}

	/**
	 * Returns the map ( building => ( profession => total expenses) ).
	 * Both buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		
		Stream<Request> xx = requests.values().stream().filter(x -> x.getState().equals(managingProperties.Request.State.completed))
				.filter(x -> x.getCharge()!=0)
				.sorted(Comparator.comparing(x -> x.getOwner().getId()));
	
		return xx.collect(Collectors.groupingBy(
						x -> (x.getApartment().split(":"))[0], 
						TreeMap::new,
						Collectors.groupingBy(
								x -> x.getProfessional().getId(), 
								TreeMap::new,
								Collectors.reducing(0, x -> x.getCharge(), Integer::sum)
								)
						));
	}

}
