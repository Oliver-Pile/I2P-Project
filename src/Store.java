package src;
/* UNIVERSITY OF SUFFOLK - INTRODUCTION PROGRAMMING
 * Module assignment
 * 
 * Module Lead: Dr. Syed Aslam
 * Last updated 2022-10-12
 * 
 * The assignment starter code consists of 3 files:
 * 
 * a) store.java: this file contains starting code for the inventory
 * management control system. See assignment brief document for 
 * more information on how to build the rest of the application.
 * 
 * b) items.txt: this file contains a list of all items in the inventory
 * with information about their quantities and total price in stock. See 
 * assignment text for more information.
 * 
 * c) transactions.txt: this file contains a list of all the transactions
 * for the day. You will be using it to print out the report of transactions
 * Each time a transaction happens i.e. an item is added or removed, 
 * a record should be stored in transactions.txt
 *  
 *
 * You are asked to work on expanding the starter code so that your Java app can do the following:
 * 
 *  - read and output to the 2 files (transactions.txt, items.txt) as appropriate
 *  - autogenerate a (5-digit) item id ie. 00001 for each new item
 *  - add a new item to the inventory (by appending a line to items.txt) 
 *  - update the quantity of an item already in store (in items.txt)
 *  - remove an item from the inventory (by removing relevant entry in items.txt)
 *  - search for an item in the inventory (items.txt)
 *  - generate and print a daily transaction report (using transactions.txt)
 * 
 * Check out the full assignment brief for more information about the report.
 */


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Store is the main class to be executed
 *
 * @author Oliver Pile
 */
public class Store {
	private static Scanner userInputScanner = new Scanner(System.in);
	private static Database db;

	/**
	 * The default main method. It creates a new database object and then loops through a user input panel.
	 * The user will input a number and the program will branch from there.
	 * The try/catch is used to detect any issues when interacting with the db and warn the user.
	 * Has a sleep at the end to allow the user to see the output of their choice before the main selection window reappears.
	 * @param args Default params
	 * @throws InterruptedException From the TimeUnit.SECOND.Sleep().
	 */
	public static void main(String args[]) throws InterruptedException {
		try {
			db = new Database("src/storeDB");
			int userInput = 0;
			while (userInput != 6) {
				userInput = getMenuChoice();
				if (userInput == 6) break;
				switch (userInput) {
					case 1:
						add();
						break;
					case 2:
						update();
						break;
					case 3:
						remove();
						break;
					case 4:
						outputTransactionReport();
						break;
					case 5:
						System.out.println("Searching for an item");
						System.out.println(search().getItemDetails(false));
						break;
					default:
						System.out.println("Incorrect input, please try again");
				}
				TimeUnit.SECONDS.sleep(2);
			}
			db.close();
			System.out.println("\n\n Thanks for using this program...!");
		}catch (SQLException e){
			System.out.println("Error with database. Please review and try again");
		}
	}

