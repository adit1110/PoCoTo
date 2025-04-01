package service;

import javafx.embed.swing.JFXPanel;
import javafx.scene.text.Text;
import model.Bear;
import model.Po;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NotificationServiceTest.java
 * Verifies correct notifications based on bear state.
 * @author Adit Bhimani and Bhavya Sharma
 * */
public class NotificationServiceTest {

    private NotificationService service;
    private Text text;

    @BeforeAll
    static void initFX() {
        new JFXPanel(); // Start JavaFX for headless test
    }

    @BeforeEach
    void setUp() {
        text = new Text();
        service = new NotificationService(text);
    }

    @Test
    void testDeadNotification() {
        Po bear = new Po("TestPo");
        bear.setHunger(0);
        service.checkBearStatus(bear);
        assertEquals("Your bear has died!", text.getText());
    }

    @Test
    void testAngryNotification() {
        Po bear = new Po("TestPo");
        bear.setHunger(0); // Triggers anger
        service.checkBearStatus(bear);
        assertEquals("Your bear is angry", text.getText());
    }

    @Test
    void testHungryNotification() {
        Po bear = new Po("TestPo");
        bear.setHunger(19); // Triggers hunger warning (modified by Adit)
        service.checkBearStatus(bear);
        assertEquals("Your bear is hungry!", text.getText());
    }

    @Test
    void testTiredNotification() {
        Po bear = new Po("TestPo");
        bear.setSleep(15); // modified by Adit
        service.checkBearStatus(bear);
        assertEquals("Your bear is tired", text.getText());
    }

    @Test
    void testCalmNotification() {
        Po bear = new Po("TestPo"); // Default stats
        service.checkBearStatus(bear);
        assertEquals("Bear is calm.", text.getText());
    }
}