package src;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Item {
    private int ID;
    private String desc;
    private double price;
    private int quantity;
    private double totalPrice;

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
    public int getID() {
        return this.ID;
    }

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
