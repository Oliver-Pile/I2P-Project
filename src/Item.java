package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Item {
    private int ID;
    private String desc;
    private double price;
    private int quantity;
    private double totalPrice;


//    public Item(String line){
//        String[] splitLine = line.split(",");
//        this.ID = splitLine[0];
//        this.desc = splitLine[1];
//        this.price = Double.parseDouble(splitLine[2]);
//        this.quantity = Integer.parseInt(splitLine[3]);
//        this.totalPrice = Double.parseDouble(splitLine[4]);
//    }
    public Item (String desc, double price, int quantity){
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    public Item (ResultSet rs) throws SQLException {
        this.ID = rs.getInt("itemID");
        this.desc = rs.getString("desc");
        this.price = rs.getDouble("price");
        this.quantity = rs.getInt("quantity");
        this.totalPrice = rs.getDouble("totalPrice");
    }

    public String getItemDetails(boolean basic){
        if (basic) return String.format("ID: %s, Description: %s",this.ID,this.desc);
        else return String.format("ID: %s, Description: %s, Unit Price: %s, Quantity in Stock: %s, Total price: %s",
                    this.ID,this.desc,this.price,this.quantity,this.totalPrice);

    }
    public int getID(){
        return this.ID;
    }
//    public String getFileString(){
//        return String.format("%s,%s,%.2f,%s,%.2f%n",this.ID,this.desc,this.price,this.quantity,this.totalPrice);
//    }
    public String getSQLAddString(){
        return String.format("INSERT INTO Items (desc, price, quantity, totalPrice) values('%s', %.2f, %d, %.2f)",this.desc,this.price,this.quantity,this.totalPrice);
    }

//    public static String generateItemID(){
//        ItemFileHandler handler = new ItemFileHandler();
//        LinkedList<Item> items = handler.readLines();
//        int largestID = Integer.MIN_VALUE;
//        int currentID = 0;
//        for (Item item : items){
//            currentID = item.getID();
//            if (currentID>largestID)largestID=currentID;
//        }
//        if(currentID<99999) currentID++;
//        else return null;
//        String newID = String.format("%05d",currentID);
//        return newID;
//    }

    public void changeQuantity(int newQuantity){
        this.quantity = newQuantity;
        this.totalPrice = newQuantity*this.price;
    }
    public String getDesc(){
        return this.desc;
    }
    public double getTotalPrice(){
        return this.totalPrice;
    }
    public double getPrice() {
        return this.price;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public void setID(int id){
        this.ID = id;
    }
}
