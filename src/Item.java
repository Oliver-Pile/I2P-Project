package src;

import java.util.LinkedList;

public class Item {
    private String ID;
    private String desc;
    private double price;
    private int quantity;
    private double totalPrice;


    public Item(String line){
        String[] splitLine = line.split(",");
        this.ID = splitLine[0];
        this.desc = splitLine[1];
        this.price = Double.parseDouble(splitLine[2]);
        this.quantity = Integer.parseInt(splitLine[3]);
        this.totalPrice = Double.parseDouble(splitLine[4]);
    }
    public Item (String ID, String desc, double price, int quantity){
        this.ID = ID;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    public String getItemDetails(boolean basic){
        if (basic) return String.format("ID: %s, Description: %s",this.ID,this.desc);
        else return String.format("ID: %s, Description: %s, Unit Price: %s, Quantity in Stock: %s, Total price: %s",
                    this.ID,this.desc,this.price,this.quantity,this.totalPrice);

    }
    public int getID(){
        return Integer.parseInt(this.ID);
    }
    public String getFileString(){
        return String.format("%s,%s,%.2f,%s,%.2f%n",this.ID,this.desc,this.price,this.quantity,this.totalPrice);
    }

    public static String generateItemID(){
        ItemFileHandler handler = new ItemFileHandler();
        LinkedList<Item> items = handler.readLines();
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

    public void changeQuantity(int newQuantity){
        this.quantity = newQuantity;
        this.totalPrice = newQuantity*this.price;
    }
}
