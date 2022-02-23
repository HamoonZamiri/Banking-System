
public class Savings {
	private String savingsBalance;
	private Customer c;
	public Savings (String savingsBalance) {
		this.savingsBalance = savingsBalance;
	}
	
	public Savings (Customer c) {
		this.c = c;
	}
	

	public String getSavingsBalance() {
		return savingsBalance;
	}

	public void setSavingsBalance(String savingsBalance) {
		this.savingsBalance = savingsBalance;
	}
	
	public String toString () {
		return savingsBalance;
	}
}
