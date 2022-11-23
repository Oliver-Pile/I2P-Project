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
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class store {
	private static Scanner userInputScanner = new Scanner(System.in);
	private static TransactionFileHandler transactionFileHandler = new TransactionFileHandler();
	private static database db;

	public static void main(String args[]) throws InterruptedException, SQLException {
		db = new database();
		int userInput = 0;
		while (userInput != 6) {
			 userInput = getMenuChoice();
			 if(userInput==6)break;
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
	}
	private static int getMenuChoice(){
		//Validate its Int?

		System.out.println("\n\nI N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
		System.out.println("-----------------------------------------------");
		System.out.println("1. ADD NEW ITEM");
		System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
		System.out.println("3. REMOVE ITEM");
		System.out.println("4. VIEW DAILY TRANSACTION REPORT");
		System.out.println("5. Output items file");
		System.out.println("---------------------------------");
		System.out.println("6. Exit");
		System.out.print("\n Enter a choice and Press ENTER to continue[1-5]:");
		int choice = userInputScanner.nextInt();
		userInputScanner.nextLine();

		return choice;
	}

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

	private static void add() throws SQLException {
			Item newItem = createNewItem();
			db.add(newItem);
			System.out.println("New item successfully added");
	}

	private static Item search() throws SQLException {
		LinkedList<Item> readItems = db.getItems();
		System.out.println("Please enter the item ID of the item you wish to select");
		for (Item item : readItems){
			System.out.println(item.getItemDetails(true));
		}
		int selection = userInputScanner.nextInt();
		userInputScanner.nextLine();
		for (Item item : readItems){
			if(item.getID() == selection)return item;
		}
		return null;
	}

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

	private static void remove() throws SQLException {
		Item itemToRemove = search();
		System.out.printf("This is the current item details,%n %s.%nPlease confirm you with to delete (y/n)",itemToRemove.getItemDetails(false));
		char confirm = userInputScanner.nextLine().toLowerCase().charAt(0);
		if(confirm == 'y'){
			db.delete(itemToRemove);
//			String fileString = transactionFileHandler.getFileString(itemToRemove.getID(), itemToRemove.getDesc(), itemToRemove.getQuantity(),itemToRemove.getTotalPrice(), 0, "Remove");
//			transactionFileHandler.add(fileString);
		}
		else {
			System.out.println("Aborting delete");
		}
	}
	private static void outputTransactionReport() throws SQLException {
		LinkedList<String> lines = db.getTransaction();
		System.out.println("Transaction report (Negative means additions to stock)");
		for(String line : lines){
			String[] splitLine = line.split(",");
			System.out.printf("Item ID: %s, Description: %s, Quantity Sold: %s, Amount: %s, Stock Remaining: %s, Transaction Type: %s%n",splitLine[0],splitLine[1],splitLine[2],splitLine[3],splitLine[4],splitLine[5]);
		}
	}
}
