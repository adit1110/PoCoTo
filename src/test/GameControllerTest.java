package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import controller.GameController;
import model.Bear;
import model.Po;

/**
 * GameControllerTest.java
 * Unit tests for save/load functionality and state management
 * Created by Jayansh Bagga
 */
public class GameControllerTest {

    /**
     * Test whether setCurrentBear() correctly stores the bear
     */
    @Test
    public void testSetAndGetCurrentBear() {
        Bear bear = new Po("Po");
        GameController.setCurrentBear(bear);
        assertEquals("Po", GameController.getCurrentBear().getName(),
            "Expected bear name 'Po' to be returned by getCurrentBear()");
    }

    /**
     * Test saving a bear and loading it back from file
     * Ensures hunger value is retained
     */
    @Test
    public void testSaveAndLoadBear() {
        Bear bear = new Po("Po");
        bear.feed(-40); // Reduce hunger to simulate change
        GameController.setCurrentBear(bear);
        GameController.saveGame();

        Bear loaded = GameController.loadGame();
        assertNotNull(loaded, "Loaded bear should not be null");
        assertEquals(bear.getName(), loaded.getName(), "Loaded bear name should match original");
        assertEquals(bear.getHunger(), loaded.getHunger(), "Loaded bear hunger should match original");
    }
}
