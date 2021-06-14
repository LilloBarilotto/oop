package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Book {
	Map<String, Topic> topics= new TreeMap<String, Topic>();
	Map<String, Question> questions  = new TreeMap<String, Question>();
	Map<String, TheoryChapter> theoryChapters = new TreeMap<String, TheoryChapter>();
	Map<String, ExerciseChapter> exerciseChapters = new TreeMap<String, ExerciseChapter>();
	List<Assignment> assignments = new ArrayList<Assignment>();

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		
		if(keyword==null || keyword.length()==0 || keyword.equals("")) {
			throw new BookException();
		}
		
		if(!topics.containsKey(keyword)) {
			topics.put(keyword, new Topic(keyword));
		}
		
		
		return topics.get(keyword);
	}

	public Question createQuestion(String question, Topic mainTopic) {
        
		questions.put(question, new Question(question, mainTopic));
		
		return questions.get(question);
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        theoryChapters.put(title, new TheoryChapter(title, numPages, text));
		
		return theoryChapters.get(title);
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		exerciseChapters.put(title, new ExerciseChapter(title, numPages));
		
		return exerciseChapters.get(title);
	}

	public List<Topic> getAllTopics() {
		List<Topic> allTopicOfTheory =
				theoryChapters.values().stream().flatMap(x -> x.getTopics().stream())
					.distinct().collect(Collectors.toList());
		
		List<Topic> allTopicOfExercise =
				exerciseChapters.values().stream().flatMap(x -> x.getTopics().stream())
					.distinct().collect(Collectors.toList());
		
		List<Topic> allTopic = new ArrayList<Topic>();
		
		allTopic.addAll(allTopicOfTheory);
		allTopic.addAll(allTopicOfExercise);
		
		
        return allTopic.stream().distinct().sorted(Comparator.comparing(x -> x.getKeyword()))
        		.collect(Collectors.toList());
	}

	public boolean checkTopics() {
		List<Topic> allTopicOfExercise =
				exerciseChapters.values().stream().flatMap(x -> x.getTopics().stream())
					.distinct().collect(Collectors.toList());

		List<Topic> allTopicOfTheory =
				theoryChapters.values().stream().flatMap(x -> x.getTopics().stream())
					.distinct().collect(Collectors.toList());
		
        return allTopicOfTheory.containsAll(allTopicOfExercise);
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        
		Assignment x = new Assignment(ID, chapter);
		assignments.add(x);
		
		return x;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return questions.values().stream()
        		.collect(Collectors.groupingBy(x -> x.numAnswers(), Collectors.toList()));
    }
}
