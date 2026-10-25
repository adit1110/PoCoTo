package test;

import model.Player;
import model.Po;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Modernized tests based on the original CS2212 PlayerTest. */
class PlayerTest {
    private Player player;
    @BeforeEach void setup() { player = new Player(new Po("Bear1")); }

    @Test void testAddScore() {
        player.addScore(10);
        assertEquals(10, player.getScore());
    }
    @Test void testAddItemToInventory() {
        player.addItemToInventory("Health Potion");
        assertTrue(player.getInventory().hasItem("Health Potion"));
    }
    @Test void testUseItem() {
        player.addItemToInventory("Health Potion");
        player.useItem("Health Potion");
        assertFalse(player.getInventory().hasItem("Health Potion"));
    }
    @Test void testSessionTracking() throws Exception {
        player.startSession();
        Thread.sleep(20);
        player.endSession();
        assertTrue(player.getTotalSessionTime() >= 0);
    }
}
