/**
 * @author: Adit Bhimani
 * Unit tests for the To (Grizzly Bear) class.
 * Focuses on stat decay and emotional impact on health.
 */


package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.To;

class ToTest {

    private To to;

    /**
     * Sets up a new To instance before each test.
     */
    @BeforeEach
    void setUp() {
        to = new To("To");
    }

    /**
     * Tests initial stat decay from one update cycle.
     */
    @Test
    void testUpdateStats() {
        to.updateStats();
        assertEquals(97, to.getHunger());
        assertEquals(98, to.getSleep());
        assertEquals(96, to.getHappiness());
    }

    /**
     * Verifies that anger state causes health to drop over time.
     */
    @Test
    void testAngerCausesHealthDrop() {
        for (int i = 0; i < 25; i++) to.updateStats();
        assertTrue(to.getHealth() < 100);
        assertTrue(to.isAngry());
    }
}