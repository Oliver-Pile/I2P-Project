package src;
import java.sql.*;
import java.util.LinkedList;

public class database {
    static Connection con;
    static Statement st;

    public database() throws SQLException {
        String url = "jdbc:sqlite:src/storeDB.db";
        con = DriverManager.getConnection(url);
        System.out.println("Connection established......");
        st = con.createStatement();
        createTable();
    }

    private void createTable() throws SQLException {
        String toEx = "CREATE TABLE IF NOT EXISTS Items (itemID INTEGER PRIMARY KEY AUTOINCREMENT, desc TEXT NOT NULL, price REAL NOT NULL, quantity INTEGER NOT NULL, totalPrice REAL NOT NULL)";
        st.execute(toEx);
    }
    public int add(String toInsert) throws SQLException {
        st.execute(toInsert);
        ResultSet rs =st.executeQuery("SELECT last_insert_rowid()");
        return rs.getInt("last_insert_rowid()");
    }

    public void update(Item item) throws SQLException {
        String toUpdate = String.format("UPDATE Items SET quantity=%d WHERE itemID=%d",item.getQuantity(),item.getID());
        st.execute(toUpdate);
    }
    public void delete(Item item){
        String toDelete = String.format("DELETE from Items WHERE itemID=%d",item.getID());
    }

    public LinkedList<Item> getItems() throws SQLException {
        LinkedList<Item> items = new LinkedList<>();
        ResultSet allItems = st.executeQuery("SELECT * FROM Items");
        while (allItems.next()){
            items.add(new Item(allItems));
        }
        return items;
    }
    public void close() throws SQLException {
        con.close();
    }
}
