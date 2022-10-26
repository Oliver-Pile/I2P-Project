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
					LinkedList<Item> items = itemFileHandler.readLines();
					String itemID = generateItemID(items);
					if (itemID==null) System.out.println("Out of IDs");
					else{
						String[] newItem = getNewItemDetails();
						String newItemString = String.format("%s,%s,%s,%s,%s%n",itemID,newItem[0],newItem[1],newItem[2],newItem[3]);
						boolean result = itemFileHandler.add(newItemString);
						if(result) System.out.println("New item successfully added");
						else System.out.println("Item not added, please try again");
					}
					break;
				case 2:
					System.out.println("Item quantity updated");
					break;
				case 3:
					System.out.println("Item Removed");
					break;
				case 4:
					System.out.println("Report printed");
					break;
				case 5:
					System.out.println("Item File");
					LinkedList<Item> readItems = itemFileHandler.readLines();
					for (Item item : readItems){
						System.out.println(item.getItemDetails());
					}
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

	private static String generateItemID(LinkedList<Item> items){
		int largestID = Integer.MIN_VALUE;
		int currentID = 0;
		for (Item item : items){
			currentID = item.getID();
			if (currentID>largestID)largestID=currentID;
		}
		if(currentID<99999) currentID++;
		else return null;
		String newID = String.format("%05d",currentID);
		return newID;
	}

	private static String[] getNewItemDetails(){
		System.out.println("Entering new Item section");
		String[] itemDetails = new String[4];
		System.out.println("Please enter item description");
		itemDetails[0] = userInputScanner.nextLine();
		System.out.println("Please enter unit price");
		double x = userInputScanner.nextDouble();
		userInputScanner.nextLine();
		itemDetails[1] = String.format("%.2f",x);
		System.out.println("Please enter total quantity in stock");
		itemDetails[2] = userInputScanner.nextLine();
		itemDetails[3] = String.format("%.2f",Double.parseDouble(itemDetails[1])*Double.parseDouble(itemDetails[2]));
		return itemDetails;
	}
}
