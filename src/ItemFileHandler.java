package src;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class ItemFileHandler {
    private final File itemFile = new File("src/items.txt");


    public LinkedList<Item> readLines() {
        try {
            LinkedList<Item> items = new LinkedList<>();
            Scanner reader = new Scanner(itemFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] splitLine = line.split(",");
                items.add(new Item(splitLine));
            }
            reader.close();
            return items;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean add(String toAppend){
        try {
            FileWriter fw = new FileWriter(itemFile,true);
            fw.write(toAppend);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}