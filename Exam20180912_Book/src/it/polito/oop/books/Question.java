package it.polito.oop.books;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Question{
	
	private String question;
	private Topic topic;
	Map<String, Boolean> answers; 
	
	public Question(String question, Topic topic) {
		this.question=question;
		this.topic=topic;
		this.answers= new TreeMap<String, Boolean>();
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public Topic getMainTopic() {
		return this.topic;
	}

	public void addAnswer(String answer, boolean correct) {
		answers.put(answer, correct);
	}
	
    @Override
    public String toString() {
        return String.format("%s (%s)", question, topic.getKeyword());
    }

	public long numAnswers() {
	    return answers.size();
	}

	public Set<String> getCorrectAnswers() {
		return answers.entrySet().stream().filter(x -> x.getValue()==true)
				.map(x -> x.getKey())
				.collect(Collectors.toSet());
	}

	public Set<String> getIncorrectAnswers() {
		return answers.entrySet().stream().filter(x -> x.getValue()==false)
				.map(x -> x.getKey())
				.collect(Collectors.toSet());
	}
	
	
	
}
