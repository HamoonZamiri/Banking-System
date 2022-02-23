//basic class with getters and setters for customer
public class Chequing {
	private String chequingBalance;
	
	public Chequing (String chequingBalance) {
		this.chequingBalance = chequingBalance;
	}
	
	public String getChequingBalance() {
		return chequingBalance;
	}

	public void setChequingBalance(String chequingBalance) {
		this.chequingBalance = chequingBalance;
	}
	
	public String toString () {
		return chequingBalance;
	}
}
 