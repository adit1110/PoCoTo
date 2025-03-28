/**
 * @author: Adit Bhimani
 * 
 * This test class verifies the functionality of the BearController class.
 * It ensures that bear actions correctly affect the bear's state and that 
 * stat accessors return expected values.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.BearController;
import model.Po;

/**
 * Unit tests for the BearController class using a Po (Panda) bear instance.
 */
class BearControllerTest {

    private BearController controller;

    /**
     * Initializes a new BearController with a fresh Po instance before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new BearController(new Po("Po"));
    }

    /**
     * Tests that feeding the bear restores hunger after stat decay.
     */
    @Test
    void testFeedBear() {
        controller.updateBear(); // Drop hunger first (simulate time passing)
        controller.feedBear();   // Feed to restore hunger
        assertTrue(controller.getHungerPercent() > 0.95, "Hunger should be nearly full after feeding");
    }

    /**
     * Tests getter methods for health and death state.
     */
    @Test
    void testGetters() {
        assertEquals(1.0, controller.getHealthPercent(), "Health should be full at start");
        assertFalse(controller.isDead(), "Bear should not be dead at initialization");
    }
}