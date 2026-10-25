package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import app.PoCoToApp;
import controller.BearController;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Po;
import view.GameplayScreen;

/**
 * JavaFX integration regression tests for stat-decay timer cleanup.
 *
 * Assumed behaviour: stat decay stops when gameplay is left for Inventory
 * or the Main Menu. These tests require a graphical desktop environment.
 *
 * Note: The repeated-navigation test checks the ORIGINAL screen's timer and
 * successful navigation. It does not enumerate all replacement-screen timers
 * or independently prove the exact decay rate.
 */
public class StatDecayLifecycleTest {

    @BeforeAll
    static void startJavaFx() throws Exception {
        CountDownLatch ready = new CountDownLatch(1);

        try {
            Platform.startup(ready::countDown);

            if (!ready.await(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("JavaFX startup timed out");
            }
        } catch (IllegalStateException alreadyStarted) {
            // JavaFX was already initialized.
        }

        // Keep JavaFX running even when individual tests close their windows.
        runOnFxThread(() -> {
            Platform.setImplicitExit(false);
            return null;
        });
    }

    @AfterAll
    static void stopJavaFx() {
        Platform.exit();
    }

    @Test
    void inventoryRoundTripDoesNotLeaveOldDecayTimerRunning() throws Exception {
        runOnFxThread(() -> {
            try (TestGame game = new TestGame()) {
                findButton(game.stage.getScene().getRoot(), "Inventory").fire();
                assertEquals(Status.STOPPED, game.initialTimer.getStatus(),
                    "Leaving Gameplay for Inventory must stop its timer");

                findButton(game.stage.getScene().getRoot(), "Back to Game").fire();
                assertEquals(Status.STOPPED, game.initialTimer.getStatus(),
                    "The original timer must not restart on return to Gameplay");
                assertNotNull(findButton(game.stage.getScene().getRoot(), "Inventory"));
            }
            return null;
        });
    }

    @Test
    void repeatedInventoryRoundTripsDoNotRestartOriginalTimer() throws Exception {
        runOnFxThread(() -> {
            try (TestGame game = new TestGame()) {
                for (int trip = 1; trip <= 3; trip++) {
                    findButton(game.stage.getScene().getRoot(), "Inventory").fire();
                    assertEquals(Status.STOPPED, game.initialTimer.getStatus(),
                        "Initial timer running in Inventory on trip " + trip);

                    findButton(game.stage.getScene().getRoot(), "Back to Game").fire();
                    assertEquals(Status.STOPPED, game.initialTimer.getStatus(),
                        "Initial timer restarted after trip " + trip);
                    assertNotNull(findButton(game.stage.getScene().getRoot(), "Inventory"),
                        "Gameplay should be usable after trip " + trip);
                }
            }
            return null;
        });
    }

    @Test
    void backToMainMenuStopsGameplayTimer() throws Exception {
        runOnFxThread(() -> {
            try (TestGame game = new TestGame()) {
                findButton(game.stage.getScene().getRoot(), "Back to Main Menu").fire();
                assertEquals(Status.STOPPED, game.initialTimer.getStatus(),
                    "Back to Main Menu must stop the gameplay decay timer");
                assertNotNull(findButton(game.stage.getScene().getRoot(), "New Game"),
                    "Main Menu should be displayed after navigation");
            }
            return null;
        });
    }

    /** A fresh gameplay stage per test, with its first timer retained for inspection. */
    private static final class TestGame implements AutoCloseable {
        final Stage stage = new Stage();
        final Timeline initialTimer;

        TestGame() throws Exception {
            GameplayScreen screen = new GameplayScreen(
                new BearController(new Po("Po")), new PoCoToApp());
            screen.start(stage);
            Field field = GameplayScreen.class.getDeclaredField("autoDecayTimer");
            field.setAccessible(true);
            initialTimer = (Timeline) field.get(screen);
            assertNotNull(initialTimer, "Gameplay should create a decay timer");
            assertEquals(Status.RUNNING, initialTimer.getStatus(),
                "The initial Gameplay timer must be running");
        }

        @Override
        public void close() {
            initialTimer.stop(); // Always clean up, even if an assertion fails.
            stage.close();
        }
    }

    private static Button findButton(Parent root, String label) {
        root.applyCss();
        for (javafx.scene.Node node : root.lookupAll(".button")) {
            if (node instanceof Button button && label.equals(button.getText())) {
                return button;
            }
        }
        throw new IllegalArgumentException("Button not found: " + label);
    }

    private static <T> T runOnFxThread(Callable<T> action) throws Exception {
        if (Platform.isFxApplicationThread()) {
            return action.call();
        }
        FutureTask<T> task = new FutureTask<>(action);
        Platform.runLater(task);
        return task.get(15, TimeUnit.SECONDS);
    }
}
