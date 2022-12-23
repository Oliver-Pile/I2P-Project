package Test;

import org.junit.Test;
import src.Item;
import src.Database;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;


public class DatabaseTest {

    private Item getLastDB(Database db) throws SQLException {
        LinkedList items = db.getItems();
        return (Item) items.getLast();

    }
    private String getRandomDesc(){
        String randomStr = "";
        Random r = new Random();
        for(int i=0;i<10;i++){
            randomStr += (char)(r.nextInt(26) + 'a');
        }
        return randomStr;
    }

    @Test
    public void addTest() throws SQLException {
        Database db = new Database("testDB");
        Item newItem = new Item(getRandomDesc(),10.99,100);
        db.add(newItem);
        Item lastItem = getLastDB(db);
        assertEquals(lastItem.getDesc(),newItem.getDesc());
        assertEquals(lastItem.getPrice(),newItem.getPrice(),0);
        assertEquals(lastItem.getQuantity(),newItem.getQuantity());
        db.close();
    }

    @Test
    public void updateTest() throws SQLException {
        //Setting up DB so there is an item to update
        Database db = new Database("testDB");
        Item newItem = new Item(getRandomDesc(),10.99,100);
        db.add(newItem);
        //Updating the item
        Item itemToUpdate = getLastDB(db);
        itemToUpdate.changeQuantity(20);
        db.update(itemToUpdate,20);
        Item updatedItem = getLastDB(db);
        assertEquals(itemToUpdate.getDesc(),updatedItem.getDesc());
        assertEquals(20,updatedItem.getQuantity());
        db.close();
    }

    @Test
    public void deleteTest() throws SQLException {
        //Setting up DB so there is an item to delete
        Database db = new Database("testDB");
        Item newItem = new Item(getRandomDesc(),10.99,100);
        db.add(newItem);
        Item itemToDelete = getLastDB(db);
        db.delete(itemToDelete);
        Item lastItem = getLastDB(db);
        assertNotEquals(itemToDelete.getDesc(),lastItem.getDesc());
    }
}
