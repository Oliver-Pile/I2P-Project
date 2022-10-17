import java.io.*;
import java.util.Scanner;

public class ItemFileHandler {
    private final File itemFile = new File("items.txt");


    public void read() {
        try {
            Scanner reader = new Scanner(itemFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.printf("Line = %s%n", line);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}