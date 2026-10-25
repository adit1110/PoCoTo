package service;

import javafx.scene.text.Text;
import model.Po;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** CS2212 notification checks, corrected for actual bear thresholds. */
public class NotificationServiceTest {
    private NotificationService service;
    private Text text;
    @BeforeEach void setup() {
        text = new Text();
        service = new NotificationService(text);
    }
    @Test void testDeadNotification() {
        Po bear = new Po("TestPo");
        bear.setHealth(0);
        service.checkBearStatus(bear);
        assertEquals("Your bear has died!", text.getText());
    }
    @Test void testAngryNotification() {
        Po bear = new Po("TestPo");
        bear.setHappiness(20);
        service.checkBearStatus(bear);
        assertEquals("Your bear is angry", text.getText());
    }
    @Test void testHungryNotification() {
        Po bear = new Po("TestPo");
        bear.setHunger(19);
        service.checkBearStatus(bear);
        assertEquals("Your bear is hungry!", text.getText());
    }
    @Test void testTiredNotification() {
        Po bear = new Po("TestPo");
        bear.setSleep(15);
        service.checkBearStatus(bear);
        assertEquals("Your bear is tired", text.getText());
    }
    @Test void testCalmNotification() {
        Po bear = new Po("TestPo");
        service.checkBearStatus(bear);
        assertEquals("Bear is calm.", text.getText());
    }
}
