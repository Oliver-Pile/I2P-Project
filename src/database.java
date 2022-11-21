package src;
import java.sql.*;

public class database {
    static Connection con;
    static Statement st;
    public static void main(String[] args) throws Exception {
        //Registering the Driver
        //DriverManager.registerDriver(new org.sqlite.JDBC());
        //Getting the connection
        String url = "jdbc:sqlite:src/storeDB.db";
        con = DriverManager.getConnection(url);
        System.out.println("Connection established......");
        st = con.createStatement();
        createTable();
        insert();
    }

    private static void createTable() throws SQLException {
        String toEx = "CREATE TABLE IF NOT EXISTS Items (itemID INTEGER PRIMARY KEY AUTOINCREMENT, desc TEXT NOT NULL, price REAL NOT NULL, quantity INTEGER NOT NULL, totalPrice REAL NOT NULL)";
        st.execute(toEx);
    }

    private static void insert() throws SQLException {
        String toEX = "INSERT INTO Items (desc, price, quantity, totalPrice) values('Test item number 2', 8, 100, 800)";
        st.execute(toEX);
    }

}
