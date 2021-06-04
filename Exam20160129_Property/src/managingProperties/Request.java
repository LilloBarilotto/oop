package managingProperties;

public class Request {
	enum State{
		pending, assigned, completed
	}
	
	private Owner owner;
	private int id;
	private String apartment;
	private String profession;
	private Professional professional;
	private State state;
	private Integer charge;
	
	public Request(Owner owner, String apartment, String profession, Integer id) {
		this.owner=owner;
		this.apartment=apartment;
		this.profession=profession;
		this.state=State.pending;
		this.id=id;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Professional getProfessional() {
		return professional;
	}

	public void setProfessional(Professional professional) {
		this.professional = professional;
		this.state=State.assigned;
	}
	
	public State getState() {
		return this.state;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	
	
	
}
