/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import java.util.Set;

public class Inventory {
    Set<Item> Inventory;

    public Inventory(Set<Item> inventory) {
        Inventory = inventory;
    }

    public Set<Item> getInventory() {
        return Inventory;
    }

    public void setInventory(Set<Item> inventory) {
        Inventory = inventory;
    }

    public void addItem(Item item){
        this.getInventory().add(item);
    }

    public void removeItem(Item item){
        this.getInventory().remove(item);
    }
}
