package test;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import controller.GameController;
import model.Bear;
import model.Co;
import model.Inventory;
import model.Po;

/** Regression checks for finite per-bear inventories and persistence. */
public class InventoryRegressionTest {
    @TempDir Path tempDir;

    @Test void starterItemsAreAvailable() {
        Inventory inv = new Po("Po").getInventory();
        assertEquals(3, inv.getItemCount("Apple"));
        assertEquals(2, inv.getItemCount("Banana"));
        assertEquals(1, inv.getItemCount("Teddy Bear"));
    }

    @Test void consumingStopsAtZero() {
        Inventory inv = new Inventory();
        inv.addItem("Apple", 2);
        assertTrue(inv.useItem("Apple"));
        assertTrue(inv.useItem("Apple"));
        assertFalse(inv.useItem("Apple"));
        assertEquals(0, inv.getItemCount("Apple"));
    }

    @Test void inventoriesAreIndependent() {
        Po first = new Po("First");
        Co second = new Co("Second");
        first.getInventory().useItem("Apple");
        assertEquals(2, first.getInventory().getItemCount("Apple"));
        assertEquals(3, second.getInventory().getItemCount("Apple"));
    }

    @Test void addingQuantitiesWorks() {
        Inventory inv = new Inventory();
        inv.addItem("Teddy Bear");
        inv.addItem("Teddy Bear", 2);
        assertEquals(3, inv.getItemCount("Teddy Bear"));
        assertThrows(IllegalArgumentException.class, () -> inv.addItem("Apple", 0));
    }

    @Test void inventorySurvivesSaveAndLoad() {
        Bear bear = new Po("Po");
        bear.getInventory().useItem("Apple");
        bear.getInventory().addItem("Teddy Bear", 2);
        Path save = tempDir.resolve("inventory.dat");
        assertTrue(GameController.saveGame(bear, save));
        Bear loaded = GameController.loadGame(save);
        assertNotNull(loaded);
        assertEquals(2, loaded.getInventory().getItemCount("Apple"));
        assertEquals(3, loaded.getInventory().getItemCount("Teddy Bear"));
    }

    @Test void foodAndGiftsAffectDifferentStats() {
        Bear bear = new Po("Po");
        bear.setHunger(40);
        bear.setHappiness(50);
        Inventory inv = bear.getInventory();
        assertTrue(inv.useItem("Apple"));
        bear.feed(10);
        assertEquals(50, bear.getHunger());
        assertEquals(50, bear.getHappiness());
        assertTrue(inv.useItem("Teddy Bear"));
        bear.play(20);
        assertEquals(70, bear.getHappiness());
    }
}
