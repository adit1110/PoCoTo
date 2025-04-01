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
 
NotificationServiceTest.java
Verifies correct notifications based on bear state.*/
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
        Bear bear = new Po();
        bear.setHealth(0);
        service.checkBearStatus(bear);
        assertEquals("Your bear has died!", text.getText());
    }

    @Test
    void testAngryNotification() {
        Bear bear = new Po();
        bear.setHunger(0); // Triggers anger
        service.checkBearStatus(bear);
        assertEquals("Your bear is angry", text.getText());
    }

    @Test
    void testHungryNotification() {
        Bear bear = new Po();
        bear.setHunger(19); // Triggers hunger warning
        service.checkBearStatus(bear);
        assertEquals("Your bear is hungry!", text.getText());
    }

    @Test
    void testTiredNotification() {
        Bear bear = new Po();
        bear.setSleep(15);
        service.checkBearStatus(bear);
        assertEquals("Your bear is tired", text.getText());
    }

    @Test
    void testCalmNotification() {
        Bear bear = new Po(); // Default stats
        service.checkBearStatus(bear);
        assertEquals("Bear is calm.", text.getText());
    }
}