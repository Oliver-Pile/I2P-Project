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


import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;


public class store {
	private static Scanner userInputScanner = new Scanner(System.in);
	private static ItemFileHandler itemFileHandler = new ItemFileHandler();

	public static void main(String args[]) {
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
					System.out.println("Report printed");
					break;
				case 5:
					System.out.println("Searching for an item");
					System.out.println(search().getItemDetails(false));
					break;
				default:
					System.out.println("Incorrect input, please try again");
			}
		}


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

	private static Item createNewItem(String itemID){
		System.out.println("*** Entering new Item section ***");
		System.out.println("Please enter item description");
		String desc = userInputScanner.nextLine();
		System.out.println("Please enter unit price");
		double price = userInputScanner.nextDouble();
		userInputScanner.nextLine();
		System.out.println("Please enter total quantity in stock");
		int quantity = userInputScanner.nextInt();
		userInputScanner.nextLine();
		return new Item(itemID,desc,price,quantity);
	}

	private static void add(){
		String itemID = Item.generateItemID();
		if (itemID==null) System.out.println("Out of IDs");
		else{
			Item newItem = createNewItem(itemID);
			boolean result = itemFileHandler.add(newItem);
			if(result) System.out.println("New item successfully added");
			else System.out.println("Item not added, please try again");
		}
	}

	private static Item search(){
		LinkedList<Item> readItems = itemFileHandler.readLines();
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

	private static void update(){
		Item itemToUpdate = search();
		System.out.printf("This is the current item details,%n %s.%nPlease enter the updated quantity",itemToUpdate.getItemDetails(false));
		int newQuantity = userInputScanner.nextInt();
		userInputScanner.nextLine();
		itemToUpdate.changeQuantity(newQuantity);
		itemFileHandler.update(itemToUpdate);

	}

	private static void remove(){
		Item itemToRemove = search();
		System.out.printf("This is the current item details,%n %s.%nPlease confirm you with to delete (y/n)",itemToRemove.getItemDetails(false));
		char confirm = userInputScanner.nextLine().toLowerCase().charAt(0);
		if(confirm == 'y'){
			itemFileHandler.remove(itemToRemove);
		}
		else {
			System.out.println("Aborting delete");
		}
	}
}
