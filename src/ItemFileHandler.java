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
                items.add(new Item(line));
            }
            reader.close();
            return items;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean add(Item item){
        try {
            FileWriter fw = new FileWriter(itemFile,true);
            fw.write(item.getFileString());
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update(Item itemToUpdate){
        /*
        Param: New item object with updated params
        Get current lines
        For each line
            Check if line ID == ID of current Item
            If no, write line to new file (temp file then rename)
            If yes, write new item object (from params) into new file
        Once all lines complete, rename temp file to items.txt to complete.
         */
        File tempFile = new File("src/tempItem.txt");
        LinkedList<Item> allItems = readLines();
        try {
            FileWriter fw = new FileWriter(tempFile);
            for(Item item : allItems){
                if (item.getID() == itemToUpdate.getID()) {
                    fw.write(itemToUpdate.getFileString());
                }
                else{
                    fw.write(item.getFileString());
                }
            }
            itemFile.delete();
            tempFile.renameTo(itemFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}