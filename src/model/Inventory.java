package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Per-bear inventory with finite, serializable item counts. */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, Integer> items = new HashMap<>();

    public static Inventory starterInventory() {
        Inventory inventory = new Inventory();
        inventory.addItem("Apple", 3);
        inventory.addItem("Banana", 2);
        inventory.addItem("Teddy Bear", 1);
        return inventory;
    }

    public void addItem(String item) { addItem(item, 1); }

    public void addItem(String item, int amount) {
        if (item == null || item.isBlank() || amount <= 0) {
            throw new IllegalArgumentException("Item name and positive quantity are required");
        }
        items.merge(item, amount, Integer::sum);
    }

    /** Returns false if none are available; never permits negative counts. */
    public boolean useItem(String item) {
        int count = getItemCount(item);
        if (count == 0) return false;
        if (count == 1) items.remove(item);
        else items.put(item, count - 1);
        return true;
    }

    public boolean hasItem(String item) { return getItemCount(item) > 0; }
    public int getItemCount(String item) { return items.getOrDefault(item, 0); }

    public List<String> getItems() {
        List<String> result = new ArrayList<>();
        items.forEach((item, count) -> result.add(item + ": " + count));
        result.sort(String::compareTo);
        return result;
    }
}
