//inherits Account class to control customer balances through direct use of getters and setters in Account class
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Customer extends Account{
	private String lastName;
	private String firstName;
	private long sinNumber;
	private int birthYear;
	private int birthDay;
	private int birthMonth;
	private CustomerTransactions customerTransactions;

	
	public Customer (String lastName, String firstName, long sinNumber, 
			int birthYear,int birthMonth,int birthDay, Savings savingsAmount,Chequing chequingAmount
			, CreditCard creditAmount) {
		super(savingsAmount,chequingAmount,creditAmount);
		this.lastName = lastName;
		this.firstName = firstName;
		this.sinNumber = sinNumber;
		this.birthYear = birthYear;
		this.birthMonth = birthMonth;
		this.birthDay = birthDay;
		this.customerTransactions = new CustomerTransactions();
	}

	public CustomerTransactions getCustomerActivity() {
		return customerTransactions;
	}
	
	public String getLastName () {
		return lastName;
	}

	public String getFirstName () {
		return firstName;
	}

	public long getSinNumber() {
		return sinNumber;
	}

	public int compareToName (Customer c) {
		return this.getLastName().compareTo(c.getLastName());
	}

	public boolean compareToSin (Customer c) {
		return this.getSinNumber() > c.getSinNumber();
	}

	public boolean equalsName (String lastName, String firstName) {
		return this.getLastName().equals(lastName) && this.getFirstName().equals (firstName);
	}

	public boolean equalsSin (long sin) {
		return this.getSinNumber() == sin;
	}
	

	public boolean isAdult () throws ParseException { //code from https://www.candidjava.com/tutorial/java-program-to-calculate-age-from-date-of-birth/
		String s = "" + birthYear + "/" + "" + birthMonth + "/" + birthDay;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d = sdf.parse(s);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		
		if (diff1.getYears() >= 18) {
			return true;
		}
		else {
			return false;
		}
	}


	public String toString() {
		return lastName + "," + firstName + "," +  sinNumber + "," + 
				birthYear + "," + birthMonth + "," + birthDay + "," + super.toString();
	}


}
