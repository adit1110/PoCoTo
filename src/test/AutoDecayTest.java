/**
 * @author: Adit Bhimani
 * JUnit test for verifying that a bear's stats decrease over time.
 * This test ensures that the auto-decay feature works as intended.
 */

package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.BearController;
import model.Bear;
import model.Co;
import model.Po;
import model.To;

/**
 * Tests automatic stat decay behavior after a call to updateStats().
 * Ensures that the bear's stats decrease as expected over time for
 * all three bear types: Po, Co, and To.
 */
public class AutoDecayTest {

    /**
     * Sets low hunger/sleep to trigger health decay.
     */

     private void triggerHealthDecay(Bear bear) {
        bear.feed(-100); // drop hunger to 0
        bear.sleep(-100); // drop sleep to 0
     }
    
    /**
     * Verifies that calling updateStats() causes all key stats
     * (hunger, health, sleep, and happiness) to decrease for Po.
     */
    @Test
    public void testStatsDecreaseForPo() {
        Bear po = new Po("Po");
        BearController controller = new BearController(po);

        triggerHealthDecay(po);

        double initialHunger = controller.getHungerPercent();
        double initialHealth = controller.getHealthPercent();
        double initialSleep = controller.getSleepPercent();
        double initialHappiness = controller.getHappinessPercent();

        for (int i = 0; i < 10; i++) { // run multiple decay cycles to trigger health loss
            controller.getBear().updateStats();
        }

        assertTrue(controller.getHungerPercent() < initialHunger, "Po hunger should decrease");
        assertTrue(controller.getHealthPercent() < initialHealth, "Po health should decrease");
        assertTrue(controller.getSleepPercent() < initialSleep, "Po sleep should decrease");
        assertTrue(controller.getHappinessPercent() < initialHappiness, "Po happiness should decrease");
    }

    /**
     * Verifies that calling updateStats() causes all key stats
     * to decrease for Co.
     */
    @Test
    public void testStatsDecreaseForCo() {
        Bear co = new Co("Co");
        BearController controller = new BearController(co);

        triggerHealthDecay(co);

        double initialHunger = controller.getHungerPercent();
        double initialHealth = controller.getHealthPercent();
        double initialSleep = controller.getSleepPercent();
        double initialHappiness = controller.getHappinessPercent();

        for (int i = 0; i < 10; i++) { // run multiple decay cycles to trigger health loss
            controller.getBear().updateStats();
        }

        assertTrue(controller.getHungerPercent() < initialHunger, "Co hunger should decrease");
        assertTrue(controller.getHealthPercent() < initialHealth, "Co health should decrease");
        assertTrue(controller.getSleepPercent() < initialSleep, "Co sleep should decrease");
        assertTrue(controller.getHappinessPercent() < initialHappiness, "Co happiness should decrease");
    }

    /**
     * Verifies that calling updateStats() causes all key stats
     * to decrease for To.
     */
    @Test
    public void testStatsDecreaseForTo() {
        Bear to = new To("To");
        BearController controller = new BearController(to);

        for (int i = 0; i < 10; i++) { // run multiple decay cycles to trigger health loss
            controller.getBear().updateStats();
        }

        double initialHunger = controller.getHungerPercent();
        double initialHealth = controller.getHealthPercent();
        double initialSleep = controller.getSleepPercent();
        double initialHappiness = controller.getHappinessPercent();

        controller.getBear().updateStats();

        assertTrue(controller.getHungerPercent() < initialHunger, "To hunger should decrease");
        assertTrue(controller.getHealthPercent() < initialHealth, "To health should decrease");
        assertTrue(controller.getSleepPercent() < initialSleep, "To sleep should decrease");
        assertTrue(controller.getHappinessPercent() < initialHappiness, "To happiness should decrease");
    }
}
