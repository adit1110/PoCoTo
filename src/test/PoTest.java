/**
 * @author: Adit Bhimani
 * Unit tests for the Po (Panda Bear) class.
 * Verifies stat changes and state transitions.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Po;

class PoTest {

    private Po po;

    /**
     * Sets up a fresh Po instance before each test.
     */
    @BeforeEach
    void setUp() {
        po = new Po("Po");
    }

    /**
     * Checks that all stats are initialized to 100.
     */
    @Test
    void testInitialStats() {
        assertEquals(100, po.getHunger());
        assertEquals(100, po.getHealth());
        assertEquals(100, po.getSleep());
        assertEquals(100, po.getHappiness());
    }

    /**
     * Verifies that updateStats() decreases stats according to Po’s behavior.
     */
    @Test
    void testUpdateStatsDropsCorrectly() {
        po.updateStats();
        assertEquals(96, po.getHunger());
        assertEquals(100, po.getHealth());
        assertEquals(98, po.getSleep());
        assertEquals(98, po.getHappiness());
    }

    /**
     * Tests whether feeding after stat drop restores hunger to max.
     */
    @Test
    void testFeedIncreasesHunger() {
        po.updateStats();
        po.feed(5);
        assertEquals(100, po.getHunger());
    }

    /**
     * Verifies Po becomes angry after many updates, but not dead yet.
     */
    @Test
    void testBecomesAngryAfterSufficientDecay() {
        for (int i = 0; i < 38; i++) {
            po.updateStats();
        }

        assertTrue(po.isAngry(),
            "Po should become angry when Happiness reaches 25 or below");
    }

    @Test
    void testHealthEventuallyReachesZero() {
        for (int i = 0; i < 80; i++) {
            po.updateStats();
        }

        assertTrue(po.isDead(),
            "Po should eventually die when Health reaches zero");
    }
}