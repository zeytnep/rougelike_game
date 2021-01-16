package rogue;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

    private HashMap<Item, String> inventory;
    private ArrayList<Item> listOfItems;
    /**
    * Constructor that initializes a new arraylist of items that will hold my items.
    */
    public Inventory() {
        inventory = new HashMap<>();
        listOfItems = new ArrayList<>();
    }

    /**
    * Add item to inventory and its functionality to the inventory.
    * @param item to add to inventory
    * @param function functionality of an item
    */
    public void addToInventory(Item item, String function) {
        inventory.put(item, function);
        listOfItems.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
        listOfItems.remove(item);
    }

    public int inventorySize() {
        return inventory.size();
    }

    public int inventoryListSize() {
        return listOfItems.size();
    }

    public HashMap<Item, String> getInventory() { //returns a hasmap named inventory
        return inventory;
    }

    public ArrayList<Item> getInventoryList() { //returns arraylist inventory
        return listOfItems;
    }

    public boolean isinvListEmpty() {
        if (listOfItems.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isInventoryEmpty() {
        if (inventory.isEmpty()) { //returns true if inventory is empty
            return true;
        }
        return false; //return false if inventory has items in it
    }

}
