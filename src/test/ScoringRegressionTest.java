package test;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import controller.GameController;
import model.Bear;
import model.Po;

/** Regression coverage for score calculation and save-file persistence. */
public class ScoringRegressionTest {
    @TempDir Path tempDir;

    @Test void startsAtZero() {
        assertEquals(0, new Po("Po").getScore());
    }

    @Test void rewardsOnlyActualStatRecovery() {
        Po bear = new Po("Po");
        bear.setHunger(95);
        int before = bear.getHunger();
        bear.feed(10);
        bear.awardCarePoints(before, bear.getHunger());
        assertEquals(5, bear.getScore());
        before = bear.getHunger();
        bear.feed(10);
        bear.awardCarePoints(before, bear.getHunger());
        assertEquals(5, bear.getScore());
    }

    @Test void doesNotRewardStatLoss() {
        Po bear = new Po("Po");
        bear.awardCarePoints(90, 80);
        assertEquals(0, bear.getScore());
    }

    @Test void rewardsMinigameOutcomes() {
        Po bear = new Po("Po");
        assertEquals(30, bear.rewardMiniGame("Player Wins!"));
        assertEquals(10, bear.rewardMiniGame("It's a Draw!"));
        assertEquals(0, bear.rewardMiniGame("Computer Wins!"));
        assertEquals(40, bear.getScore());
    }

    @Test void ignoresUnknownMinigameResults() {
        Po bear = new Po("Po");
        assertEquals(0, bear.rewardMiniGame("Player Win"));
        assertEquals(0, bear.rewardMiniGame(null));
        assertEquals(0, bear.getScore());
    }

    @Test void saveAndLoadPreservesScore() {
        Po bear = new Po("Po");
        bear.rewardMiniGame("Player Wins!");
        bear.awardCarePoints(20, 30);
        Path save = tempDir.resolve("scoring-save.dat");
        assertTrue(GameController.saveGame(bear, save));
        Bear loaded = GameController.loadGame(save);
        assertNotNull(loaded);
        assertEquals(40, loaded.getScore());
    }
}
