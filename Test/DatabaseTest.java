package Test;

import org.junit.Test;
import src.Item;
import src.Database;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.*;


public class DatabaseTest {

    private Item getLastDB(Database db) throws SQLException {
        LinkedList items = db.getItems();
        return (Item) items.getLast();

    }

    @Test
    public void addTest() throws SQLException {
        Database db = new Database("testDB");
        Item newItem = new Item("A unit test item for adding",10.99,100);
        db.add(newItem);
        Item lastItem = getLastDB(db);
        assertEquals(lastItem.getDesc(),newItem.getDesc());
        assertEquals(lastItem.getPrice(),newItem.getPrice(),0);
        assertEquals(lastItem.getQuantity(),newItem.getQuantity());
        db.close();
    }

    @Test
    public void updateTest() throws SQLException {
        Database db = new Database("testDB");
        Item newItem = new Item("A unit test item for updating",10.99,100);
        db.add(newItem);
        Item itemToUpdate = getLastDB(db);
        itemToUpdate.changeQuantity(20);
        db.update(itemToUpdate,20);
        Item updatedItem = getLastDB(db);
        assertEquals(itemToUpdate.getDesc(),updatedItem.getDesc());
        assertEquals(20,updatedItem.getQuantity());
        db.close();
    }
}
