package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.ParentalSettings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fast tests for playtime lockout, without waiting an hour.
 * Each test uses a temporary settings file and never touches real user history.
 */
class ParentalPlaytimeLimitTest {
    @TempDir Path temp;

    private ParentalSettings withPlaytime(long seconds, int limitMinutes) throws IOException {
        Path file = temp.resolve("parental_controls.properties");
        Properties properties = new Properties();
        properties.setProperty("playedSeconds", Long.toString(seconds));
        properties.setProperty("limitMinutes", Integer.toString(limitMinutes));
        properties.setProperty("revivalEnabled", "true");
        properties.setProperty("sessions", "4");
        try (OutputStream output = Files.newOutputStream(file)) {
            properties.store(output, "isolated JUnit test settings");
        }
        return new ParentalSettings(file);
    }

    @Test
    void allowsGameplayJustBeforeOneHourLimit() throws IOException {
        ParentalSettings settings = withPlaytime(3599, 60);
        assertTrue(settings.canPlay(), "3599 seconds should still be allowed");
    }

    @Test
    void blocksGameplayAtExactLimit() throws IOException {
        ParentalSettings settings = withPlaytime(3600, 60);
        assertFalse(settings.canPlay(), "3600 seconds must trigger lockout");
    }

    @Test
    void blocksGameplayAfterLimitAndAfterReload() throws IOException {
        ParentalSettings settings = withPlaytime(3601, 60);
        assertFalse(settings.canPlay());
        ParentalSettings reopened = new ParentalSettings(temp.resolve("parental_controls.properties"));
        assertFalse(reopened.canPlay(), "Restarting must not bypass lockout");
    }

    @Test
    void blockedPlayerCannotStartAnotherSession() throws IOException {
        ParentalSettings settings = withPlaytime(3600, 60);
        long sessionsBefore = settings.getSessions();
        settings.beginSession();
        assertEquals(sessionsBefore, settings.getSessions(),
                "Lockout must prevent starting another gameplay session");
    }

    @Test
    void switchingToUnlimitedUnlocksGameplay() throws IOException {
        ParentalSettings settings = withPlaytime(3600, 60);
        assertFalse(settings.canPlay());
        settings.configure(-1, true);
        assertTrue(settings.canPlay());
        assertTrue(new ParentalSettings(temp.resolve("parental_controls.properties")).canPlay());
    }

    @Test
    void changingToLongerLimitUnlocksUntilThatLimit() throws IOException {
        ParentalSettings settings = withPlaytime(3600, 60);
        assertFalse(settings.canPlay());
        settings.configure(120, true);
        assertTrue(settings.canPlay());
        settings.configure(60, true);
        assertFalse(settings.canPlay());
    }

    @Test
    void unlimitedAllowsAlreadyLongPlaytime() throws IOException {
        ParentalSettings settings = withPlaytime(999999, -1);
        assertTrue(settings.canPlay());
    }
}
