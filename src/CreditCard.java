//basic class with getters and setters for customer
public class CreditCard {
	private String creditCardBalance;
	
	public CreditCard (String creditCardBalance) {
		this.creditCardBalance = creditCardBalance;
	}
	
	public String getCreditCardBalance() {
		return creditCardBalance;
	}
	public void setCreditCardBalance(String creditCardBalance) {
		this.creditCardBalance = creditCardBalance;
	}
	
	public String toString () {
		return creditCardBalance;
	}
	

}
