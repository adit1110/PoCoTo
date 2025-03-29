package model;

import java.util.HashMap;
import java.util.Map;
import java.util.List; // added by Adit for the List<String> method
import java.util.ArrayList; // added by Adit for the List<String> method


/**
 * The Inventory class tracks the items and their counts for a player in the Bear game.
 * It provides methods to add, use, and check items in the inventory.
 * 
 * @author: Krish Patel
 */
public class Inventory {

    private static Map<String, Integer> items; // changed by Adit to include static to safely call it from the static method getItems()

    /**
     * Constructs an Inventory object with an empty list of items.
     */
    public Inventory() {
        items = new HashMap<>();
    }

    /**
     * Adds an item to the inventory. If the item already exists, it increments the count.
     *
     * @param item The item to be added.
     */
    public void addItem(String item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    /**
     * Uses an item from the inventory. If the item exists and has a count greater than 0,
     * it decrements the count. If the item is not found, no action is taken.
     *
     * @param item The item to be used.
     */
    public void useItem(String item) {
        if (items.containsKey(item) && items.get(item) > 0) {
            items.put(item, items.get(item) - 1);
        }
    }

    /**
     * Checks if an item exists in the inventory.
     *
     * @param item The item to check.
     * @return True if the item exists in the inventory, false otherwise.
     */
    public boolean hasItem(String item) {
        return items.containsKey(item) && items.get(item) > 0;
    }

    /**
     * Gets the number of items of a particular type in the inventory.
     *
     * @param item The item type to check.
     * @return The count of the specified item in the inventory.
     */
    public int getItemCount(String item) {
        return items.getOrDefault(item, 0);
    }

    /** Added by Adit to implement a static getItems() method
     * Returns a copy of the inventory items as a formatted list of strings.
     * 
     * @return A list of strings representating each item and its count
     */

     public static List<String> getItems() {
        List<String> itemList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: items.entrySet()) {
            itemList.add(entry.getKey() + ": " + entry.getValue());
        }
        return itemList;
     }
}
