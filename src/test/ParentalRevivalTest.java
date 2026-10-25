package test;

import controller.GameController;
import model.Bear;
import model.Co;
import model.Po;
import model.To;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.ParentalSettings;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/** Regression coverage for parental revival, independent of the UI and real settings. */
class ParentalRevivalTest {
    @TempDir Path temporaryFolder;

    private ParentalSettings settings() {
        return new ParentalSettings(temporaryFolder.resolve("parental_test.properties"));
    }

    @Test void disabledRevivalCannotResurrectDeadBear() {
        ParentalSettings policy = settings();
        Bear bear = new Po("Po");
        bear.setHealth(0);
        policy.configure(-1, false);

        assertFalse(policy.canRevive(bear));
        assertFalse(policy.revive(bear));
        assertTrue(bear.isDead());
        assertEquals(0, bear.getHealth());
    }

    @Test void enabledRevivalRestoresDeadPoStats() {
        ParentalSettings policy = settings();
        Bear bear = new Po("Po");
        bear.setHealth(0);
        bear.setHunger(0);
        bear.setSleep(5);
        bear.setHappiness(10);
        policy.configure(-1, true);

        assertTrue(policy.canRevive(bear));
        assertTrue(policy.revive(bear));
        assertFalse(bear.isDead());
        assertEquals(100, bear.getHealth());
        assertEquals(100, bear.getHunger());
        assertEquals(100, bear.getSleep());
        assertEquals(100, bear.getHappiness());
    }

    @Test void livingBearCannotBeRevived() {
        ParentalSettings policy = settings();
        Bear bear = new Po("Po");
        bear.setHealth(30);
        policy.configure(-1, true);

        assertFalse(policy.canRevive(bear));
        assertFalse(policy.revive(bear));
        assertEquals(30, bear.getHealth());
    }

    @Test void allThreeBearTypesCanBeRevived() {
        ParentalSettings policy = settings();
        policy.configure(-1, true);
        for (Bear bear : new Bear[] {new Po("Po"), new Co("Co"), new To("To")}) {
            bear.setHealth(0);
            assertTrue(policy.revive(bear), bear.getName() + " should revive");
            assertFalse(bear.isDead());
        }
    }

    @Test void disabledPolicyPersistsAfterRestart() {
        Path path = temporaryFolder.resolve("persisted.properties");
        ParentalSettings first = new ParentalSettings(path);
        first.configure(60, false);
        ParentalSettings reopened = new ParentalSettings(path);
        Bear bear = new To("To");
        bear.setHealth(0);

        assertFalse(reopened.isRevivalEnabled());
        assertFalse(reopened.revive(bear));
        assertTrue(bear.isDead());
    }

    @Test void loadedDeadBearRespectsRevivalPolicy() {
        ParentalSettings policy = settings();
        Path save = temporaryFolder.resolve("dead_bear.dat");
        Bear deadBear = new Co("Co");
        deadBear.setHealth(0);
        assertTrue(GameController.saveGame(deadBear, save));
        Bear loaded = GameController.loadGame(save);
        assertNotNull(loaded);
        assertTrue(loaded.isDead());

        policy.configure(-1, false);
        assertFalse(policy.revive(loaded));
        assertTrue(loaded.isDead());
        policy.configure(-1, true);
        assertTrue(policy.revive(loaded));
        assertEquals(100, loaded.getHealth());
    }

    @Test void nullBearIsNotRevivable() {
        ParentalSettings policy = settings();
        policy.configure(-1, true);
        assertFalse(policy.canRevive(null));
        assertFalse(policy.revive(null));
    }
}