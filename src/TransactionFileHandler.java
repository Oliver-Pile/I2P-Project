package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionFileHandler {
    private final File transactionFile = new File("src/transactions.txt");


    public void add(String detailsToWrite){
        try {
            FileWriter fw = new FileWriter(transactionFile,true);
            fw.write(detailsToWrite);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getFileString(int ID, String desc, int sold, double amount, int stockRemaining, String type ){
        return String.format("%05d,%s,%s,%.2f,%s,%s%n",ID,desc,sold,amount,stockRemaining,type);
    }
}
