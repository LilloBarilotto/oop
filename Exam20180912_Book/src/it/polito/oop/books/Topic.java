package it.polito.oop.books;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Topic {
 private String keyword;
 private Map<String, Topic> subTopics;
	
 	public Topic (String keyword){
 		this.keyword=keyword;
 		this.subTopics = new TreeMap<String,Topic>();
 	}
 
	public String getKeyword() {
        return this.keyword;
	}
	
	@Override
	public String toString() {
	    return this.keyword;
	}

	public boolean addSubTopic(Topic topic) {
		if(subTopics.containsKey(topic.getKeyword())) {
			return false;
		}
		
		subTopics.put(topic.getKeyword(), topic);
		return true;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subTopics.values().stream()
        		.sorted(Comparator.comparing(x ->x.getKeyword()))
        		.collect(Collectors.toList());
	}
}
