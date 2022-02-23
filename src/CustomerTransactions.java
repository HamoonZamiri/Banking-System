import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

//used for transaction history of each customer
public class CustomerTransactions {
	private ArrayList activity; //one unit of work e.g. open account.
	private ArrayList transactions; //transaction contains a full operation e.g transfer fund/ purchases
	
	
	public CustomerTransactions () {
		activity = new ArrayList();	
		transactions = new ArrayList();
	}
	
	public ArrayList getActivity () {
		return this.activity;
	}
	
	public void addActivity (String currentActivity) {
		this.activity.add(currentActivity);
	}
	
	public void writeTransactionsToFile(String fileName) {
		
		String activities = this.toString(); //convert current activities to a transaction(group of activities)
		try { // append characters to the file 
			FileWriter writer = new FileWriter( fileName, true );
			writer.write(activities + "\n");			
			writer.close();	
			transactions.add(activities); //add this these activities to one tranaction
			activity.clear(); //clear the list for the next activities/operations
		}
		catch ( IOException iox ) { 	
			System.out.println("Problem writing " + fileName ); 
		}
	}
	
	public String toString () {
		String content = ""; // = "Account number: " + accountNumber + "\n";
		for (int i = 0; i < activity.size(); i++) {
			content += activity.get(i).toString() + "\n";
		}
		
		return content;
	}
	
	public String recentTransactions () {
		String content = ""; 
		int transactionCounter = 0;
		int transactionSize = transactions.size() - 1;
		
		
		for(int i = transactionSize; i> -1 && transactionSize > 0; i--) {
			if(transactionCounter > 4) {
				break;
			}
			content += transactions.get(i).toString() + "\n\n";
			transactionCounter++;
		}
				
		return content;

	}
	
	private String readAllTransactions(String fileName) throws IOException //https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
	{
	    String content = "";	   
	    content = new String ( Files.readAllBytes( Paths.get(fileName) ) );	    
	    return content;
	}
	
	public void initializeTransactionHistory (String fileName) {
		Scanner myReader = null;
		try { //file reader from W3 Schools example code
			String transctionsHistory = readAllTransactions(fileName);
			String[] customerTransactions = transctionsHistory.split("\n\n");
			for(int i=0;i<customerTransactions.length;i++) {
				transactions.add(customerTransactions[i]);
			}
		}
		catch ( IOException iox ) { 	
			//failed to read the textfile
			System.out.println("initializeTransactionHistory: has failed to intialize the file " +fileName + ". "
					+ "Customer has no transaction history." ); 
		} 
		finally {
			//clean up memory
			if(myReader != null) {
				myReader.close();
			}
		}
	}
}
