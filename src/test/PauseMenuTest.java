package test; // Modified by Adit to fix the package name

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import view.PauseMenu;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PauseMenuTest.java
 * Verifies PauseMenu functionality including resume callback execution.
 * @author: Jayansh Bagga
 */
public class PauseMenuTest {

    /**
     * Ensures the resume Runnable is triggered correctly
     * when resumeAction is invoked.
     */
    @Test
    public void testResumeActionTriggered() throws Exception {
        new JFXPanel(); // Initializes JavaFX toolkit

        final boolean[] resumeCalled = {false};

        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu(new Stage(),
                () -> resumeCalled[0] = true,
                () -> {},
                () -> {}
            );

            menu.show();                           // Simulate showing the menu
            menu.getResumeAction().run();          // Simulate clicking resume button
        });

        Thread.sleep(1000); // Allow time for JavaFX thread
        assertTrue(resumeCalled[0], "Resume action should be triggered");
    }
}
