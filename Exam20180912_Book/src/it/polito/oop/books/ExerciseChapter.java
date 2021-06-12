package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {
	private String title;
	private Integer numPages;
	private List<Question> questions;
	
	public ExerciseChapter(String title, int numPages) {
		this.title=title;
		this.numPages= numPages;
		this.questions= new ArrayList<Question>();
	}
	
    public List<Topic> getTopics() {
        return questions.stream().map(x -> x.getMainTopic())
        		.distinct()
        		.sorted(Comparator.comparing(x -> x.getKeyword()))
        		.collect(Collectors.toList());
	};
	

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

	public void addQuestion(Question question) {
		questions.add(question);
	}	
}
