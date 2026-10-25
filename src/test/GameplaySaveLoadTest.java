package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import app.PoCoToApp;
import controller.BearController;
import controller.GameController;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Po;
import view.GameplayScreen;

/** Integration checks for gameplay Save and Load buttons. */
public class GameplaySaveLoadTest {
    @TempDir Path tempDir;

    @BeforeAll
    static void initializeJavaFx() throws Exception {
        CountDownLatch started = new CountDownLatch(1);
        try {
            Platform.startup(started::countDown);
            assertTrue(started.await(10, TimeUnit.SECONDS));
        } catch (IllegalStateException alreadyStarted) {
            // A prior test initialized JavaFX in this JVM.
        }
        runOnFxThread(() -> {
            Platform.setImplicitExit(false);
            return null;
        });
    }

    @AfterAll
    static void exitJavaFx() {
        Platform.exit();
    }

    @Test
    void gameplayButtonsSaveAndRestoreActiveBear() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage();
            GameplayScreen screen = null;
            try {
                Po initial = new Po("Po");
                initial.setHunger(37);
                screen = new GameplayScreen(new BearController(initial), new PoCoToApp(),
                    tempDir.resolve("saved-bear.dat"));
                screen.start(stage);
                findButton(stage.getScene().getRoot(), "Save Game").fire();

                initial.setHunger(80);
                findButton(stage.getScene().getRoot(), "Load Game").fire();
                BearController active = currentController(screen);
                assertNotSame(initial, active.getBear());
                assertEquals(37, active.getBear().getHunger());
                assertSame(active.getBear(), GameController.getCurrentBear());
                assertEquals(37, Math.round(active.getHungerPercent() * 100));
            } finally {
                stopTimer(screen);
                stage.close();
            }
            return null;
        });
    }

    @Test
    void missingSaveDoesNotReplaceActiveBear() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage();
            GameplayScreen screen = null;
            try {
                Po original = new Po("Po");
                screen = new GameplayScreen(new BearController(original), new PoCoToApp(),
                    tempDir.resolve("does-not-exist.dat"));
                screen.start(stage);
                findButton(stage.getScene().getRoot(), "Load Game").fire();
                assertSame(original, currentController(screen).getBear());
                assertTrue(stage.getScene().getRoot().lookupAll(".label").stream()
                    .filter(n -> n instanceof Label)
                    .map(n -> ((Label) n).getText())
                    .anyMatch(t -> t.contains("Could not load game")));
            } finally {
                stopTimer(screen);
                stage.close();
            }
            return null;
        });
    }

    private static BearController currentController(GameplayScreen screen) throws Exception {
        Field f = GameplayScreen.class.getDeclaredField("bearController");
        f.setAccessible(true);
        return (BearController) f.get(screen);
    }

    private static void stopTimer(GameplayScreen screen) throws Exception {
        if (screen == null) return;
        Field f = GameplayScreen.class.getDeclaredField("autoDecayTimer");
        f.setAccessible(true);
        Timeline timer = (Timeline) f.get(screen);
        if (timer != null) timer.stop();
    }

    private static Button findButton(Parent root, String label) {
        root.applyCss();
        return root.lookupAll(".button").stream()
            .filter(n -> n instanceof Button && label.equals(((Button) n).getText()))
            .map(n -> (Button) n)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Missing button: " + label));
    }

    private static <T> T runOnFxThread(Callable<T> work) throws Exception {
        FutureTask<T> task = new FutureTask<>(work);
        Platform.runLater(task);
        return task.get(15, TimeUnit.SECONDS);
    }
}
