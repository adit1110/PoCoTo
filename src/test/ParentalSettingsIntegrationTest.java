package test;

import model.Po;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.ParentalSettings;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class ParentalSettingsIntegrationTest {
    @TempDir Path temp;

    @Test void settingsSurviveRestart() {
        Path file = temp.resolve("parental.properties");
        ParentalSettings first = new ParentalSettings(file);
        first.configure(120, false);
        ParentalSettings reopened = new ParentalSettings(file);
        assertEquals(120, reopened.getLimitMinutes());
        assertFalse(reopened.isRevivalEnabled());
    }

    @Test void forbidsInvalidLimits() {
        ParentalSettings settings = new ParentalSettings(temp.resolve("parental.properties"));
        assertThrows(IllegalArgumentException.class, () -> settings.configure(3, true));
    }

    @Test void revivePolicyEnforced() {
        ParentalSettings settings = new ParentalSettings(temp.resolve("parental.properties"));
        Po bear = new Po("Po");
        bear.setHealth(0);
        settings.configure(60, false);
        assertFalse(settings.revive(bear));
        assertTrue(bear.isDead());
        settings.configure(60, true);
        assertTrue(settings.revive(bear));
        assertFalse(bear.isDead());
    }

    @Test void sessionCountPersists() {
        Path file = temp.resolve("parental.properties");
        ParentalSettings settings = new ParentalSettings(file);
        settings.beginSession();
        settings.endSession();
        assertEquals(1, new ParentalSettings(file).getSessions());
    }
}
