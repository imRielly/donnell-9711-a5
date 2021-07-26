/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import java.util.HashSet;
import java.util.Set;

public class Inventory {
    Set<Item> item;

    public Inventory(){
        this.item = new HashSet<>();
    }

    public Inventory(Set<Item> inventory) {
        this.item = inventory;
    }

    public Set<Item> getInventoryItems() {
        return item;
    }

    public void setInventory(Set<Item> inv) {
        item = inv;
    }

    public void addItem(Item item){
        this.getInventoryItems().add(item);
    }

    public void removeItem(Item item){
        this.getInventoryItems().remove(item);
    }

    public void removeItemBySN(String sn) {
        this.getInventoryItems().removeIf(i -> sn.equalsIgnoreCase(i.serialNumber.getValueSafe()));
    }
}
