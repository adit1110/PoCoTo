package test;

import model.ParentalControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * By: Krish Patel
 * Unit tests for the ParentalControl class.
 * This class verifies the functionality of methods in the ParentalControl class.
 * It tests the functionality of playtime limit, character revival toggle, and user statistics.
 */
class ParentalControlTest {

    private ParentalControl parentalControl;

    /**
     * Sets up a fresh ParentalControl object before each test.
     */
    @BeforeEach
    void setUp() {
        parentalControl = new ParentalControl();
    }

    /**
     * Tests that the playtime limit can be set correctly.
     */
    @Test
    void testSetPlaytimeLimit() {
        parentalControl.setPlaytimeLimit(60); // Limit set to 60 minutes
        assertEquals(60, parentalControl.getPlaytimeLimit(), "Playtime limit should be correctly set.");
    }

    /**
     * Tests that the character revival toggle can be enabled and disabled.
     */
    @Test
    void testCharacterRevivalToggle() {
        parentalControl.toggleCharacterRevival(true);
        assertTrue(parentalControl.isCharacterRevivalEnabled(), "Character revival should be enabled.");
        
        parentalControl.toggleCharacterRevival(false);
        assertFalse(parentalControl.isCharacterRevivalEnabled(), "Character revival should be disabled.");
    }

    /**
     * Tests that the user's statistics are correctly retrieved.
     */
    @Test
    void testGetUserStatistics() {
        parentalControl.setPlaytimeLimit(60);
        parentalControl.toggleCharacterRevival(true);
        
        String stats = parentalControl.getUserStatistics();
        assertTrue(stats.contains("Playtime Limit: 60"), "Statistics should contain the correct playtime limit.");
        assertTrue(stats.contains("Character Revival: Enabled"), "Statistics should contain the correct revival status.");
    }

    /**
     * Tests the behavior of password protection (assuming a method for password checking).
     */
    @Test
    void testPasswordProtection() {
        parentalControl.setPassword("test123");
        assertTrue(parentalControl.checkPassword("test123"), "Password should match and grant access.");
        assertFalse(parentalControl.checkPassword("wrongpass"), "Incorrect password should deny access.");
    }
}
