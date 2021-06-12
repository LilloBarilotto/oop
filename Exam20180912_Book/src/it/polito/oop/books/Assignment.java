package it.polito.oop.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Assignment {
	
	private String ID;
	private ExerciseChapter exerciseChapter;
	private Map<Question, List<String>> questionsAndAnswers;
	private Map<Question, Double> questionsAndResponse;
	
	public Assignment(String ID, ExerciseChapter exerciseChapter) {
		this.ID=ID;
		this.exerciseChapter=exerciseChapter;
		this.questionsAndAnswers= new HashMap<Question,List<String>>();
		this.questionsAndResponse= new HashMap<Question, Double>();
	}
	
    public String getID() {
        return this.ID;
    }

    public ExerciseChapter getChapter() {
        return this.exerciseChapter;
    }

    public double addResponse(Question q,List<String> answers) {
        questionsAndAnswers.put(q, answers);
        
        long N = q.numAnswers();
        long FP = answers.stream().filter(x -> q.getIncorrectAnswers().contains(x))
        			.count();
        long FN = q.getCorrectAnswers().stream().filter(x -> !answers.contains(x))
        			.count();
        
        double res  = (double)(N - FP - FN)/N;
        
        questionsAndResponse.put(q, res);
        
    	return res;
    }
    
    public double totalScore() {
    	
        return questionsAndResponse.values().stream().collect(Collectors.summingDouble(x-> x));
    }

}
