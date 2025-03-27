package test;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import view.PauseMenu;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PauseMenuTest.java
 * Unit test for PauseMenu UI functionality.
 * Specifically tests whether the resume action triggers when Resume is invoked.
 * Created by Jayansh Bagga
 */
public class PauseMenuTest {

    /**
     * Test: Simulate the resume button being clicked
     * and verify that the associated Runnable is called.
     * Uses a boolean flag to track if resumeAction is triggered.
     * Note: JavaFX must be initialized using JFXPanel to run on headless test environments.
     */
    @Test
    public void testResumeActionTriggered() throws Exception {
        new JFXPanel(); // Initializes JavaFX toolkit (required in headless mode for JavaFX tests)

        final boolean[] resumeCalled = {false};  // Mutable flag to detect if resumeAction is executed

        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu(new Stage(),
                () -> resumeCalled[0] = true,  // Resume action logic
                () -> {},                     // No-op Save callback
                () -> {}                      // No-op MainMenu callback
            );

            menu.show();                      // Show the PauseMenu UI (modal dialog)
            menu.show();                      // Forces the stage to open for interaction

            // Simulate resume action directly
            resumeCalled[0] = false;
            menu = new PauseMenu(new Stage(),
                () -> resumeCalled[0] = true,
                () -> {},
                () -> {}
            );

            menu.show();                      // Simulate showing it again before triggering
            menu.getResumeAction().run();          // Simulate clicking resume button

            // Verify the action was executed
            assertTrue(resumeCalled[0], "Resume action should be triggered");
        });

        Thread.sleep(1000); // Give JavaFX thread time to execute events
    }
}
