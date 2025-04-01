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
     * Verifies that calling updateStats() causes all key stats
     * (hunger, health, sleep, and happiness) to decrease for Po.
     */
    @Test
    public void testPoHungerDecreases() {
        Bear po = new Po("Po");
        BearController controller = new BearController(po);

        double initial = controller.getHungerPercent();
        controller.updateBear();
        assertTrue(controller.getHungerPercent() < initial, "Po hunger should decrease");
    }

    @Test
    public void testPoSleepDecreases() {
        Bear po = new Po("Po");
        BearController controller = new BearController(po);

        double initial = controller.getSleepPercent();
        controller.updateBear();
        assertTrue(controller.getSleepPercent() < initial, "Po sleep should decrease");
    }

    @Test
    public void testPoHappinessDecreases() {
        Bear po = new Po("Po");
        BearController controller = new BearController(po);

        double initial = controller.getHappinessPercent();
        controller.updateBear();
        assertTrue(controller.getHappinessPercent() < initial, "Po happiness should decrease");
    }

    /**
     * Verifies that calling updateStats() causes all key stats
     * to decrease for Co.
     */
    @Test
    public void testCoSleepDecreases() {
        Bear co = new Co("Co");
        BearController controller = new BearController(co);

        double initial = controller.getSleepPercent();
        controller.updateBear();
        assertTrue(controller.getSleepPercent() < initial, "Co sleep should decrease");
    }

    @Test
    public void testCoHappinessDecreases() {
        Bear co = new Co("Co");
        BearController controller = new BearController(co);

        double initial = controller.getHappinessPercent();
        controller.updateBear();
        assertTrue(controller.getHappinessPercent() < initial, "Co happiness should decrease");
    }

    /**
     * Verifies that calling updateStats() causes all key stats
     * to decrease for To.
     */
    @Test
    public void testToHungerDecreases() {
        Bear to = new To("To");
        BearController controller = new BearController(to);

        double initial = controller.getHungerPercent();
        controller.updateBear();
        assertTrue(controller.getHungerPercent() < initial, "To hunger should decrease");
    }

    @Test
    public void testToSleepDecreases() {
        Bear to = new To("To");
        BearController controller = new BearController(to);

        double initial = controller.getSleepPercent();
        controller.updateBear();
        assertTrue(controller.getSleepPercent() < initial, "To sleep should decrease");
    }

    @Test
    public void testToHappinessDecreases() {
        Bear to = new To("To");
        BearController controller = new BearController(to);

        double initial = controller.getHappinessPercent();
        controller.updateBear();
        assertTrue(controller.getHappinessPercent() < initial, "To happiness should decrease");
    }
}
