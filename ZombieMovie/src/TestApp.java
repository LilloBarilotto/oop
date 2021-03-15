
public class TestApp {

	public static void main(String[] args) {
		Movie m1;
		m1 = new Movie();
		m1.watch();
		
		Movie m2= new Movie("CELAFAREMOOO", 2021, "Pazzo sgravo");
		m2.watch();
		
		Movie alias=m2;
		alias.move();
		alias.move(7);
		
		Movie m3= new Movie("World War Z", 2013, "Marc Foster");
		m3.runningZombies=true;
		m3.move(15);
	}
}
