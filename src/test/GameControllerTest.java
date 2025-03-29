package test.controller;

import model.Bear;
import model.Po;
import controller.GameController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GameControllerTest.java
 * Unit tests for save/load functionality and state management
 * @author: Jayansh Bagga
 */
public class GameControllerTest {

    /**
     * Tests whether GameController properly stores and retrieves current bear
     */
    @Test
    public void testSetAndGetCurrentBear() {
        Bear bear = new Po("Po");
        GameController.setCurrentBear(bear);
        assertEquals("Po", GameController.getCurrentBear().getName(),
            "Expected bear name 'Po' to be returned by getCurrentBear()");
    }

    /**
     * Tests saving and loading a bear, and ensures hunger state is preserved
     */
    @Test
    public void testSaveAndLoadBear() {
        Bear bear = new Po("Po");
        bear.feed(-40); // Simulate change in hunger
        GameController.setCurrentBear(bear);
        GameController.saveGame(bear); // bear added into brackets by Adit to help tests run

        Bear loaded = GameController.loadGame();
        assertNotNull(loaded, "Loaded bear should not be null");
        assertEquals(bear.getName(), loaded.getName(), "Loaded bear name should match original");
        assertEquals(bear.getHunger(), loaded.getHunger(), "Loaded bear hunger should match original");
    }
}
