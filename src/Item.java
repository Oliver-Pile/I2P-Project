package src;

public class Item {
    String ID;
    String desc;
    double price;
    int quantity;
    double totalPrice;

    public Item(String[] line){
        this.ID = line[0];
        this.desc = line[1];
        this.price = Double.parseDouble(line[2]);
        this.quantity = Integer.parseInt(line[3]);
        this.totalPrice = Double.parseDouble(line[4]);
    }
    public Item (String ID, String desc, double price, int quantity){
        this.ID = ID;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }
}
