package src;
import java.sql.*;
import java.time.LocalDate;
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
        st.execute("CREATE TABLE IF NOT EXISTS Transactions (itemID INTEGER NOT NULL, desc TEXT NOT NULL, changeQty INTEGER NOT NULL, amount REAL NOT NULL, stockRemaining INTEGER NOT NULL, transactionType TEXT NOT NULL, date TEXT NOT NULL)");
    }
    public void add(Item item) throws SQLException {
        String toInsert = String.format("INSERT INTO Items (desc, price, quantity, totalPrice) values('%s', %.2f, %d, %.2f)",item.getDesc(),item.getPrice(),item.getQuantity(),item.getTotalPrice());
        st.execute(toInsert);
        ResultSet rs =st.executeQuery("SELECT last_insert_rowid()");
        int newID = rs.getInt("last_insert_rowid()");
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",newID, item.getDesc(), -1*item.getQuantity(), -1*item.getTotalPrice(),item.getQuantity(),"Add", LocalDate.now());
        st.execute(trans);
    }

    public void update(Item item, int qtyChange) throws SQLException {
        String toUpdate = String.format("UPDATE Items SET quantity=%d WHERE itemID=%d",item.getQuantity(),item.getID());
        st.execute(toUpdate);
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",item.getID(), item.getDesc(), qtyChange, qtyChange*item.getPrice(),item.getQuantity(),"Update",LocalDate.now());
        st.execute(trans);
    }
    public void delete(Item item) throws SQLException {
        String toDelete = String.format("DELETE from Items WHERE itemID=%d",item.getID());
        st.execute(toDelete);
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",item.getID(), item.getDesc(), item.getQuantity(), item.getTotalPrice(),0,"Remove",LocalDate.now());
        st.execute(trans);
    }

    public LinkedList<Item> getItems() throws SQLException {
        LinkedList<Item> items = new LinkedList<>();
        ResultSet allItems = st.executeQuery("SELECT * FROM Items");
        while (allItems.next()){
            items.add(new Item(allItems));
        }
        return items;
    }
    public LinkedList<String> getTransaction() throws SQLException {
        LinkedList<String> transactions = new LinkedList<>();
        ResultSet allTransactions = st.executeQuery(String.format("SELECT * FROM Transactions WHERE date='%s'",LocalDate.now()));
        while(allTransactions.next()){
            transactions.add(String.format("%05d,%s,%d,%.2f,%d,%s",allTransactions.getInt("itemID"),allTransactions.getString("desc"),allTransactions.getInt("changeQty"),allTransactions.getDouble("amount"),allTransactions.getInt("stockRemaining"),allTransactions.getString("transactionType")));
        }
        return transactions;
    }
    public void close() throws SQLException {
        con.close();
    }
}
