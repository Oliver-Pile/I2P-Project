package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class TransactionFileHandler {
    private final File transactionFile= new File("src/transactions.txt");

    public TransactionFileHandler(){
        try {
            new FileWriter(transactionFile, false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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

    public LinkedList<String> readLines() {
        try {
            LinkedList<String> lines = new LinkedList<>();
            Scanner reader = new Scanner(transactionFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lines.add(line);
            }
            reader.close();
            return lines;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
