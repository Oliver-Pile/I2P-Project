package src;

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

    public String getItemDetails(){
        return String.format("ID: %s, Description: %s, Unit Price: %s, Quantity in Stock: %s, Total price: %s",
                this.ID,this.desc,this.price,this.quantity,this.totalPrice);
    }
    public int getID(){
        return Integer.parseInt(this.ID);
    }
    public String getFileString(){
        return String.format("%s,%s,%.2f,%s,%.2f%n",this.ID,this.desc,this.price,this.quantity,this.totalPrice);
    }
}
