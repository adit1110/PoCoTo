package test.view;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import view.PauseMenu;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PauseMenuTest.java
 * Verifies PauseMenu functionality including resume callback execution.
 * Created by Jayansh Bagga
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
            menu.show();
            // Manually simulate resume
            menu.show();
        });

        Thread.sleep(1000); // Allow time for JavaFX thread
        assertTrue(resumeCalled[0], "Resume action should be triggered");
    }
}
