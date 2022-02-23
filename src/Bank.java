//Hamoon Zamiri
//Banking Assignment
//2020-04-16
//Main program
//ICS 4U1
import java.io.*;
import java.text.ParseException;
import java.util.*; 

public class Bank {
	static int customerIndex = 0; //Controls index of customers array
	static Customer [] customers = new Customer [50]; //object array of customers
	static String textFile = textFileInput();

	public static void main(String[] args) throws ParseException {
		//static String textFile = textFileInput();
		final int QUIT = 8;
		final int ADD_CUSTOMER = 1;
		final int DELETE_CUSTOMER = 2;
		final int SORT_BY_NAME = 3;
		final int SORT_BY_SIN = 4;
		final int DISPLAY_CUSTOMER_SUMMARY = 5;
		final int SEARCH_BY_NAME = 6;
		final int SEARCH_BY_SIN = 7;
		int input = 0;

		//addHeader ();
		initializeCustomer (); 
		while (input != 8) {
			System.out.println("Welcome to the VP Bank.");
			System.out.println("Please choose an action from the following:");
			System.out.println("\t1: Add a customer");
			System.out.println("\t2: Delete a customer");
			System.out.println("\t3: Sort customers by last name, first name");
			System.out.println("\t4: Sort customers by SIN");
			System.out.println("\t5: Display customer summary (Name, SIN)");
			System.out.println("\t6: Find profile by last name, first name");
			System.out.println("\t7: Find profile by SIN");
			System.out.println("\t8: Quit");

			Scanner sc = new Scanner (System.in);
			try {
				input = sc.nextInt();
			}
			catch (InputMismatchException ex) {
				System.out.println("User input was not of type int!");
			}

			if (input == QUIT) {
				System.out.print("\nSession has been terminated.");
				System.exit(0);
			}
			else if (input == ADD_CUSTOMER) {
				addCustomer ();
				printCustomers();
			}
			else if (input == SORT_BY_NAME) {
				sortByName ();
				printCustomers();
				updateArray ();
			}
			else if (input == SORT_BY_SIN) {
				sortBySin (textFile);
				printCustomers();
				updateArray ();
			}
			else if (input == DELETE_CUSTOMER) {
				int location = searchByName();
				System.out.print("\nCustomer is at index: " + location + "\n");
				customers = removeCustomer (customers, location);								
				printCustomers(); 
				updateArray();
			}
			else if (input == DISPLAY_CUSTOMER_SUMMARY) {
				displayCustomerSummary();
			}
			else if (input == SEARCH_BY_NAME) {
				int location = searchByName();

				System.out.print("\nCustomer is at index: " + location + "\n");
				if (location != -1) {
					profileMenu (location);
				}
				else {
					System.out.print("\nCustomer is not found\n");
				}
				printCustomers();
			}

			else if (input == SEARCH_BY_SIN) {
				int location = searchBySin();
				System.out.print("\nCustomer is at index: " + location + "\n");
				if (location != -1) {
					profileMenu (location);
				}
				else {
					System.out.print("\nCustomer is not found\n");
				}
				printCustomers();

			}
		}

	}


	public static Customer[] removeCustomer(Customer[] customers,int index) // //https://www.geeksforgeeks.org/remove-an-element-at-specific-index-from-an-array-in-java/

	{
		Customer currentCustomer = customers [index];
		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		if (currentSavingsBalance.equals("none") && currentChequingBalance.equals("none")
				&& currentCreditBalance.equals("none")){
			// If the array is empty 
			// or the index is not in array range 
			// return the original array 
			if (customers == null
					|| index < 0
					|| index >= customers.length) { 

				return customers; 
			} 

			// Create another array of size one less 
			Customer[] newCustomersArray = new Customer[customers.length]; 

			// Copy the elements except the index 
			// from original array to the other array 
			for (int i = 0, k = 0; i < customers.length; i++) { 			
				// if the index is 
				// the removal element index 
				if (i == index) { 
					customerIndex --;
					continue; 
				} 			
				// if the index is not 
				// the removal element index 
				newCustomersArray[k++] = customers[i]; 
			} 

			// return the resultant array 
			return newCustomersArray; 
		}
		else {
			System.out.println ("One or more cards are still active, accounts must be closed!");
			return customers;
		}
	} 

