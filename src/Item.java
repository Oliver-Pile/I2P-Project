package src;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Item is a class that models items. They can be created via user input or from the DB.
 * Used to contain all relevant data during transactions between the two.
 * @author Oliver Pile
 */
public class Item {
    private int ID;
    private String desc;
    private double price;
    private int quantity;
    private double totalPrice;

    /**
     * Constructor for creating an item based on user input
     * @param desc The description of the item
     * @param price The price of the item
     * @param quantity The quantity of the item
     */
    public Item (String desc, double price, int quantity){
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    /**
     * Constructor for creating an item based of call to DB.
     * Gets data from the DB columns in the RS.
     * @param rs ResultSet is items from DB query
     * @throws SQLException if there is an issue with the database call. This is handled by the store class.
     */
    public Item (ResultSet rs) throws SQLException {
        this.ID = rs.getInt("itemID");
        this.desc = rs.getString("desc");
        this.price = rs.getDouble("price");
        this.quantity = rs.getInt("quantity");
        this.totalPrice = rs.getDouble("totalPrice");
    }

    /**
     * @param basic Boolean value for determining which of the two strings to return.
     * @return  A human-readable string with the item details in either basic or detailed view
     */
    public String getItemDetails(boolean basic){
        if (basic) return String.format("ID: %05d, Description: %s",this.ID,this.desc);
        else return String.format("ID: %05d, Description: %s, Unit Price: %s, Quantity in Stock: %s, Total price: %s",
                    this.ID,this.desc,this.price,this.quantity,this.totalPrice);

    }

    /**
     * Accessor for ID
     * @return ID of item object
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Mutator for quantity - modifies both quantity and total price.
     * @param newQuantity the new value for the quantity attribute to be set to
     */
    public void changeQuantity(int newQuantity){
        this.quantity = newQuantity;
        this.totalPrice = newQuantity*this.price;
    }

    /**
     * Accessor for description
     * @return Description of the item object
     */
    public String getDesc(){
        return this.desc;
    }

    /**
     * Accessor for total price
     * @return Total price of the item object
     */
    public double getTotalPrice(){
        return this.totalPrice;
    }

    /**
     * Accessor for price
     * @return Price of the item object
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Accessor for quantity
     * @return Quantity of the item object
     */
    public int getQuantity(){
        return this.quantity;
    }
}
