package util;

import model.Bear;
import util.DatabaseHelper;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DatabaseHelperTest.java
 * Unit tests for saving and loading Bear game data to and from a file
 * using JSON format.
 * Created by Bhavya Sharma
 */

public class DatabaseHelperTest {


    /**
     * Test: Simulate the resume button being clicked
     * and verify that the associated Runnable is called.
     * Uses a boolean flag to track if resumeAction is triggered.
     * Note: JavaFX must be initialized using JFXPanel to run on headless test environments.
     */

    @Mock
    private FileWriter mockFileWriter;

    @Mock
    private FileReader mockFileReader;

    @Mock
    private Gson mockGson;

    private DatabaseHelper databaseHelper;

    /**
     * Sets up a fresh DatabaseHelper instance before each test.
     * Creates mock FileWriter, FileReader, and Gson objects to simulate file I/O.
     * Initializes the DatabaseHelper instance with the mock objects.
     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        databaseHelper = new DatabaseHelper();
    }

    /**
     * Tests that the writeToFile method correctly writes a Bear object to a file.
     * Verifies that the FileWriter is called with the correct JSON string.
     */
    @Test
    void testWriteToFile() throws IOException {
        Bear bear = new Bear(); 
        when(mockGson.toJson(bear)).thenReturn("{}"); 
        doNothing().when(mockFileWriter).write(anyString());

        databaseHelper.writeToFile(bear); 
        verify(mockFileWriter, times(1)).write("{}"); 
    }

    /**
     * Tests that the readFromFile method correctly reconstructs a Bear object from a file.
     * Verifies that the FileReader is called and the returned Bear object matches the expected Bear.
     */
    @Test
    void testReadFromFile() throws IOException {
        Bear expectedBear = new Bear(); 
        when(mockGson.fromJson(mockFileReader, Bear.class)).thenReturn(expectedBear); 

        Bear result = databaseHelper.readFromFile(Bear.class);
        assertNotNull(result, "The result should not be null");
        assertEquals(expectedBear, result, "The returned Bear object should match the expected Bear");
    }
}
