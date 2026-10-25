package test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import controller.GameController;
import model.Bear;
import model.Po;
import model.Co;
import model.To;

/** Regression tests for GameController's Java-serialization save format. */
public class SaveLoadRegressionTest {
    @TempDir Path tempDir;

    private Path savePath() {
        return tempDir.resolve("bear_save.dat");
    }

    @Test
    void savingCreatesNonEmptyFile() throws Exception {
        assertTrue(GameController.saveGame(new Po("Po"), savePath()));
        assertTrue(Files.exists(savePath()));
        assertTrue(Files.size(savePath()) > 0);
    }

    @Test
    void loadingRestoresAllStatsAndName() {
        Po bear = new Po("Panda One");
        bear.setHunger(31);
        bear.setHealth(46);
        bear.setSleep(57);
        bear.setHappiness(68);
        assertTrue(GameController.saveGame(bear, savePath()));

        Bear loaded = GameController.loadGame(savePath());
        assertNotNull(loaded);
        assertInstanceOf(Po.class, loaded);
        assertEquals("Panda One", loaded.getName());
        assertAll(
            () -> assertEquals(31, loaded.getHunger()),
            () -> assertEquals(46, loaded.getHealth()),
            () -> assertEquals(57, loaded.getSleep()),
            () -> assertEquals(68, loaded.getHappiness())
        );
    }

    @Test
    void restoresEveryBearType() {
        Bear[] types = {new Po("Po"), new Co("Co"), new To("To")};
        for (Bear bear : types) {
            assertTrue(GameController.saveGame(bear, savePath()));
            Bear loaded = GameController.loadGame(savePath());
            assertNotNull(loaded);
            assertEquals(bear.getClass(), loaded.getClass());
        }
    }

    @Test
    void missingSaveReturnsNull() {
        assertNull(GameController.loadGame(tempDir.resolve("missing.dat")));
    }

    @Test
    void saveOverwritesPreviousBear() {
        assertTrue(GameController.saveGame(new Po("First"), savePath()));
        Co newest = new Co("Second");
        newest.setHunger(42);
        assertTrue(GameController.saveGame(newest, savePath()));

        Bear loaded = GameController.loadGame(savePath());
        assertInstanceOf(Co.class, loaded);
        assertEquals("Second", loaded.getName());
        assertEquals(42, loaded.getHunger());
    }

    @Test
    void corruptSaveReturnsNull() throws Exception {
        Files.writeString(savePath(), "not a Java serialized object");
        assertNull(GameController.loadGame(savePath()));
    }
}
