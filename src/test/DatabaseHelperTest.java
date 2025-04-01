package util;

import model.Po;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

/**
 * DatabaseHelperTest.java
 * Unit tests for saving and loading Bear game data to and from a file.
 * @author: Bhavya Sharma and Adit Bhimani
 */

public class DatabaseHelperTest {


    @Test
    void testWriteAndReadBear() throws IOException {
        Po originalBear = new Po("TestPo");
        originalBear.setHunger(70); // added by Adit
        originalBear.setHappiness(50); // added by Adit

        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.writeToFile(originalBear);

        Po loadedBear = (Po) dbHelper.readFromFile(Po.class); // added by Adit

        assertNotNull(loadedBear);
        assertEquals(originalBear.getName(), loadedBear.getName()); // modified by Adit
        assertEquals(originalBear.getHunger(), loadedBear.getHunger()); // modified by Adit
        assertEquals(originalBear.getHappiness(), loadedBear.getHappiness()); // modified by Adit
    }
}
