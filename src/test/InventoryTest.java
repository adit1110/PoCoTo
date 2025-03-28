package test;

import model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Krish Patel
 * Unit tests for the Inventory class.
 * This class verifies the functionality of methods in the Inventory class.
 * It tests adding, using, and checking items in the inventory.
 */
class InventoryTest {

    private Inventory inventory;

    /**
     * Sets up a fresh Inventory object before each test.
     */
    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    /**
     * Tests that an item can be added to the inventory.
     */
    @Test
    void testAddItem() {
        inventory.addItem("Apple");
        assertTrue(inventory.hasItem("Apple"), "Inventory should contain the added item.");
    }

    /**
     * Tests that an item can be used from the inventory.
     */
    @Test
    void testUseItem() {
        inventory.addItem("Apple");
        inventory.useItem("Apple");
        assertFalse(inventory.hasItem("Apple"), "Item should be removed from inventory after use.");
    }

    /**
     * Tests that hasItem correctly identifies if an item is in the inventory.
     */
    @Test
    void testHasItem() {
        inventory.addItem("Toy");
        assertTrue(inventory.hasItem("Toy"), "Inventory should confirm the item is present.");
    }

    /**
     * Tests that trying to use an item not in the inventory does nothing.
     */
    @Test
    void testUseNonExistentItem() {
        inventory.useItem("Potion");
        assertFalse(inventory.hasItem("Potion"), "Non-existent item should not be in inventory.");
    }
}