	public static void displayCustomerSummary () {

		for (int i = 0; i < customerIndex; i++) {
			String current_lastName = customers[i].getLastName();
			String current_firstName = customers[i].getFirstName();
			long currentSin = customers[i].getSinNumber();
			System.out.print("Name: " + current_lastName + "," + current_firstName +  " Sin: " + currentSin + "\n");
		}
	}

	public static void updateArray () {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
			for (int loopCount = 0; loopCount < customerIndex; loopCount++) {

				out.write(customers[loopCount].toString() + "\n");
			}

			out.close(); 
		} 
		catch ( IOException iox ) { 	
			System.out.println("Problem writing! "+ "stuff.txt"); 
		} 
	}

	public static String textFileInput() { //requests text file
		Scanner sc = new Scanner (System.in);
		System.out.print("Enter the text file (.txt) ");
		return sc.nextLine();

	}

	public static void addHeader () {
		try { 

			File file = new File(textFile);
			if(!file.exists()) { //only add the header once, when file is created for the first time
				BufferedWriter out = new BufferedWriter(new FileWriter(textFile));

				out.write("Last Name,First Name,SIN,Birth Year, Birth Month, Birth Day,Savings Balance,Chequing Balance,Credit Balance\n");
				out.close(); 
			}
		} 
		catch ( IOException iox ) { 	
			System.out.println("Problem writing! "+ textFile); 
		} 
	}
	public static void addCustomer () { //function 1.
		System.out.print("\nEnter last name: ");
		Scanner scan = new Scanner (System.in);//scanner for String
		Scanner sc = new Scanner (System.in);//scanner for long and int

		String lastName = scan.nextLine();

		System.out.print("\nEnter first name: ");
		String firstName = scan.nextLine();

		System.out.print ("\nEnter SIN Number: ");
		long sinNumber = sc.nextLong();

		System.out.println();
		System.out.print("\nEnter your birth year: ");

		int birthYear = sc.nextInt();

		int birthMonth = -1;
		do {
			System.out.print("\nEnter your birth month (0-12): ");
			birthMonth = sc.nextInt();
		} while (birthMonth < 0 || birthMonth > 12);

		int birthDay = -1;
		do {
			System.out.print("\nEnter your birth day (1-31): ");
			birthDay = sc.nextInt();
		} while (birthDay < 1 || birthDay > 31);

		//default customer layout
		Savings defaultS = new Savings ("none");
		Chequing defaultCh = new Chequing ("none");
		CreditCard defaultCr = new CreditCard ("none");

		Account account = new Account (defaultS,defaultCh,defaultCr);

		Customer c = new Customer (lastName, firstName, sinNumber,birthYear,
				birthMonth,birthDay,defaultS,defaultCh,defaultCr);

		customers [customerIndex] = c;

		try { // append characters to the file 
			FileWriter writer = new FileWriter( textFile, true );
			//writer.write("\n"+currentLine[1] + "\n");
			//writer.write(c.toString() + "\n");
			writer.write(customers[customerIndex].toString() + "\n");
			customerIndex ++; //increase index for next customer
			writer.close();	
		}
		catch ( IOException iox ) { 	
			System.out.println("Problem writing " + textFile ); 
		}
	}//ends addCustomer method

	public static void sortBySin (String textFile) {
		int i,j;
		Customer newValue;

		for (i = 1; i < customerIndex; i++) {
			newValue = customers[i];
			j = i;
			while (j > 0 && customers [j-1].compareToSin(newValue)) {
				customers [j] = customers [j-1];
				j--;
			}
			customers [j] = newValue;
		}

	}

	public static int searchByName (){
		int location = -1;
		boolean found = false;
		Scanner scan = new Scanner (System.in);

		System.out.print("Last Name: ");
		String desiredLastName = scan.nextLine();

		System.out.print("\nFirst Name: ");
		String desiredFirstName = scan.nextLine();

		for (int i = 0; i < customerIndex && !found; i++) {
			if (customers [i].equalsName(desiredLastName, desiredFirstName)) {
				location = i;
				found = true;
			}
		}
		return location;
	}

	public static int searchBySin () {
		int location = -1;
		boolean found = false;
		Scanner sc = new Scanner (System.in);
		long desiredSin = 0;
		try {
			System.out.print("Enter SIN Number: ");
			desiredSin = sc.nextLong();
		}

		catch (InputMismatchException e) {
			System.out.println("Entry was not of type Long!");
		}
		for (int i = 0; i < customerIndex && !found; i++) {
			if (customers [i].equalsSin(desiredSin)) {
				location = i;
				found = true;
			}
		}
		if (location == -1) {
			System.out.println("Customer not found!");
		}
		return location;
	}

	public static void sortByName () {

		Customer newValue;
		int i,j;
		for (i = 1; i < customerIndex; i++) {
			newValue = customers[i];
			j = i;
			while (j > 0 && customers[j-1].compareToName(newValue) > 0) {
				customers [j] = customers [j-1];
				j--;
			}
			customers [j] = newValue;
		}

	}//end sortByName

	public static void printCustomers () {
		for (int i = 0; i < customerIndex; i++) {
			System.out.print(customers[i]);
			System.out.println();
		}
	}

	public static void initializeCustomer (){
		Scanner myReader = null;
		try { //file reader from W3 Schools example code
			File myObj = new File(textFile);
			myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {

				String data = myReader.nextLine(); //current line 

				String [] currentLine = data.split(",");
				String lastName = currentLine [0];
				String firstName = currentLine [1];
				long sinNumber = Long.parseLong(currentLine [2]);
				int birthYear = Integer.parseInt(currentLine [3]);
				int birthMonth = Integer.parseInt(currentLine [4]);
				int birthDay = Integer.parseInt(currentLine [5]);
				Savings s = new Savings (currentLine [6]);
				Chequing ch = new Chequing (currentLine [7]);
				CreditCard cr = new CreditCard (currentLine[8]);

				Customer c = new Customer (lastName,firstName,sinNumber,birthYear,
						birthMonth,birthDay,s,ch,cr);
				customers [customerIndex] = c;
				customerIndex++;
			}			
		}
		catch ( IOException iox ) { 	
			//failed to read the textfile
			System.out.println("initializeCustomer: has failed to intialize the file " +textFile + ". There is no customer history, file has been created." ); 
		} 
		finally {
			//clean up memory
			if(myReader != null) {
				myReader.close();
			}
		}
	}//end initializeCustomer

	public static void profileMenu (int location) throws ParseException {
		Scanner sc = new Scanner (System.in);
		Customer currentCustomer = customers [location];

		String currentCustName = currentCustomer.getLastName() + "" + 
				currentCustomer.getFirstName() + ".txt";
		int input = -1;
		final int VIEW_ACCOUNT_ACTIVITY = 1;
		final int DEPOSIT = 2;
		final int WITHDRAW = 3;
		final int PROCESS_CHEQUE = 4;
		final int PROCESS_PURCHASE = 5;
		final int PAY_BILL = 6;
		final int TRANSFER_FUNDS = 7;
		final int ISSUE_CARD = 8;
		final int CANCEL_ACCOUNT = 9;

		currentCustomer.getCustomerActivity().initializeTransactionHistory (currentCustName);

		while (input != 10) {
			System.out.println("PROFILE MENU");
			System.out.println("\t1: View Account Activity");
			System.out.println("\t2: Deposit");
			System.out.println("\t3: Withdraw");
			System.out.println("\t4: Process cheque");
			System.out.println("\t5: Process purchase");
			System.out.println("\t6: Pay bill");
			System.out.println("\t7: Transfer Funds");
			System.out.println("\t8: Open account or issue card");
			System.out.println("\t9: Cancel account or card");
			System.out.println("\t10: Return to main menu");

			input = sc.nextInt();
			if (input == DEPOSIT) {
				deposit(currentCustomer);
				updateArray();
			}
			else if (input == VIEW_ACCOUNT_ACTIVITY) {
				viewAccountActivity (currentCustomer);
				updateArray();
			}
			else if (input == WITHDRAW) {
				withdraw (currentCustomer);
				updateArray();
			}
			else if(input == ISSUE_CARD) {
				issueCard (currentCustomer);
				updateArray();

			}
			else if (input == PROCESS_CHEQUE) {
				processCheque (currentCustomer);
				updateArray();
			}
			else if (input == PROCESS_PURCHASE) {
				processPurchase (currentCustomer);
				updateArray();
			}
			else if (input == PAY_BILL) {
				payBill (currentCustomer);
				updateArray();
			}
			else if (input == TRANSFER_FUNDS) {
				transferFunds (currentCustomer);
				updateArray();
			}
			else if (input == CANCEL_ACCOUNT) {
				cancelCard (currentCustomer);
				updateArray();
			}
		}
	}//end profile menu

	public static void viewAccountActivity (Customer currentCustomer ) {
		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();
		System.out.print(customerActivity.recentTransactions());
	}

	public static void deposit (Customer currentCustomer) {
		final int SAVING = 1;
		final int CHEQUING = 2;

		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";
		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity(); //transaction activity object

		System.out.print("Enter amount: ");
		Scanner sc = new Scanner (System.in);

		Double amount = sc.nextDouble();
		String currentSavingsBalance = ""+ currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = ""+ currentCustomer.getChequingAmount(); //current chequing balance
		System.out.print("1:Savings 2:Chequing");
		int accountChoice = sc.nextInt();

		if (accountChoice == SAVING && !(currentSavingsBalance.equals("none"))) {
			double total = Double.parseDouble(currentSavingsBalance) + amount;
			Savings newTotal = new Savings (""+total);
			currentCustomer.setSavingsAmount(newTotal);

			customerActivity.addActivity("Description: Deposit Cash");
			customerActivity.addActivity("Account: Saving Account");
			customerActivity.addActivity("Opening Balance: " + currentSavingsBalance);
			customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
			customerActivity.addActivity("Amount of Transaction: " + amount);
			customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount());
			customerActivity.writeTransactionsToFile (currentCustName);

		}
		else if (accountChoice == CHEQUING && !(currentChequingBalance.equals("none"))) {
			double total = Double.parseDouble(currentChequingBalance) + amount;
			Chequing newTotal = new Chequing ("" + total);
			currentCustomer.setChequingAmount(newTotal);

			customerActivity.addActivity("Description: Deposit Cash");
			customerActivity.addActivity("Account: Chequing Account");
			customerActivity.addActivity("Opening Balance: " + currentChequingBalance);
			customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
			customerActivity.addActivity("Amount of Transaction: " + amount);
			customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount());
			customerActivity.writeTransactionsToFile (currentCustName);
		}
		else {
			System.out.println("Customer has not created the specified account.");
		}


		customerActivity.writeTransactionsToFile (currentCustName);

	}//end deposit

	public static void withdraw (Customer currentCustomer) {
		final int SAVING = 1;
		final int CHEQUING = 2;
		final int CREDIT = 3;

		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";
		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();

		System.out.print("Enter amount: ");
		Scanner sc = new Scanner (System.in);

		Double amount = sc.nextDouble();
		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		System.out.print("1:Savings 2:Chequing 3:Credit card");
		int accountChoice = sc.nextInt();

		if (accountChoice == SAVING && !currentSavingsBalance.equals("none")) {
			double total = Double.parseDouble(currentSavingsBalance) - amount;
			if (total >= 0) {
				Savings newTotal = new Savings (""+total);
				currentCustomer.setSavingsAmount(newTotal);

				customerActivity.addActivity("Description: Withdraw Cash");
				customerActivity.addActivity("Account: Saving Account");
				customerActivity.addActivity("Opening Balance: " + currentSavingsBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + amount);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}
			else {
				System.out.println("Account balance can not be below $0");
			}
		}
		else if (accountChoice == CHEQUING && !(currentChequingBalance.equals("none"))) {

			double total = Double.parseDouble(currentChequingBalance) - amount;
			if (total >= 0) {
				Chequing newTotal = new Chequing ("" + total);
				currentCustomer.setChequingAmount(newTotal);

				customerActivity.addActivity("Description: Withdraw Cash");
				customerActivity.addActivity("Account: Chequing Account");
				customerActivity.addActivity("Opening Balance: " + currentChequingBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + amount);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}
			else {
				System.out.println("Account balance can not be below $0");
			}


		}
		else if (accountChoice == CREDIT && !currentCreditBalance.equals("none")) {
			double total = Double.parseDouble(currentCreditBalance) - amount;
			CreditCard newTotal = new CreditCard ("" + total);
			currentCustomer.setCreditAmount(newTotal);

			customerActivity.addActivity("Description: Withdraw Cash");
			customerActivity.addActivity("Account: Credit Card Account");
			customerActivity.addActivity("Opening Balance: " + currentCreditBalance);
			customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
			customerActivity.addActivity("Amount of Transaction: " + amount);
			customerActivity.addActivity("Ending Balance: " + currentCustomer.getCreditAmount());
			customerActivity.writeTransactionsToFile (currentCustName);
		}
		else {
			System.out.println("Customer has not created the specified account.");
		}

	} //end withdraw

	public static void processCheque (Customer currentCustomer) {
		Scanner sc = new Scanner (System.in);
		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();

		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";

		String currentChequingBalance = "" + currentCustomer.getChequingAmount();

		System.out.print("Enter amount on cheque: ");
		double amount = sc.nextDouble();

		if (!((currentChequingBalance).equals("none"))) {
			double total = Double.parseDouble(currentChequingBalance) + amount;
			Chequing newTotal = new Chequing ("" + total);
			currentCustomer.setChequingAmount(newTotal);

			customerActivity.addActivity("Description: Process Cheque");
			customerActivity.addActivity("Account: Chequing Account");
			customerActivity.addActivity("Opening Balance: " + currentChequingBalance);
			customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
			customerActivity.addActivity("Amount of Transaction: " + amount);
			customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount());
			customerActivity.writeTransactionsToFile (currentCustName);
		}
		else {
			System.out.println ("Chequing account has not been created!");
		}


	} // end process cheque

	public static void processPurchase (Customer currentCustomer) {
		Scanner sc = new Scanner (System.in);
		Scanner scan = new Scanner (System.in);
		final int SAVINGS = 1;
		final int CHEQUING = 2;
		final int CREDIT = 3;

		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";

		System.out.print("Purchase was made at... ");
		String locationOfPurchase = scan.nextLine();

		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();

		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		System.out.print("Amount: ");
		double amount = sc.nextDouble();

		System.out.println("");
		System.out.print("Paid from 1:Savings 2:Chequing 3:Credit card: ");
		int accountChoice = sc.nextInt();

		if (accountChoice == SAVINGS && !currentSavingsBalance.equals("none")) {
			if (Double.parseDouble(currentSavingsBalance) - amount >= 0){
				double total = Double.parseDouble(currentSavingsBalance) - amount;
				Savings newTotal = new Savings ("" + total);
				currentCustomer.setSavingsAmount(newTotal);

				customerActivity.addActivity("Description: Purchase at " + locationOfPurchase);
				customerActivity.addActivity("Account: Saving Account");
				customerActivity.addActivity("Opening Balance: " + currentSavingsBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + amount);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}
		}

		else if (accountChoice == CHEQUING && !currentChequingBalance.equals("none")) {

			if (Double.parseDouble(currentChequingBalance) - amount >= 0) {
				double total = Double.parseDouble(currentChequingBalance) - amount;
				Chequing newTotal = new Chequing ("" + total);
				currentCustomer.setChequingAmount(newTotal);
				currentCustomer.setChequingAmount(newTotal);

				customerActivity.addActivity("Description: Purchase at " + locationOfPurchase);
				customerActivity.addActivity("Account: Chequing Account");
				customerActivity.addActivity("Opening Balance: " + currentChequingBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + amount);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}
		}


		else if (accountChoice == CREDIT && !currentCreditBalance.equals("none")) {

			double total = Double.parseDouble(currentCreditBalance) - amount;
			CreditCard newTotal = new CreditCard ("" + total);
			currentCustomer.setCreditAmount(newTotal);

			customerActivity.addActivity("Description: Purchase at " + locationOfPurchase);
			customerActivity.addActivity("Account: Credit Card Account");
			customerActivity.addActivity("Opening Balance: " + currentCreditBalance);
			customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
			customerActivity.addActivity("Amount of Transaction: " + amount);
			customerActivity.addActivity("Ending Balance: " + currentCustomer.getCreditAmount());
			customerActivity.writeTransactionsToFile (currentCustName);
		}
		else {
			System.out.println("Insufficent funds/Account does not exist");
		}



	}//end process purchase

	public static void transferFunds (Customer currentCustomer) {
		final int SAVINGS = 1;
		final int CHEQUING = 2;
		Scanner sc = new Scanner (System.in);
		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";

		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();
		customerActivity.addActivity("Description: Fund Transfer ");

		System.out.println ("Transfer funds between Savings and Chequing Accounts");
		int accountChoice = -1;
		while (accountChoice != SAVINGS && accountChoice != CHEQUING) {
			System.out.print("Transfer From 1: Savings or 2: Chequing ");
			accountChoice = sc.nextInt();
		}
		System.out.println("");

		System.out.print("Enter amount: ");
		double amount = sc.nextDouble();

		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); 
		String currentChequingBalance = "" + currentCustomer.getChequingAmount();

		if (!currentSavingsBalance.equals("none") && !currentChequingBalance.equals("none")) {
			if (accountChoice == SAVINGS && Double.parseDouble(currentSavingsBalance)
					> amount) {
				double newSavingsTotal = Double.parseDouble(currentSavingsBalance) - amount;
				Savings s = new Savings ("" + newSavingsTotal);
				currentCustomer.setSavingsAmount(s);

				double newChequingTotal = Double.parseDouble(currentChequingBalance) + amount;
				Chequing c = new Chequing ("" + newChequingTotal);
				currentCustomer.setChequingAmount(c);

				//writing to transaction file
				customerActivity.addActivity("Description: Fund Transfer ");
				customerActivity.addActivity("From: Savings Account");
				customerActivity.addActivity("To: Chequing Account");
				customerActivity.addActivity("Amount of Transfer: " + amount);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Account: Savings" );
				customerActivity.addActivity("Opening Balance: " +  currentSavingsBalance);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount()); 
				customerActivity.addActivity("Account: Chequing" );
				customerActivity.addActivity("Opening Balance: " +  currentChequingBalance);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount()); 
				customerActivity.writeTransactionsToFile (currentCustName);

			}

			else if (accountChoice == CHEQUING && Double.parseDouble(currentChequingBalance)
					> amount) {
				double newChequingTotal = Double.parseDouble(currentChequingBalance) - amount;
				Chequing c = new Chequing ("" + newChequingTotal);
				currentCustomer.setChequingAmount(c);

				double newSavingsTotal = Double.parseDouble(currentSavingsBalance) + amount;
				Savings s = new Savings ("" + newSavingsTotal);
				currentCustomer.setSavingsAmount(s);

				customerActivity.addActivity("Description: Fund Transfer ");
				customerActivity.addActivity("From: Chequing Account");
				customerActivity.addActivity("To: Savings Account");
				customerActivity.addActivity("Amount of Transfer: " + amount);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Account: Chequing" );
				customerActivity.addActivity("Opening Balance: " +  currentChequingBalance);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount()); 
				customerActivity.addActivity("Account: Savings" );
				customerActivity.addActivity("Opening Balance: " +  currentSavingsBalance);
				customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}

			else {
				System.out.println("Problem transfering funds!");
			}
		}
		else {
			System.out.println ("Savings and/or Chequing account has not been created!");
		}

	}

	public static void payBill (Customer currentCustomer) {
		final int SAVING = 1;
		final int CHEQUING = 2;
		Scanner sc = new Scanner (System.in);
		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";

		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();

		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		System.out.print("Pay bill with 1: Saving 2: Chequing: ");
		int accountChoice = sc.nextInt();

		if (!currentCreditBalance.equals("none")) {
			if (accountChoice == SAVING && Double.parseDouble(currentSavingsBalance) > //try to pay bill with no account and see what happens
			Double.parseDouble(currentCreditBalance) && !(currentSavingsBalance.equals("none"))) {
				double savingsTotal = Double.parseDouble(currentSavingsBalance) + 
						Double.parseDouble(currentCreditBalance);
				Savings s = new Savings ("" + savingsTotal);
				currentCustomer.setSavingsAmount(s);

				CreditCard c = new CreditCard ("0");
				currentCustomer.setCreditAmount(c);

				System.out.println ("Credit card balance: " + "0");

				customerActivity.addActivity("Description: Bill Payment");
				customerActivity.addActivity("Account: Savings Account");
				customerActivity.addActivity("Opening Savings Balance: " + currentSavingsBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + -Double.parseDouble(currentCreditBalance));
				customerActivity.addActivity("Ending Savings Balance: " + currentCustomer.getSavingsAmount());
				customerActivity.addActivity ("Ending Credit Card Balance: " + currentCustomer.getCreditAmount());
				customerActivity.writeTransactionsToFile (currentCustName);
			}
			else if (accountChoice == SAVING && Double.parseDouble(currentSavingsBalance) <
					Double.parseDouble(currentCreditBalance) && !(currentSavingsBalance.equals("none"))) {
				Savings s = new Savings ("0");
				currentCustomer.setSavingsAmount(s);

				double creditTotal = Double.parseDouble(currentCreditBalance) + 
						Double.parseDouble(currentSavingsBalance);

				CreditCard cr = new CreditCard ("" + creditTotal);
				currentCustomer.setCreditAmount(cr);

				customerActivity.addActivity("Description: Bill Payment");
				customerActivity.addActivity("Account: Savings Account");
				customerActivity.addActivity("Opening Savings Balance: " + currentSavingsBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + -Double.parseDouble(currentCreditBalance));
				customerActivity.addActivity("Ending Savings Balance: " + currentCustomer.getSavingsAmount());
				customerActivity.addActivity ("Ending Credit Card Balance: " + currentCustomer.getCreditAmount());
				customerActivity.writeTransactionsToFile (currentCustName);

				System.out.println("Credit card balance: " + creditTotal);
			}


			else if (accountChoice == CHEQUING && !currentChequingBalance.equals("none")
					&& Double.parseDouble(currentChequingBalance) > 
			Double.parseDouble(currentCreditBalance)) {
				double chequingTotal = Double.parseDouble(currentChequingBalance) - 
						Double.parseDouble(currentCreditBalance);
				Chequing c = new Chequing ("" + chequingTotal);
				currentCustomer.setChequingAmount(c);
				CreditCard cr = new CreditCard ("0");
				currentCustomer.setCreditAmount(cr);

				System.out.println ("Credit card balance: " + "0");

				customerActivity.addActivity("Description: Bill Payment");
				customerActivity.addActivity("Account: Chequing Account");
				customerActivity.addActivity("Opening Chequing Balance: " + currentChequingBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + -Double.parseDouble(currentCreditBalance));
				customerActivity.addActivity("Ending Chequing Balance: " + currentCustomer.getChequingAmount());
				customerActivity.addActivity ("Ending Credit Card Balance: " + currentCustomer.getCreditAmount());
				customerActivity.writeTransactionsToFile (currentCustName);



			}
			else if (accountChoice == CHEQUING && !currentChequingBalance.equals("none")
					&& Double.parseDouble(currentChequingBalance) < 
					Double.parseDouble(currentCreditBalance)) {

				Chequing c = new Chequing ("0");
				currentCustomer.setChequingAmount(c);
				double creditTotal = Double.parseDouble(currentCreditBalance) - 
						Double.parseDouble(currentChequingBalance);

				CreditCard cr = new CreditCard ("" + creditTotal);
				currentCustomer.setCreditAmount(cr);

				customerActivity.addActivity("Description: Bill Payment");
				customerActivity.addActivity("Account: Chequing Account");
				customerActivity.addActivity("Opening Chequing Balance: " + currentChequingBalance);
				customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
				customerActivity.addActivity("Amount of Transaction: " + -Double.parseDouble(currentCreditBalance));
				customerActivity.addActivity("Ending Chequing Balance: " + currentCustomer.getChequingAmount());
				customerActivity.addActivity ("Ending Credit Card Balance: " + currentCustomer.getCreditAmount());
				customerActivity.writeTransactionsToFile (currentCustName);

				System.out.print("Remaing Credit Bill: " + currentCustomer.getCreditAmount());


			}
			else {
				System.out.println("Specified Account has not been opened!");
			}
		}
		else {
			System.out.println ("Credit Card account has not been created!");
		}




	}//end pay bill

	public static void issueCard (Customer currentCustomer) throws ParseException {
		String currentCustName = currentCustomer.getLastName() + "" + currentCustomer.getFirstName() + ".txt";
		Scanner scanString = new Scanner (System.in);
		Scanner scanInt = new Scanner (System.in);
		final int SAVING = 1;
		final int CHEQUING = 2;
		final int CREDIT = 3;
		CustomerTransactions customerActivity = currentCustomer.getCustomerActivity();

		int accountChoice = -1;
		System.out.println("");
		while (accountChoice != SAVING && accountChoice != CHEQUING 
				&& accountChoice != CREDIT) {
			System.out.print("1:Savings 2:Chequing 3:Credit Card");
			accountChoice = scanInt.nextInt();

			if (accountChoice == SAVING || accountChoice == CHEQUING) {
				System.out.print("Enter opening balance: ");
				String openingBalance = scanString.nextLine();
				if (accountChoice == SAVING) {
					Savings amount = new Savings (openingBalance);
					currentCustomer.setSavingsAmount(amount);					

					customerActivity.addActivity("Description: Opened an Account");
					customerActivity.addActivity("Account: Saving Account");
					customerActivity.addActivity("Opening Balance: " + currentCustomer.getSavingsAmount());
					customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
					customerActivity.addActivity("Amount of Transaction: 0.00");
					customerActivity.addActivity("Ending Balance: " + currentCustomer.getSavingsAmount());
					customerActivity.writeTransactionsToFile (currentCustName);

				}
				else if (accountChoice == CHEQUING && currentCustomer.isAdult()) {
					Chequing amount = new Chequing (openingBalance);
					currentCustomer.setChequingAmount(amount);

					customerActivity.addActivity("Description: Opened an Account");
					customerActivity.addActivity("Account: Chequing Account");
					customerActivity.addActivity("Opening Balance: " + currentCustomer.getChequingAmount());
					customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
					customerActivity.addActivity("Amount of Transaction: 0.00");
					customerActivity.addActivity("Ending Balance: " + currentCustomer.getChequingAmount());
					customerActivity.writeTransactionsToFile (currentCustName);
				}

			}
			else if (accountChoice == CREDIT){
				if (currentCustomer.isAdult()) {
					CreditCard amount = new CreditCard ("0");
					customerActivity.addActivity("Description: Opened an Account");
					currentCustomer.setCreditAmount(amount);
					customerActivity.addActivity("Account: Credit Card");
					customerActivity.addActivity("Opening Balance: " + currentCustomer.getCreditAmount());
					customerActivity.addActivity("Date: " + currentCustomer.getOpeningDate());
					customerActivity.addActivity("Amount of Transaction: 0.00");
					customerActivity.addActivity("Ending Balance: " + currentCustomer.getCreditAmount());
					customerActivity.writeTransactionsToFile (currentCustName);
				}
				else {
					System.out.println ("Customer is not over 18.");
				}
			}
		}


	}//end issueCard

	public static void cancelCard (Customer currentCustomer) {
		Scanner sc = new Scanner (System.in);

		final int SAVINGS = 1;
		final int CHEQUING = 2;
		final int CREDIT = 3;

		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		System.out.print("Which card would you like to cancel: 1: Savings 2: Chequing 3: Credit Card ");
		int accountChoice = sc.nextInt();

		System.out.println("");

		if (accountChoice == SAVINGS && !currentSavingsBalance.equals("none")) {
			Savings s = new Savings ("none");
			currentCustomer.setSavingsAmount(s);
		}
		else if (accountChoice == CHEQUING && !currentChequingBalance.equals("none")) {
			Chequing c = new Chequing ("none");
			currentCustomer.setChequingAmount(c);
		}
		else if (accountChoice == CREDIT) {
			if (Double.parseDouble(currentCreditBalance) == 0) {
				CreditCard c = new CreditCard ("none");
				currentCustomer.setCreditAmount(c);
			}
			else {
				System.out.println("Balance is negative, you must pay credit card bills before cancelling!");
			}
		}
		else {
			System.out.println("Card can not be cancelled!");
		}

	}


	public static void test(Customer currentCustomer) throws ParseException {
		String currentSavingsBalance = "" + currentCustomer.getSavingsAmount(); //current savings balance
		String currentChequingBalance = "" + currentCustomer.getChequingAmount(); //current chequing balance
		String currentCreditBalance = "" + currentCustomer.getCreditAmount();

		System.out.println ("Adult: " + currentCustomer.isAdult());
		System.out.println ("Saving Existance: " + !(currentSavingsBalance.equals("none")));
		System.out.println ("Chequing Existance: " + !(currentChequingBalance.equals("none")));
		System.out.println ("Credit Existance: " +!(currentCreditBalance.equals("none")));
	}

}//end class
