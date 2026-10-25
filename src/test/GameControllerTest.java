package test.controller;

import model.Bear;
import model.Po;
import controller.GameController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/** Original CS2212 GameController checks, isolated from real saves. */
public class GameControllerTest {
    @TempDir Path tempDir;
    @Test public void testSetAndGetCurrentBear() {
        Bear bear = new Po("Po");
        GameController.setCurrentBear(bear);
        assertSame(bear, GameController.getCurrentBear());
    }
    @Test public void testSaveAndLoadBear() {
        Path save = tempDir.resolve("bear_save.dat");
        Bear bear = new Po("Po");
        bear.setHunger(60);
        assertTrue(GameController.saveGame(bear, save));
        Bear loaded = GameController.loadGame(save);
        assertNotNull(loaded);
        assertEquals(bear.getName(), loaded.getName());
        assertEquals(60, loaded.getHunger());
    }
}
