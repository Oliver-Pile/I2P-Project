package src;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Database is a class for handling all interactions with the database.
 *
 * @author Oliver Pile
 */
public class Database {
    private static Connection con;
    private static Statement st;

    /**
     * Constructor that establishes a connection to the DB and ensures all tables have been created
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public Database(String name) throws SQLException {
        String url = String.format("jdbc:sqlite:%s.db",name);
        con = DriverManager.getConnection(url);
        st = con.createStatement();
        createTable();
    }

    /**
     * Creates the tables in the database for item and transactions
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    private void createTable() throws SQLException {
        st.execute("CREATE TABLE IF NOT EXISTS Items (itemID INTEGER PRIMARY KEY AUTOINCREMENT, desc TEXT NOT NULL, price REAL NOT NULL, quantity INTEGER NOT NULL, totalPrice REAL NOT NULL)");
        st.execute("CREATE TABLE IF NOT EXISTS Transactions (itemID INTEGER NOT NULL, desc TEXT NOT NULL, changeQty INTEGER NOT NULL, amount REAL NOT NULL, stockRemaining INTEGER NOT NULL, transactionType TEXT NOT NULL, date TEXT NOT NULL)");
    }

    /**
     * Inserts a new item into the items table of the DB. Then retrieves that added item to get its auto-created ID.
     * Then inserts a transaction report into the transaction table of the DB.
     * @param item The item to be added to the DB.
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public void add(Item item) throws SQLException {
        String toInsert = String.format("INSERT INTO Items (desc, price, quantity, totalPrice) values('%s', %.2f, %d, %.2f)",item.getDesc(),item.getPrice(),item.getQuantity(),item.getTotalPrice());
        st.execute(toInsert);
        ResultSet rs =st.executeQuery("SELECT last_insert_rowid()");
        int newID = rs.getInt("last_insert_rowid()");
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",newID, item.getDesc(), -1*item.getQuantity(), -1*item.getTotalPrice(),item.getQuantity(),"Add", LocalDate.now());
        st.execute(trans);
    }

    /**
     * Update the quantity of an item in the items table.
     * Inserts a transaction report into the transaction table.
     * @param item The item that is to be updated
     * @param qtyChange The new quantity to be used
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public void update(Item item, int qtyChange) throws SQLException {
        String toUpdate = String.format("UPDATE Items SET quantity=%d WHERE itemID=%d",item.getQuantity(),item.getID());
        st.execute(toUpdate);
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",item.getID(), item.getDesc(), qtyChange, qtyChange*item.getPrice(),item.getQuantity(),"Update",LocalDate.now());
        st.execute(trans);
    }

    /**
     * Deletes an item from the items table of the DB.
     * Inserts a transaction report into the transaction table.
     * @param item The item to be deleted
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public void delete(Item item) throws SQLException {
        String toDelete = String.format("DELETE from Items WHERE itemID=%d",item.getID());
        st.execute(toDelete);
        String trans = String.format("INSERT INTO Transactions (itemID, desc, changeQty, amount, stockRemaining, transactionType, date) values (%d, '%s', %d, %.2f, %d, '%s','%s')",item.getID(), item.getDesc(), item.getQuantity(), item.getTotalPrice(),0,"Remove",LocalDate.now());
        st.execute(trans);
    }

    /**
     * Gets all the records from the item table. Creates an item object for each record.
     * @return A list of all the items
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public LinkedList<Item> getItems() throws SQLException {
        LinkedList<Item> items = new LinkedList<>();
        ResultSet allItems = st.executeQuery("SELECT * FROM Items");
        while (allItems.next()){
            items.add(new Item(allItems));
        }
        return items;
    }

    /**
     * Gets all the records from the transaction table on a specific date and store each as a string.
     * @param date The date to search for records on
     * @return A list of each transaction
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     *
     */
    public LinkedList<String> getTransaction(String date) throws SQLException {
        LinkedList<String> transactions = new LinkedList<>();
        ResultSet allTransactions = st.executeQuery(String.format("SELECT * FROM Transactions WHERE date='%s'",date));
        while(allTransactions.next()){
            transactions.add(String.format("%05d,%s,%d,%.2f,%d,%s",allTransactions.getInt("itemID"),allTransactions.getString("desc"),allTransactions.getInt("changeQty"),allTransactions.getDouble("amount"),allTransactions.getInt("stockRemaining"),allTransactions.getString("transactionType")));
        }
        return transactions;
    }

    /**
     * Closes the database connection
     * @throws SQLException if there was an issue connecting to the DB. Handled by the store class.
     */
    public void close() throws SQLException {
        con.close();
    }
}
