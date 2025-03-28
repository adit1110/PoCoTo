package service;

import model.Bear;
import service.NotificationService;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NotificationServiceTest.java
 * Unit tests for the NotificationService class
 * Ensures that the correct notification is displayed based on the bear's status
 * and that the notification text is set correctly.
 * @author: Bhavya Sharma
 */

public class NotificationServiceTest {
    /**
     * Test: Simulate the resume button being clicked
     * and verify that the associated Runnable is called.
     * Uses a boolean flag to track if resumeAction is triggered.
     * Note: JavaFX must be initialized using JFXPanel to run on headless test environments.
     */

    private NotificationService notificationService;
    private Text mockNotificationText;

    /**
     * Sets up a fresh NotificationService instance before each test.
     * Creates a mock Text object to simulate the notification text.
     */

    @BeforeEach
    void setUp() {
        mockNotificationText = mock(Text.class);
        notificationService = new NotificationService(mockNotificationText);
    }

    /**
     * Tests that the correct notification is displayed based on the bear's status.
     * Verifies that the notification text is set correctly.
     * Uses mock Bear objects to simulate different bear statuses.
     */
    @Test
    void testCheckBearStatusDead() {
        Bear deadBear = mock(Bear.class);
        when(deadBear.isDead()).thenReturn(true);
        notificationService.checkBearStatus(deadBear);
        verify(mockNotificationText).setText("Your bear has died!");
    }

    /**
     * Tests that the correct notification is displayed based on the bear's status.
     * Verifies that the notification text is set correctly.
     * Uses mock Bear objects to simulate different bear statuses.
     */
    @Test
    void testCheckBearStatusAngry() {
        Bear angryBear = mock(Bear.class);
        when(angryBear.isAngry()).thenReturn(true);
        notificationService.checkBearStatus(angryBear);
        verify(mockNotificationText).setText("Your bear is angry");
    }

    /**
     * Tests that the correct notification is displayed based on the bear's status.
     * Verifies that the notification text is set correctly.
     */
    @Test
    void testCheckBearStatusHungry() {
        Bear hungryBear = mock(Bear.class);
        when(hungryBear.isHungry()).thenReturn(true);
        notificationService.checkBearStatus(hungryBear);
        verify(mockNotificationText).setText("Your bear is hungry!");
    }

    /**
     * Tests that the correct notification is displayed based on the bear's status.
     * Verifies that the notification text is set correctly.
     */
    @Test
    void testCheckBearStatusTired() {
        Bear tiredBear = mock(Bear.class);
        when(tiredBear.isTired()).thenReturn(true);
        notificationService.checkBearStatus(tiredBear);
        verify(mockNotificationText).setText("Your bear is tired");
    }

    /**
     * Tests that the correct notification is displayed based on the bear's status.
     * Verifies that the notification text is set correctly.
     */
    @Test
    void testCheckBearStatusCalm() {
        Bear calmBear = mock(Bear.class);
        when(calmBear.isDead()).thenReturn(false);
        when(calmBear.isAngry()).thenReturn(false);
        when(calmBear.isHungry()).thenReturn(false);
        when(calmBear.isTired()).thenReturn(false);

        notificationService.checkBearStatus(calmBear);
        verify(mockNotificationText).setText("Bear is calm.");
    }
}
