package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Inventory class tracks the items and their counts for a player in the Bear game.
 * It provides methods to add, use, and check items in the inventory.
 * 
 * @author: Krish Patel
 */
public class Inventory {

    private Map<String, Integer> items;

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
}