	/**
	 * Outputs a set of options to the user via command line and waits for the user to input their response.
	 * Uses a scanner {@link java.util.Scanner} to capture user input as integer.
	 * Try/Catch detects if the user doesn't enter an integer, causing the program to return -1.
	 * @return The value entered by the user, unless there is an error in which case it returns -1.
	 */
	private static int getMenuChoice(){
		System.out.println("\n\nI N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
		System.out.println("-----------------------------------------------");
		System.out.println("1. ADD NEW ITEM");
		System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
		System.out.println("3. REMOVE ITEM");
		System.out.println("4. VIEW DAILY TRANSACTION REPORT");
		System.out.println("5. Output items file");
		System.out.println("---------------------------------");
		System.out.println("6. Exit");
		System.out.print("\n Enter a choice and Press ENTER to continue[1-6]:");
		try {
			int choice = userInputScanner.nextInt();
			userInputScanner.nextLine();
			return choice;
		}catch (InputMismatchException e){
			return -1;
		}
	}

	/**
	 * Runs through a set of input requests to the user for determining the requirements for a new item.
	 * @return An {@link Item} object with the attributes the user inputted
	 */
	private static Item createNewItem(){
		System.out.println("*** Entering new Item section ***");
		System.out.println("Please enter item description");
		String desc = userInputScanner.nextLine();
		System.out.println("Please enter unit price");
		double price = userInputScanner.nextDouble();
		userInputScanner.nextLine();
		System.out.println("Please enter total quantity in stock");
		int quantity = userInputScanner.nextInt();
		userInputScanner.nextLine();
		return new Item(desc,price,quantity);
	}

	/**
	 * Gets the new item to be added and calls a {@link Database} function
	 * @throws SQLException if there is an issue with the database call. This is handled by main.
	 */
	private static void add() throws SQLException {
			Item newItem = createNewItem();
			db.add(newItem);
			System.out.println("New item successfully added");
	}

	/**
	 * Gets all the items currently in the DB and outputs them in basic detail. Gets the user to select the item they wish to see in more detail.
	 * @return The item object that corresponds to the ID the user selected. Null if no item by that ID exists.
	 * @throws SQLException if there is an issue with the database call. This is handled by main.
	 */
	private static Item search() throws SQLException {
		LinkedList<Item> readItems = db.getItems();
		System.out.println("Please enter the item ID of the item you wish to select");
		while (true) {
			for (Item item : readItems) {
				System.out.println(item.getItemDetails(true));
			}
			int selection = userInputScanner.nextInt();
			userInputScanner.nextLine();
			for (Item item : readItems) {
				if (item.getID() == selection) {
					return item;
				}
			}
			System.out.println("Invalid choice, please try again.");
		}
	}

	/**
	 * Gets the item to be updated. Gets the user to input the new quantity required. Updates the quantity on the item object itself.
	 * Then makes a call to a {@link Database} function.
	 * @throws SQLException if there is an issue with the database call. This is handled by main.
	 */
	private static void update() throws SQLException {
		Item itemToUpdate = search();
		int oldQuantity = itemToUpdate.getQuantity();
		System.out.printf("This is the current item details,%n %s.%nPlease enter the updated quantity",itemToUpdate.getItemDetails(false));
		int newQuantity = userInputScanner.nextInt();
		userInputScanner.nextLine();
		itemToUpdate.changeQuantity(newQuantity);
		int quantityChange = oldQuantity-newQuantity;
		db.update(itemToUpdate,quantityChange);
	}

	/**
	 * Gets the item to be removed. Makes the user confirm that the item selected is correct.
	 * Makes a call to a {@link Database} function.
	 * @throws SQLException if there is an issue with the database call. This is handled by main.
	 */
	private static void remove() throws SQLException {
		Item itemToRemove = search();
		System.out.printf("This is the current item details,%n %s.%nPlease confirm you with to delete (y/n)",itemToRemove.getItemDetails(false));
		char confirm = userInputScanner.nextLine().toLowerCase().charAt(0);
		if(confirm == 'y'){
			db.delete(itemToRemove);
		}
		else {
			System.out.println("Aborting delete");
		}
	}

	/**
	 * Gets the user to input a specific date to search for (Default is current date)
	 * Calls {@link Database} function to get all transactions for specific date. Outputs them in a readable format.
	 * @throws SQLException if there is an issue with the database call. This is handled by main.
	 */
	private static void outputTransactionReport() throws SQLException {
		System.out.println("Please enter the date you wish to view (or press enter for today). Use format YYYY-MM-DD");
		String date = userInputScanner.nextLine();
		if(date.equals("")) date = LocalDate.now().toString();
		LinkedList<String> lines = db.getTransaction(date);
		if(lines.size()!=0){
			System.out.println("Transaction report (Negative means additions to stock)");
			for(String line : lines){
				String[] splitLine = line.split(",");
				System.out.printf("Item ID: %s, Description: %s, Quantity Sold: %s, Amount: %s, Stock Remaining: %s, Transaction Type: %s%n",splitLine[0],splitLine[1],splitLine[2],splitLine[3],splitLine[4],splitLine[5]);
			}
		}else System.out.println("No entries found for specified date");
	}
}
