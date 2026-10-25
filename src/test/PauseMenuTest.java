package test;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.PauseMenu;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

/** Modal-free, bounded JavaFX callback check for original PauseMenuTest. */
public class PauseMenuTest {
    @BeforeAll static void initFx() throws Exception {
        try { Platform.startup(() -> {}); }
        catch (IllegalStateException alreadyRunning) { /* toolkit already started */ }
        Platform.setImplicitExit(false);
    }

    @Test void testResumeActionTriggered() throws Exception {
        CountDownLatch completed = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                Stage owner = new Stage();
                PauseMenu menu = new PauseMenu(owner, () -> {}, () -> {}, () -> {});
                // Exercise the actual Resume button, not merely the stored Runnable.
                Stage dialog = (Stage) reflectStage(menu);
                Pane root = (Pane) dialog.getScene().getRoot();
                Button resume = root.getChildren().stream()
                    .filter(Button.class::isInstance).map(Button.class::cast)
                    .filter(b -> b.getText().equals("Resume Game"))
                    .findFirst().orElseThrow();
                final boolean[] called = {false};
                PauseMenu verifiedMenu = new PauseMenu(owner, () -> called[0] = true, () -> {}, () -> {});
                Stage verifiedStage = (Stage) reflectStage(verifiedMenu);
                Pane verifiedRoot = (Pane) verifiedStage.getScene().getRoot();
                Button verifiedResume = verifiedRoot.getChildren().stream()
                    .filter(Button.class::isInstance).map(Button.class::cast)
                    .filter(b -> b.getText().equals("Resume Game"))
                    .findFirst().orElseThrow();
                verifiedResume.fire();
                assertTrue(called[0]);
            } catch (Throwable t) { error.set(t); }
            finally { completed.countDown(); }
        });
        assertTrue(completed.await(5, TimeUnit.SECONDS), "JavaFX callback timed out");
        if (error.get() != null) fail(error.get());
    }

    private static Object reflectStage(PauseMenu menu) throws Exception {
        var field = PauseMenu.class.getDeclaredField("pauseStage");
        field.setAccessible(true);
        return field.get(menu);
    }
}
