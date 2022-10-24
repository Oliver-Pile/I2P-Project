import java.io.*;
import java.util.Scanner;

public class ItemFileHandler {
    private final File itemFile = new File("items.txt");


    public void read() {
        try {
            Scanner reader = new Scanner(itemFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] splitLine = line.split(",");
                System.out.printf("ID: %s, Description: %s, Unit Price: %s, Quantity in Stock: %s, Total price: %s%n",splitLine[0],splitLine[1],splitLine[2],splitLine[3],splitLine[4]);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}