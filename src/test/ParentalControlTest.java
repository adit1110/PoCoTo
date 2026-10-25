package test;

import model.Po;
import model.ParentalControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Modernized CS2212 parental control checks. */
class ParentalControlTest {
    private ParentalControl parentalControl;
    @BeforeEach void setup() { parentalControl = new ParentalControl(new Po("Bear1")); }

    @Test void testSetPlaytimeLimit() {
        parentalControl.setPlaytimeLimit(60);
        assertEquals(60, parentalControl.getPlaytimeLimit());
    }
    @Test void testCharacterRevivalToggle() {
        parentalControl.toggleCharacterRevival(true);
        assertTrue(parentalControl.isCharacterRevivalEnabled());
        parentalControl.toggleCharacterRevival(false);
        assertFalse(parentalControl.isCharacterRevivalEnabled());
    }
    @Test void testGetUserStatistics() {
        parentalControl.setPlaytimeLimit(60);
        parentalControl.toggleCharacterRevival(true);
        String stats = parentalControl.getUserStatistics();
        assertTrue(stats.contains("Playtime Limit: 60"));
        assertTrue(stats.contains("Character Revival: Enabled"));
    }
    @Test void testPasswordProtection() {
        parentalControl.setPassword("test123");
        assertTrue(parentalControl.checkPassword("test123"));
        assertFalse(parentalControl.checkPassword("wrongpass"));
    }
    @Test void testReviveOnlyWhenDeadAndEnabled() {
        Po bear = new Po("Bear1");
        ParentalControl controls = new ParentalControl(bear);
        bear.setHealth(0);
        controls.setRevivalEnabled(false);
        assertFalse(controls.canReviveBear());
        controls.reviveBear();
        assertEquals(0, bear.getHealth());
        controls.setRevivalEnabled(true);
        assertTrue(controls.canReviveBear());
        controls.reviveBear();
        assertEquals(100, bear.getHealth());
    }
}
