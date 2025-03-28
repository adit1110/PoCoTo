package test;

import model.Player;
import model.Po; // added by Adit to help fix the tests not working
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * By: Krish Patel
 * Unit tests for the Player class.
 * This class verifies the functionality of methods in the Player class.
 * It tests the functionality of adding score, using inventory, and tracking session time.
 */
class PlayerTest {

    private Player player;

    /**
     * Sets up a fresh Player object before each test.
     */
    @BeforeEach
    void setUp() {
        // Assuming that Player requires a Bear object in the constructor
        player = new Player(new Po("Bear1"));
    }

    /**
     * Tests the addScore method to ensure that points are correctly added to the player's score.
     */
    @Test
    void testAddScore() {
        player.addScore(10);
        assertEquals(10, player.getScore(), "Score should be updated correctly after adding points.");
    }

    /**
     * Tests the addItemToInventory method to ensure an item can be added to the player's inventory.
     */
    @Test
    void testAddItemToInventory() {
        player.addItemToInventory("Health Potion");
        assertTrue(player.getInventory().hasItem("Health Potion"), "Item should be added to inventory.");
    }

    /**
     * Tests the useItem method to ensure an item can be used from the player's inventory.
     */
    @Test
    void testUseItem() {
        player.addItemToInventory("Health Potion");
        player.useItem("Health Potion");
        // Assuming that using an item would remove it from the inventory
        assertFalse(player.getInventory().hasItem("Health Potion"), "Item should be used and removed from inventory.");
    }

    /**
     * Tests the session time tracking functionality by starting and ending a session.
     */
    @Test
    void testSessionTracking() {
        player.startSession();
        try {
            Thread.sleep(1000); // Simulate 1 second of session time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        player.endSession();
        assertTrue(player.getTotalSessionTime() >= 1000, "Total session time should be at least 1 second.");
    }
}
