package util;

import model.Po;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/** Original JSON persistence test. Savefile is backed up and restored. */
public class DatabaseHelperTest {
    @Test void testWriteAndReadBear() throws IOException {
        Path file = Path.of("savefile.json");
        boolean existed = Files.exists(file);
        byte[] previous = existed ? Files.readAllBytes(file) : null;
        try {
            Po original = new Po("TestPo");
            original.setHunger(70);
            original.setHappiness(50);
            DatabaseHelper.writeToFile(original);
            Po loaded = (Po) DatabaseHelper.readFromFile(Po.class);
            assertNotNull(loaded);
            assertEquals(original.getName(), loaded.getName());
            assertEquals(70, loaded.getHunger());
            assertEquals(50, loaded.getHappiness());
        } finally {
            if (existed) Files.write(file, previous);
            else Files.deleteIfExists(file);
        }
    }
}
