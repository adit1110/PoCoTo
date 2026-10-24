package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import model.Bear;
import model.Po;

/** Regression tests for Bear's play/heal stat calculations. */
public class BearStatLogicTest {

    private static void setStat(Bear bear, String fieldName, int value) throws Exception {
        Field field = Bear.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.setInt(bear, value);
    }

    @Test
    void playIncreasesExistingHappinessRatherThanUsingHunger() throws Exception {
        Bear bear = new Po("Po");
        setStat(bear, "hunger", 70);
        setStat(bear, "happiness", 30);

        bear.play(15);

        assertEquals(45, bear.getHappiness(), "Play should add 15 to happiness (30), not hunger (70)");
        assertEquals(70, bear.getHunger(), "Playing must not change hunger");
    }

    @Test
    void healIncreasesExistingHealthRatherThanUsingHunger() throws Exception {
        Bear bear = new Po("Po");
        setStat(bear, "hunger", 70);
        setStat(bear, "health", 20);

        bear.heal(15);

        assertEquals(35, bear.getHealth(), "Heal should add 15 to health (20), not hunger (70)");
        assertEquals(70, bear.getHunger(), "Healing must not change hunger");
    }

    @Test
    void playCapsHappinessAtOneHundred() throws Exception {
        Bear bear = new Po("Po");
        setStat(bear, "hunger", 10);
        setStat(bear, "happiness", 95);

        bear.play(15);

        assertEquals(100, bear.getHappiness());
    }

    @Test
    void healCapsHealthAtOneHundred() throws Exception {
        Bear bear = new Po("Po");
        setStat(bear, "hunger", 10);
        setStat(bear, "health", 95);

        bear.heal(15);

        assertEquals(100, bear.getHealth());
    }
}