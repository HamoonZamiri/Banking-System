import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Account {
	private Savings savingsAmount; 
	private Chequing chequingAmount;
	private CreditCard creditAmount;
	private String openingDate;
	
	
	
	public Account (Savings savingsAmount,Chequing chequingAmount
			, CreditCard creditAmount) {
		this.savingsAmount = savingsAmount;
		this.chequingAmount = chequingAmount;
		this.creditAmount = creditAmount;
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();		
		this.openingDate = dateFormat.format(date);// account opening date defaults to todays date
		
	}
	

	public Savings getSavingsAmount() {
		return savingsAmount;
	}

	public void setSavingsAmount(Savings savingsAmount) {
		this.savingsAmount = savingsAmount;
	}

	public Chequing getChequingAmount() {
		return chequingAmount;
	}

	public void setChequingAmount(Chequing chequingAmount) {
		this.chequingAmount = chequingAmount;
	}

	public CreditCard getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(CreditCard creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public String toString () {
		return savingsAmount + "," + chequingAmount + "," + creditAmount;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

}
