package diet;

/**
 * Represent a take-away system user
 *  
 */
public class User{
	private final String firstName;
	private final String lastName;
	private String email;
	private String phone;
	
	public User(String firstName, String lastName, String email, String phone) {
		this.firstName=firstName;
		this.lastName=lastName;	
		this.email=email;
		this.phone=phone;
	}
	
	/**
	 * get user's last name
	 * @return last name
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		this.email=email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		this.phone=phone;
	}
	
	@Override
	public String toString() {
		return this.getFirstName() + " "  + this.getLastName();
	}
	
}
