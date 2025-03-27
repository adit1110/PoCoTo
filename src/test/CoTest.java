/**
 * By: Adit Bhimani
 * Unit tests for the Co (Polar Bear) class.
 * Verifies stat update and hunger-related health loss.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Co;

class CoTest {

    private Co co;

    /**
     * Sets up a new Co instance before each test.
     */
    @BeforeEach
    void setUp() {
        co = new Co("Co");
    }

    /**
     * Tests if stats decrease properly after a single update cycle.
     */
    @Test
    void testUpdateStats() {
        co.updateStats();
        assertEquals(98, co.getHunger());
        assertEquals(98, co.getSleep());
        assertEquals(99, co.getHappiness());
    }

    /**
     * Verifies that health drops after many hunger-decreasing updates.
     */
    @Test
    void testHealthDropsWhenHungry() {
        for (int i = 0; i < 50; i++) co.updateStats();
        assertTrue(co.getHealth() < 100);
    }
}