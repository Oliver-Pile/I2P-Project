package Test;

import org.junit.Test;
import src.Item;
import src.Store;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class StoreTest {

    private Method getPrivMethod(String name) throws NoSuchMethodException {
        Method method = Store.class.getDeclaredMethod(name);
        method.setAccessible(true);
        return method;
    }
    @Test
    public void createNewItemTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Item correctItem = new Item("A unit test item",10.99,100);
        String cmdInp = String.format("A unit test item%s10.99%s%s100%s",System.lineSeparator(),System.lineSeparator(),System.lineSeparator(),System.lineSeparator());
        ByteArrayInputStream bytInp = new ByteArrayInputStream(cmdInp.getBytes());
        System.setIn(bytInp);
        Item item = (Item) getPrivMethod("createNewItem").invoke(null);
        assertEquals(correctItem.getDesc(),item.getDesc());
        assertEquals(correctItem.getPrice(),item.getPrice(),0);
        assertEquals(correctItem.getQuantity(),item.getQuantity());
    }


}
