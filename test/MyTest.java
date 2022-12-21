package com.saksham.game;

import com.mygdx.game.HealthGenerator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class MyTest {
    @Test
    public void testHealth() {
        HealthGenerator healthGenerator = HealthGenerator.getInstance();
        float myHealth = healthGenerator.getMaxHealth();
        assertEquals(1.0, myHealth);
    }
}