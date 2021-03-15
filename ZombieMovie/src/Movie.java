
public class Movie {
	String title;
	int year;
	String director;
	boolean runningZombies = false;
	
	Movie() {
		// THE standard zombie movie
		year = -1;
		title = "**ERROR**";
		director = "**ERROR**";
	}

	Movie(String title, int year, String director) {
		// custom zombie movie
		this.title = title;
		this.year = year;
		this.director = director;	
	}

	Movie(String title, int year, String director, boolean runningZombies) {
		// custom zombie movie with explicit runningZombies
		this.title = title;
		this.year = year;
		this.director = director;	
		this.runningZombies = runningZombies;
	}

	void watch() {
		if(year < 0) {
			System.out.println("Can't see a non-existing movie!");
		} else {
			System.out.println("I'm watching \"" + title + "\" (" + year + ") by " + director);
		}
	}
	
	void move() {
		move(3);
	}

	void move(int speed) {
		if(speed <= 6) {
			System.out.println("Zombies in \"" + title + "\" are walking at " + speed + " km/h");
		} else if(speed <= 15 && runningZombies) {
			System.out.println("Zombies in \"" + title + "\" are running at " + speed + " km/h");
		} else {
			System.out.println("Yeuch: Zombies in \"" + title + "\" can't move so fast!");
		}
	}
}