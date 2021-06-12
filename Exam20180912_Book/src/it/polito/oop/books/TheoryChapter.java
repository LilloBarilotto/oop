package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {

	private String title;
	private Integer numPages;
	private String text;
	private List<Topic> topics;
	
	public TheoryChapter(String title, int numPages, String text) {
		this.title=title;
		this.numPages=numPages;
		this.text=text;
		this.topics= new ArrayList<Topic>();
	}
	
    public String getText() {
		return this.text;
	}

    public void setText(String newText) {
    	this.text=newText;
    }


	public List<Topic> getTopics() {
        return this.topics.stream().sorted(Comparator.comparing(x -> x.getKeyword())).collect(Collectors.toList());
	}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
    	this.title=newTitle;
    }

    public int getNumPages() {
        return this.numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages=newPages;
    }
    
    public void addTopic(Topic topic) {
    	
    	if(!topics.contains(topic)) {
	    	topics.add(topic);
	    	
	    	List<Topic> sub = topic.getSubTopics();
	    	for (Topic t : sub) {
	    		addTopic(t);
	    	}
    	}
    }
    
}
