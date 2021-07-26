/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AppModel {
    Inventory inventory;

    public AppModel() {
        this.inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getTSV() {
        StringBuilder result = new StringBuilder();

        result.append("Name\tSerialNumber\tValue\n");

        for (Item i :
                getInventory().getInventoryItems()) {
            result.append(i.getName()).append("\t").append(i.getSerialNumber()).append("\t").append(i.getValue()).append("\n");
        }

        return String.valueOf(result);
    }

    public String getHTML() {
        StringBuilder result = new StringBuilder();

        result.append("<html><head><title>Inventory Manager</title></head><body><table style = \"border: 1px solid black;min-width: 500px; padding: 15px;\">");
        result.append("<tr><th>Name</th><th>SerialNumber</th><th>Value</th></tr>");

        for (Item i :
                getInventory().getInventoryItems()) {
            result.append("<tr><td>").append(i.getName()).append("</td><td>").append(i.getSerialNumber()).append("</td><td>").append(i.getValue()).append("</td></tr>");
        }

        result.append("</table></body></html>");

        return String.valueOf(result);
    }

    public String getJSON() {
        return null;
    }

    public void loadFile(File file) {
        String fileType = file.getName().substring(file.getName().length()-4);
        if (fileType.equalsIgnoreCase(".txt")){
            loadTSV(file);
        }else if(fileType.equalsIgnoreCase("html")){
            loadHTML(file);
        }else if (fileType.equalsIgnoreCase("json")){
            loadJSON(file);
        }else System.out.println("No file selected");
    }

    private void loadTSV(File file) {
        try (BufferedReader brFile = new BufferedReader(new FileReader(file))) {
            Inventory loaded = new Inventory();
            String data = null;
            while((data = brFile.readLine()) != null){
                String[] values = data.split("\t");
                if (values.length >= 2){
                    String name = values[0];
                    String sn = values[1];
                    try {
                        Double value = Double.parseDouble(values[2]);
                        Item loadItem = new Item(name, sn, value);
                        loaded.addItem(loadItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            inventory = loaded;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHTML(File file) {
        try (BufferedReader brFile = new BufferedReader(new FileReader(file))) {
            Inventory loaded = new Inventory();
            String data = brFile.readLine();
            data = data.replaceAll("<html><head><title>Inventory Manager</title></head><body><table style = \"border: 1px solid black;min-width: 500px; padding: 15px;\"><tr><th>Name</th><th>SerialNumber</th><th>Value</th></tr>", "");
            data = data.replaceAll("<tr>","");
            data = data.replaceAll("<td>","");
            data = data.replaceAll("</table></body></html>","");
            String[] lines = data.split("</tr>");
            for (String line : lines) {
                String[] values = line.split("</td>");
                String name = values[0];
                String sn = values[1];
                try {
                    Double value = Double.parseDouble(values[2]);
                    Item loadItem = new Item(name, sn, value);
                    loaded.addItem(loadItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            inventory = loaded;
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private void loadJSON(File file) {

    }
}
