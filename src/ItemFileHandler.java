package src;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class ItemFileHandler {
    private final File itemFile = new File("src/items.txt");


    public LinkedList<String[]> readLines() {
        try {
            LinkedList<String[]> lines = new LinkedList<>();
            Scanner reader = new Scanner(itemFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] splitLine = line.split(",");
                lines.add(splitLine);

            }
            reader.close();
            return lines;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}