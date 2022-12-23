package Test;

import org.junit.Test;
import src.Item;
import src.Database;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;


public class DatabaseTest {

    private final String testDBName = "testDB";
    private Item getLastDB(Database db) throws SQLException {
        LinkedList<Item> items = db.getItems();
        return items.getLast();

    }
    private String getRandomDesc(){
        String randomStr = "";
        Random r = new Random();
        for(int i=0;i<10;i++){
            randomStr += (char)(r.nextInt(26) + 'a');
        }
        return randomStr;
    }

    private void deleteTestDB(){
        File testDB = new File(String.format("src/%s.db",testDBName));
        testDB.delete();
    }

    @Test
    public void addTest() throws SQLException {
        Database db = new Database(testDBName);
        Item newItem = new Item(getRandomDesc(),10.99,100);
        db.add(newItem);
        Item lastItem = getLastDB(db);
        assertEquals(lastItem.getDesc(),newItem.getDesc());
        assertEquals(lastItem.getPrice(),newItem.getPrice(),0);
        assertEquals(lastItem.getQuantity(),newItem.getQuantity());
        db.close();
        deleteTestDB();
    }

    @Test
    public void updateTest() throws SQLException {
        //Setting up DB so there is an item to update
        Database db = new Database(testDBName);
        db.add(new Item(getRandomDesc(),15.87,100));
        //Updating the item
        Item itemToUpdate = getLastDB(db);
        itemToUpdate.changeQuantity(20);
        db.update(itemToUpdate,20);
        Item updatedItem = getLastDB(db);
        assertEquals(itemToUpdate.getDesc(),updatedItem.getDesc());
        assertEquals(20,updatedItem.getQuantity());
        db.close();
        deleteTestDB();
    }

    @Test
    public void deleteTest() throws SQLException {
        //Setting up DB so there is an item to delete and another item to test on
        Database db = new Database(testDBName);
        db.add(new Item(getRandomDesc(),1.99,150));
        db.add(new Item(getRandomDesc(),10.99,100));
        //Deleting the item
        Item itemToDelete = getLastDB(db);
        db.delete(itemToDelete);
        Item lastItem = getLastDB(db);
        assertNotEquals(itemToDelete.getDesc(),lastItem.getDesc());
        db.close();
        deleteTestDB();
    }

    @Test
    public void getItemsTest() throws SQLException {
        //Setting up DB so there are two items to get.
        Database db = new Database(testDBName);
        Item newItemA = new Item(getRandomDesc(),10.99,100);
        Item newItemB = new Item(getRandomDesc(),10.99,100);
        db.add(newItemA);
        db.add(newItemB);
        //Retrieving the items
        LinkedList<Item> allItems = db.getItems();
        assertTrue(allItems.get(0).getDesc().equals(newItemA.getDesc()));
        assertTrue(allItems.get(1).getDesc().equals(newItemB.getDesc()));
        db.close();
        deleteTestDB();
    }

    @Test
    public void getTransactionTest() throws SQLException {
        //Generating some transaction records.
        Database db = new Database(testDBName);
        db.add(new Item(getRandomDesc(),16.99,150));
        Item addItem = new Item(getRandomDesc(),11.99,100);
        db.add(addItem);
        Item itemToUpdate = getLastDB(db);
        itemToUpdate.changeQuantity(50);
        db.update(itemToUpdate,50);
        Item itemToDelete = getLastDB(db);
        db.delete(itemToDelete);
        //Getting transaction report
        LinkedList<String> trans = db.getTransaction(LocalDate.now().toString());
        assertTrue(trans.get(1).contains(addItem.getDesc()));
        assertTrue(trans.get(1).contains("Add"));
        assertTrue(trans.get(2).contains(itemToUpdate.getDesc()));
        assertTrue(trans.get(2).contains("50"));
        assertTrue(trans.get(2).contains("Update"));
        assertTrue(trans.get(3).contains(itemToDelete.getDesc()));
        assertTrue(trans.get(3).contains("Remove"));
        db.close();
        deleteTestDB();
    }
}
